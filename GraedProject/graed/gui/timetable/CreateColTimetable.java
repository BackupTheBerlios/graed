
package graed.gui.timetable;



import graed.indisponibilite.IndisponibiliteInterface;
import graed.ressource.RessourceInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.hokage.swing.JBackgroundPanel;


/**
 * @author Nadège GONORD
 *
 * Création d'un JPanel contenant l'emploi du temps d'une ressource
 */
public class CreateColTimetable extends JBackgroundPanel{
	private Hashtable jourTable;
	private RessourceInterface r;
	private int start;
	private int stop;
	private boolean menu;
	private Color color;
	String title;
	
	/**
	 * Constructeur de la classe
	 * @param r Ressource dont on veut creer l'emploi du temps
	 * @param title titre de l'emploi du temps
	 * @param start heure de début
	 * @param stop heure de fin
	 */
	public CreateColTimetable(RessourceInterface r,String title, int start, int stop, boolean menu){
		super("graed/gui/timetable/icons/fond.png");
		this.r=r;
		color = new Color(0xffffed);
		jourTable=new Hashtable();		
		this.title=title;
		this.start = start;
		this.stop = stop;
		this.menu=menu;
		setSize((stop-start)*100+60,500);
		CreateTables();
	}
	/**
	 * Recupère le Jpanel contenant l'emploi du temps de la ressource
	 * @return JPanel contenant l'emploi du temps de la ressource
	 */
	/*public JPanel getTimetable(){
		return colTimetable;
	}*/
	/**
	 * Récupération du titre de l'emploi du temps
	 * @return titre de l'emploi du temps
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Creation de la table affichant les heures
	 * @param start heure de depart
	 * @param stop heure de fin
	 * @return la table nouvellement crée
	 */
	private JTable CreateHoursTable(){
		/* Table affichant les heures */
		
		JTable hours =new JTable(1,(stop+1-start)*4);
		hours.setBackground(color);
		hours.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		hours.setCellSelectionEnabled(true);
		hours.setEnabled(false);
		hours.setRowHeight(getHeight()/12);
		hours.setFont(new Font("Dialog",Font.PLAIN,8));
		hours.setPreferredSize( new Dimension( (stop-start)*100,getHeight()/12 ));
		
		for(int i=start;i<=stop;++i){
			hours.setValueAt(i+"h",0,(i-start)*4);			
		}
		
		return hours;	
	}
	/**
	 * Création de la table contenant les jour de la semaine
	 * @return la table contenant les jour de la semaine
	 */
	private JTable CreateDaysTable(){
		/* Table affichant les jours */
		JTable days =new JTable(5,1);
		days.setOpaque(false);
		days.setBackground(color);
		days.setGridColor(Color.BLACK);		
		days.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		days.setEnabled(false);
		days.setPreferredSize( new Dimension( 60,getHeight()*5/6 ));
		days.setRowHeight(getHeight()/6);
		days.setValueAt("Lundi",0,0);
		days.setValueAt("Mardi",1,0);
		days.setValueAt("Mercredi",2,0);
		days.setValueAt("Jeudi",3,0);
		days.setValueAt("Vendredi",4,0);
		return days;
	}
	/**
	 * Création d'une table correspondant à un jour de la semaine
	 * @param name nom de la table
	 * @param mdl modèle de sélection
	 * @param start heure de début
	 * @param stop heure de fin
	 * @return table correspondant à un jour de la semaine
	 */
	private TimetableColJTable CreateIndispoTable(String name, TimetableDefaultListSelectionModel mdl){
		TimetableColJTable t= new TimetableColJTable(new TimetableDefaultTableModel(stop-start+1,name ),mdl,menu);
		t.setOpaque(false);
		t.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		t.setPreferredSize( new Dimension( (stop-start)*100,getHeight()/6 ));
		t.setRowHeight(getHeight()/6);
		
		jourTable.put(name,t);
		return t;
	}
	/**
	 * Création de toutes les tables constituant un emploi du temps
	 * @param start heure de début
	 * @param stop heure de fin
	 */
	private void CreateTables(){
		GridBagLayout l=new GridBagLayout();
		
		setLayout(l);
		setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		setBackground(color);
		setOpaque(false);
		GridBagConstraints c= new GridBagConstraints();
		
		/* Table affichant les heures */
		JTable heure =CreateHoursTable();
		heure.setOpaque(false);
		c.gridx = 1;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.weighty=1;
	    c.fill = GridBagConstraints.BOTH;
	    add(heure,c);
		
		/* Table affichant les jours */
		JTable jour =CreateDaysTable();
		jour.setOpaque(false);
		c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weighty=0.2;
	    c.fill = GridBagConstraints.BOTH;
	    
	    add(jour,c);
	
		
		/* Tables affichant les cours */
		JPanel col=new JBackgroundPanel("graed/gui/timetable/icons/fond.png");
		col.setLayout(new GridLayout(5,1));
		col.setPreferredSize( new Dimension( (stop-start)*100,getHeight()*5/6  ));
		TimetableDefaultListSelectionModel mdl=new TimetableDefaultListSelectionModel();
		
		   
		TimetableColJTable t1= CreateIndispoTable(GregorianCalendar.MONDAY+"", mdl);
		col.add(t1);
		
		TimetableColJTable t2= CreateIndispoTable(GregorianCalendar.TUESDAY+"", mdl);
		col.add(t2);
	
		TimetableColJTable t3= CreateIndispoTable(GregorianCalendar.WEDNESDAY+"", mdl);
		col.add(t3);
		
		TimetableColJTable t4= CreateIndispoTable(GregorianCalendar.THURSDAY+"", mdl);
		col.add(t4);
		
		TimetableColJTable t5= CreateIndispoTable(GregorianCalendar.FRIDAY+"", mdl);
		col.add(t5);
		
		
		c.gridx = 1;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.fill = GridBagConstraints.BOTH;
	    add(col,c);
		
		/* Table affichant les heures */
		JTable heure2 =CreateHoursTable();
		c.gridx = 1;
	    c.gridy = 2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.BOTH;
	    add(heure2,c);
		
	}
	/**
	 * Ajouter une indisponibilité à l'emploi du temps de la ressource
	 * @param i l'indisponibilitée à ajouter
	 */
	public void addIndispo(IndisponibiliteInterface i){
		try {
			GregorianCalendar gcal=new GregorianCalendar();
			gcal.setTime(i.getDebut());		
			
			int celldebut =(i.getHdebut().getHours()-start)*4+(i.getHdebut().getMinutes()/15);
			int nbcell=i.getDuree()/15;						
			
			System.out.println("Indispo: "+i.print());
			if(((TimetableColJTable)jourTable.get(gcal.get(GregorianCalendar.DAY_OF_WEEK)+""))!=null)
			((TimetableColJTable)jourTable.get(gcal.get(GregorianCalendar.DAY_OF_WEEK)+"")).addIndispo(i,
				celldebut,nbcell,true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Vide l'emploi du temps
	 *
	 */
	public void refresh(){
		for(Iterator it=jourTable.values().iterator();it.hasNext();)
			((TimetableColJTable) it.next()).clear();
	}
	/**
	 * Vide l'emploi du temps
	 *
	 */
	public void clear(){
		for(Iterator it=jourTable.values().iterator();it.hasNext();)
			((TimetableColJTable) it.next()).clear();
	}
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f=new JFrame("Test Emploi du temps");		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tp=new JTabbedPane();
		CreateColTimetable time1=new CreateColTimetable(null,"Emploi du temps n°1",8,15,true);
		tp.add(time1.getTitle(),time1);
		CreateColTimetable time2=new CreateColTimetable(null,"Emploi du temps n°2",8,15,true);
		tp.add(time2.getTitle(),time2);
		f.setContentPane(tp);
		/* Affichage de l'interface */
		f.pack();
		f.setVisible(true);
	}
	/**
	 * @return Returns the start.
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start The start to set.
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return Returns the stop.
	 */
	public int getStop() {
		return stop;
	}
	/**
	 * @param stop The stop to set.
	 */
	public void setStop(int stop) {
		this.stop = stop;
	}
	
	
}
