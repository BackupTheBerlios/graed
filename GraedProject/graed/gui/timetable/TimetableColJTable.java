/*
 * Created on 16 janv. 2005
 *	Fen�tre d'emploi du temps
 *	Tests r�alis�s:
 *	-Drag and Drop
 *	-Fusion (redimenssionnement de cellules)
 *	-S�lection d'une seule JTable
 * 
 */
package graed.gui.timetable;

import graed.client.Client;
import graed.indisponibilite.IndisponibiliteInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableColumn;
/**
 * @author Nad�ge GONORD
 *
 */
public class TimetableColJTable extends JTable {
	private Hashtable list_ind;
	private TimetableDefaultTableModel tm;	/* Mod�le de donn�es associ� � la table */
	TimetableDefaultListSelectionModel l; /* Mod�le de s�lection partag� entre les diff�rentes tables */
	/**
	 * Constructeur de la classe
	 * R�alise le Drag and Drop
	 * D�finit le ColumnModel pour recalculer le nombre de colonnes
	 * D�finit la s�lection pour d�selection les cellules des autres tables
	 * D�finit le type de s�lection par colonne
	 * Ajoute toutes les colonnes
	 * @param arg0
	 */
	public TimetableColJTable(TimetableDefaultTableModel arg0,TimetableDefaultListSelectionModel l) {
		super(arg0);
		list_ind = new Hashtable(); 
		tm=arg0;
		/**
		 * S�lection par ligne non
		 */
		setRowSelectionAllowed(false);
		/**
		 * S�lection par colonne oui
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
		}
		
		public void mouseReleased(MouseEvent e){
			//if( e.getButton() == MouseEvent.BUTTON1 ) System.out.println("D�place");
			Container ct = TimetableColJTable.this.getParent();
			Point p=ct.getMousePosition(true);
			Component c = TimetableColJTable.this.getParent().getComponentAt(p);
			if( !(c instanceof TimetableColJTable ))return;
			TimetableColJTable table = (TimetableColJTable)c;
		
			int row= table.rowAtPoint(p);
			int col= table.columnAtPoint(p);
			Object o=TimetableColJTable.this.getValueAt(rowF,colF);
			if( o!= null) {
				int size = TimetableColJTable.this.getCellSize( rowF, colF );	
				//On ne d�place si la taille est trop grande
				if(size<=table.getColumnCount()-col){					
					try {
						IndisponibiliteInterface i=TimetableColJTable.this.removeIndispo(colF);
						int j=Integer.parseInt(table.getName())-Integer.parseInt(TimetableColJTable.this.getName());
						i.setDebut( new Date((i.getDebut().getTime()+j*(1000*60*60*24))));					
						table.addIndispo(i,col,size);	
						Client.getIndisponibiliteManager().updateIndiponibilite(i);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				TimetableColJTable.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				table.changeSelection(row,col,false,false);
				table.setCellSelectionEnabled(true);
				
			}
		}
        };
        
		addMouseListener(listener);
		
	}
	public void setPreferredSize(Dimension preferredSize){
		super.setPreferredSize(preferredSize);
		for(int i=0;i<getColumnCount();++i)
		{
			TableColumn c=getColumnModel().getColumn(i);
			c.setPreferredWidth(preferredSize.width/getColumnCount());
		}
	}
	/**
	 * Renvoie le nom de la table (jour)
	 */
	public String getName(){
		return tm.getName();
	}
	/**
	 * Renvoie la valeur � la ligne et colonne indiqu�e
	 */
	public Object getValueAt(int row, int column) {
		return tm.getValueAt(row, column);
	}
	/**
	 * Ajoute un nouveau cours (du texte obligatoirement)
	 * Ins�re un JTextArea dans la table
	 * @param o = texte!!!
	 * @param col
	 * @param size taille du cours (nombre de colonnes)
	 */
	public void addIndispo (IndisponibiliteInterface i,int col,int size){
		addI(i,col);
		JTextArea j = new JTextArea();
		try {
			j.setText(i.print());	
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.setOpaque(true);
		//Passer � la ligne suivante pour le texte
		j.setLineWrap(true);
		j.setWrapStyleWord(true);		
		//Gestion des erreurs de la taille de la cellule
		if(size>tm.getColumnCount()-col)size=tm.getColumnCount()-col;
		tm.setValueAt(j,col,size);
		TableColumn c=getColumnModel().getColumn(col);
		c.setPreferredWidth(size*c.getPreferredWidth()+size-1);
		
	}
	/**
	 * Supprime la donn�e � la colonne indiqu�e
	 * @param col
	 * @return
	 */
	public IndisponibiliteInterface removeIndispo (int col){
		int size = tm.getCellSize(0, col);
		Object o =tm.removeValueAt(col,size);
		TableColumn c=getColumnModel().getColumn(col);
		c.setPreferredWidth(c.getPreferredWidth()/size);
		return removeI(col);
	}
	public IndisponibiliteInterface removeI(int col){
		return (IndisponibiliteInterface) list_ind.remove(new Integer(col));
	}
	public void addI(IndisponibiliteInterface i,int col){
		list_ind.put(new Integer(col), i);
	}
	/**
	 * R�cup�re la taille du cours (nombre de colonnes)
	 * @param row
	 * @param col
	 * @return
	 */
	public int getCellSize( int row, int col) {
		return tm.getCellSize( row, col );
	}

	/**
	 * Permet la s�lection de plusieurs colonnes en d�selectionnant les colonnes
	 * des autres tables
	 */
	public void changeSelection(int arg0, int arg1, boolean arg2, boolean arg3) {
		l.clearAllTables();
		super.changeSelection(arg0, arg1, arg2, arg3);
	}
	
	
}
