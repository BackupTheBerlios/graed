/*
 * Created on 16 janv. 2005
 *	Fenêtre d'emploi du temps
 *	Tests réalisés:
 *	-Drag and Drop
 *	-Fusion (redimenssionnement de cellules)
 *	-Sélection d'une seule JTable
 * 
 */
package graed.gui.timetable;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.JPaintPanel;
import graed.gui.indisponibilite.IndisponibiliteWindow;
import graed.indisponibilite.IndisponibiliteInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;
/**
 * @author Nadège GONORD
 *
 */
public class TimetableColJTable extends JTable {
	private Component moving;
	
	private Hashtable list_ind;
	private IndisponibiliteInterface i_tmp;	
	private TimetableDefaultTableModel tm;	/* Modèle de données associé à la table */
	TimetableDefaultListSelectionModel l; /* Modèle de sélection partagé entre les différentes tables */
	/* Menu Popup */
	private JMenuItem creer;
	private JMenuItem consu;
	private JMenuItem modif;
	private JMenuItem suppr;
	private Collection collec;
	private Container cours; 
	/**
	 * Constructeur de la classe
	 * Réalise le Drag and Drop
	 * Définit le ColumnModel pour recalculer le nombre de colonnes
	 * Définit la sélection pour déselection les cellules des autres tables
	 * Définit le type de sélection par colonne
	 * Ajoute toutes les colonnes
	 * @param arg0
	 */
	public TimetableColJTable(TimetableDefaultTableModel arg0,TimetableDefaultListSelectionModel l) {
		super(arg0);
		i_tmp=null;
		creer=null;
		this.setComponentPopupMenu(CreatePopupMenu());
		list_ind = new Hashtable(); 
		tm=arg0;
		/**
		 * Sélection par ligne non
		 */
		setRowSelectionAllowed(false);
		/**
		 * Sélection par colonne oui
		 */
		setColumnSelectionAllowed(true);
		setSelectionModel(l);
		l.addTable(this);
		setColumnModel(new TimetableColumnModel(tm));
		this.l=l;
		setDragEnabled(true);
		for(int i=0;i<getColumnCount();++i)
		{
			TableColumn t=new TableColumn();
			t.setCellRenderer(new TimetableTableCellRenderer());
			addColumn(t);
		}

		MouseListener listener = new MouseAdapter() {
        	private int rowF =0;
        	private int colF=0;
        	
            public void mousePressed(MouseEvent e){
            		Point p= e.getPoint();
            		rowF= TimetableColJTable.this.rowAtPoint(p);
            		colF= TimetableColJTable.this.columnAtPoint(p);
            		Object k =TimetableColJTable.this.getValueAt(rowF,colF);
            		if( k!= null){
            			TimetableColJTable.this.setCellSelectionEnabled(false);
            			TimetableColJTable.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            		}     
            		/* Menu Popup */
            		if(e.getButton() == MouseEvent.BUTTON3 ){
    				 	JPopupMenu pop=TimetableColJTable.this.getComponentPopupMenu();
    				 	    				 	if( k!= null){      				 		
                			creer.setEnabled(false);
                				Collection coll=getI(colF);
                				if(coll!=null && coll.size()==1){
                					i_tmp=(IndisponibiliteInterface) coll.toArray()[0];
                					consu.setEnabled(true);
                					modif.setEnabled(true);
                					suppr.setEnabled(true);
                				}
                				else{
                					consu.setEnabled(false);
                    				modif.setEnabled(false);
                    				suppr.setEnabled(false);
                				}
                			
                		} 
    				 	else{
    				 		creer.setEnabled(true);
    				 		consu.setEnabled(false);
            				modif.setEnabled(false);
            				suppr.setEnabled(false);
    				 	}
    				 	if(pop!=null)pop.show(TimetableColJTable.this, e.getX(),e.getY());
    				 	return;
    				 }
            		
		}
		
		public void mouseReleased(MouseEvent e){	
			if( e.getButton() == MouseEvent.BUTTON3 ) return;
			Container ct = TimetableColJTable.this.getParent();
			Point p=ct.getMousePosition(true);
			Component c = TimetableColJTable.this.getParent().getComponentAt(p);
			if( !(c instanceof TimetableColJTable ))return;
			TimetableColJTable table = (TimetableColJTable)c;
		
			int row= table.rowAtPoint(p);
			int col= table.columnAtPoint(p);
			Object o=TimetableColJTable.this.getValueAt(rowF,colF);
			if( o!= null && (col!=colF||row!=rowF||table!=TimetableColJTable.this)) {
				int size = TimetableColJTable.this.getCellSize( rowF, colF );	
				//On ne déplace si la taille est trop grande
				try {
						Collection coll = TimetableColJTable.this.removeIndispo(colF);
						for(Iterator it=coll.iterator();it.hasNext();){
							IndisponibiliteInterface i=(IndisponibiliteInterface) it.next();
							int j=Integer.parseInt(table.getName())-Integer.parseInt(TimetableColJTable.this.getName());
							i.setDebut( new Date((i.getDebut().getTime()+j*(1000*60*60*24))));	
							i.setFin( new Date((i.getFin().getTime()+j*(1000*60*60*24))));
							i.setHdebut(new Time(i.getHdebut().getTime()+((col-colF)*1000*60*15)));
							table.addIndispo(i,col,size,false);	
							Client.getIndisponibiliteManager().updateIndiponibilite(i);
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				
				
				TimetableColJTable.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				table.changeSelection(row,col,false,false);
				table.setCellSelectionEnabled(true);
				
			}
			else if(o!=null){
				System.out.println("Drag and Drop "+col+"!="+colF+" && "+row+"!="+rowF);
			}
			else  System.out.println("Drag and Drop o=null");
		}
        };
        
		addMouseListener(listener);
		setMouseListener();
		setMouseMotionListener();
		
	}
	
	protected void setMouseListener() {
        addMouseListener( new MouseListener() {

            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                Component c = getComponentAt(p);
                if( c!=null && c!=TimetableColJTable.this ) {
                    moving = c;
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    setComponentZOrder(moving,0);
                    validate();
                    System.out.println(moving);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if( moving != null ) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    moving = null;
                }
            }

            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            
        });
    }
    
    
    protected void setMouseMotionListener() {
        addMouseMotionListener( new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                if( moving != null
                        && e.getX()<=(getWidth()-moving.getWidth())
                        && e.getY()<=(getHeight()-moving.getHeight())
                        && e.getX()>=0
                        && e.getY()>=0)
                    moving.setLocation(e.getX(),e.getY());
            }

            public void mouseMoved(MouseEvent e) {}
            
        });
    }
	
	
	
	/**
	 * On redéfinit la taille de la JTable et de ses colonnes
	 */
	public void setPreferredSize(Dimension preferredSize){
		super.setPreferredSize(preferredSize);
		for(int i=0;i<getColumnModel().getColumnCount();++i)
		{
			TableColumn c=getColumnModel().getColumn(i);
			if(c!=null)c.setPreferredWidth(preferredSize.width/getColumnCount());
			//System.out.println(tm.getName()+"setPreferredSize col="+i+" width="+preferredSize.width/getColumnCount());
		}
	}
	/**
	 * Renvoie le nom de la table (jour)
	 */
	public String getName(){
		return tm.getName();
	}
	/**
	 * Renvoie la valeur à la ligne et colonne indiquée
	 */
	public Object getValueAt(int row, int column) {
		return tm.getValueAt(row, column);
	}
	
	/**
	 * Création d'une Popup menu 
	 * @return Popup menu
	 */
	private JPopupMenu CreatePopupMenu(){
		JPopupMenu p= new JPopupMenu();		
		/* Créer une indisponibilite */
		creer=new JMenuItem("Creer");
		creer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {						
				try {				
					new IndisponibiliteWindow(InformationWindow.CREATE,i_tmp).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(null,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
			
		});
		consu=new JMenuItem("Consulter");
		consu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(i_tmp!=null)new IndisponibiliteWindow(InformationWindow.SEE,i_tmp).OpenWindow();
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(null,
							"Vous ne pouvez consulter cette indisponibilite",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}		
		});
		modif=new JMenuItem("Modifier");
		modif.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(i_tmp!=null)new IndisponibiliteWindow(InformationWindow.MODIFY,i_tmp).OpenWindow();
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(null,
							"Vous ne pouvez modifier cette indisponibilite",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}		
		});
		suppr=new JMenuItem("Supprimer");
		suppr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {				
					try {
						if(i_tmp!=null)Client.getIndisponibiliteManager().deleteIndisponibilite(i_tmp);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i_tmp=null;				
			}		
		});
		p.add(creer);
		p.add(consu);
		p.add(modif);
		return p;
	}
	/**
	 * Ajoute un nouveau cours (du texte obligatoirement)
	 * Insère un JTextArea dans la table
	 * @param o = texte!!!
	 * @param col
	 * @param size taille du cours (nombre de colonnes)
	 */
	public void addIndispo (IndisponibiliteInterface i,int col,int size, boolean b){
		if(b)col=find_col(col,size);		
		addI(i,col);
		JTextArea j =(JTextArea) getValueAt(0,col);
		String text="";
		String tooltext="";
		if(j!=null)	{
			text=j.getText()+"\n";
			tooltext=j.getToolTipText().replaceAll("</html>","<br><br>");
			int size_tmp=tm.getCellSize(0,col);
			TableColumn c=getColumnModel().getColumn(col);
			c.setPreferredWidth(c.getPreferredWidth()/size_tmp);
			size=size>size_tmp?size:size_tmp;
			tm.modifyCellSize(col,size);
		}
		else{
			j = new JTextArea();
			tooltext="<html>";
		}
		try {
			j.setText(text+i.print());
			tooltext+=i.print().replaceAll("\\n","<br>");	
			j.setToolTipText(tooltext+"</html>");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.setOpaque(true);
		//Passer à la ligne suivante pour le texte
		j.setLineWrap(true);
		j.setWrapStyleWord(true);		
		//Gestion des erreurs de la taille de la cellule
		if(size>tm.getColumnCount()-col)size=tm.getColumnCount()-col;
		if((JTextArea) getValueAt(0,col)==null)tm.setValueAt(j,col,size);
		TableColumn c=getColumnModel().getColumn(col);	
		c.setPreferredWidth((size*c.getPreferredWidth())+size-1);
	}
	/**
	 * Supprime la donnée à la colonne indiquée
	 * @param col
	 * @return
	 */
	public Collection removeIndispo (int col){
		int size = tm.getCellSize(0, col);
		Object o =tm.removeValueAt(col,size);
		TableColumn c=getColumnModel().getColumn(col);
		c.setPreferredWidth(c.getPreferredWidth()/size);
		return removeI(col);
	}
	/**
	 * Supprime une liste d'indisponibilité sur une colonne
	 * @param col la colonne
	 * @return la liste des indisponibilités
	 */
	public Collection removeI(int col){
		return (Collection) list_ind.remove(new Integer(col));
	}
	/**
	 * Ajoute une indisponibilité sur une colonne
	 * @param i l'indisponibilité
	 * @param col la colonne
	 */
	private void addI(IndisponibiliteInterface i,int col){
		Collection coll=getI(col);
		if(coll!=null){
			coll.add(i);
		}
		else {
			coll=new HashSet();
			coll.add(i);
		}
		list_ind.put(new Integer(col), coll);
	}
	/**
	 * Récupère la liste des indisponibilités à l'emplacement donné
	 * @param col la colonne
	 * @return une collection d'indisponibilités
	 */
	public Collection getI(int col){
		return (Collection) list_ind.get(new Integer(col));
	}
	/**
	 * Trouve le bon numéro de colonne par rapport 
	 * aux indisponibilités déjà ajoutée à la table
	 * @param col le numéro de colonne initial
	 * @param size la taille
	 * @return le numéro de colonne calculé
	 */
	private int find_col(int col,int size){
		if(list_ind!=null){
			for(Enumeration en=list_ind.keys();en.hasMoreElements();){
				int col_tmp=((Integer)en.nextElement()).intValue();				
				if(col_tmp<col){
					col-=(tm.getCellSize(0,col_tmp)-1);
					if(col<0){
						col=col_tmp;						
					}
				}				
			}
		}	
		return col;
	}
	/**
	 * Trouve le bon numéro de colonne par rapport 
	 * aux indisponibilités déjà ajoutée à la table
	 * @param col le numéro de colonne initial
	 * @param size la taille
	 * @return le numéro de colonne calculé
	 */
	private int find_real_col(int col){
		if(list_ind!=null){
			for(Enumeration en=list_ind.keys();en.hasMoreElements();){
				int col_tmp=((Integer)en.nextElement()).intValue();				
				if(col_tmp<col){
					col+=(tm.getCellSize(0,col_tmp)-1);					
				}				
			}
		}			
		return col;
	}
	/**
	 * Récupère la taille du cours (nombre de colonnes)
	 * @param row
	 * @param col
	 * @return
	 */
	public int getCellSize( int row, int col) {
		return tm.getCellSize( row, col );
	}

	/**
	 * Permet la sélection de plusieurs colonnes en déselectionnant les colonnes
	 * des autres tables
	 */
	public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
		l.clearAllTables();
		super.changeSelection(arg0, arg1, arg2, arg3);
	}
	/**
	 * Vide l'emploi du temps
	 *
	 */
	public void clear(){
		for(Enumeration en=list_ind.keys();en.hasMoreElements();)
			removeIndispo (((Integer)en.nextElement()).intValue());
		tm.clear();
		list_ind.clear();
	}
	
	
	
	/*public void resize(int width, int height, int nbcol) {
		int size=0;
		for(int j=0;j<getColumnModel().getColumnCount();++j)
		{
			TableColumn c=getColumnModel().getColumn(j);
			if(c!=null)c.setPreferredWidth(width/nbcol);
			System.out.println(tm.getName()+"resize col="+j+" width="+width/nbcol);
		}
		if(list_ind!=null && !list_ind.isEmpty()){
			for(Enumeration en=list_ind.keys();en.hasMoreElements();){
				Integer col_tmp=((Integer)en.nextElement());	
				Collection c=(Collection) list_ind.get(col_tmp);
				for (Iterator i=c.iterator();i.hasNext();){
					IndisponibiliteInterface ii=(IndisponibiliteInterface) i.next();
					try {
						if (ii.getDuree()>size)size=ii.getDuree();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				TableColumn tc=getColumnModel().getColumn(col_tmp.intValue());		
				tc.setPreferredWidth((size*tc.getPreferredWidth())+size-1);	
			}
		}
	}*/
}
