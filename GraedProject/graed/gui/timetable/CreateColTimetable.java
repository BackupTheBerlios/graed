
package graed.gui.timetable;


import graed.indisponibilite.Indisponibilite;
import graed.ressource.Ressource;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;


/**
 * @author Nadège GONORD
 *
 * Création d'un JPanel contenant l'emploi du temps d'une ressource
 */
public class CreateColTimetable {
	private final JPanel colTimetable;
	private Hashtable jourTable;
	private Ressource r;
	private int start;
	private int stop;
	private Color color;
	String title;
	
	/**
	 * Constructeur de la classe
	 * @param r Ressource dont on veut creer l'emploi du temps
	 * @param title titre de l'emploi du temps
	 * @param start heure de début
	 * @param stop heure de fin
	 */
	public CreateColTimetable(Ressource r,String title, int start, int stop){
		this.r=r;
		color = Color.LIGHT_GRAY;
		jourTable=new Hashtable();
		this.title=title;
		this.start = start;
		this.stop = stop;
		colTimetable=new JPanel();
		colTimetable.setSize((stop-start)*100+60,300);
		CreateTables();		
	}
	/**
	 * Recupère le Jpanel contenant l'emploi du temps de la ressource
	 * @return JPanel contenant l'emploi du temps de la ressource
	 */
	public JPanel getTimetable(){
		return colTimetable;
	}
	/**
	 * Récupération du titre de l'emploi du temps
	 * @return titre de l'emploi du temps
	 */
	public String getTitle(){
		return title;
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
		hours.setRowHeight(colTimetable.getHeight()/12);
		hours.setFont(new Font("Dialog",Font.PLAIN,8));
		hours.setPreferredSize( new Dimension( (stop-start)*100,colTimetable.getHeight()/12 ));
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
		days.setBackground(color);
		days.setGridColor(Color.BLACK);		
		days.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		days.setEnabled(false);
		days.setPreferredSize( new Dimension( 60,colTimetable.getHeight()*5/6 ));
		days.setRowHeight(colTimetable.getHeight()/6);
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
		TimetableColJTable t= new TimetableColJTable(new TimetableDefaultTableModel(stop-start+1,name ),mdl);
		t.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		t.setPreferredSize( new Dimension( (stop-start)*100,colTimetable.getHeight()/6 ));
		t.setRowHeight(colTimetable.getHeight()/6);
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
		
		colTimetable.setLayout(l);
		colTimetable.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		colTimetable.setBackground(color);
		GridBagConstraints c= new GridBagConstraints();
		
		/* Table affichant les heures */
		JTable heure =CreateHoursTable();	
		c.gridx = 1;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.weighty=1;
	    c.fill = GridBagConstraints.BOTH;
	    colTimetable.add(heure,c);
		
		/* Table affichant les jours */
		JTable jour =CreateDaysTable();
		c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weighty=0.2;
	    c.fill = GridBagConstraints.BOTH;
	    
	    colTimetable.add(jour,c);
	
		
		/* Tables affichant les cours */
		JPanel col=new JPanel();
		col.setLayout(new GridLayout(5,1));
		col.setPreferredSize( new Dimension( (stop-start)*100,colTimetable.getHeight()*5/6  ));
		TimetableDefaultListSelectionModel mdl=new TimetableDefaultListSelectionModel();
		
		   
		TimetableColJTable t1= CreateIndispoTable(GregorianCalendar.MONDAY+"", mdl);
		//t1.addIndispo("bla1",1,15);
		col.add(t1);
		
		TimetableColJTable t2= CreateIndispoTable(GregorianCalendar.TUESDAY+"", mdl);
		//t2.addIndispo("bla2",5,1);
		col.add(t2);
	
		TimetableColJTable t3= CreateIndispoTable(GregorianCalendar.WEDNESDAY+"", mdl);
		//t3.addIndispo("bla3",10,7);
		col.add(t3);
		
		TimetableColJTable t4= CreateIndispoTable(GregorianCalendar.THURSDAY+"", mdl);
		//t4.addIndispo("toto aime titi qui aime tata mais tutu n'est pas là",2,7);
		col.add(t4);
		
		TimetableColJTable t5= CreateIndispoTable(GregorianCalendar.FRIDAY+"", mdl);
		//t5.addIndispo("bla5",5,7);
		//t5.addIndispo("bla6",4,3);
		//t5.removeIndispo(4);
		col.add(t5);
		
		
		c.gridx = 1;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.fill = GridBagConstraints.BOTH;
	    colTimetable.add(col,c);
		
		/* Table affichant les heures */
		JTable heure2 =CreateHoursTable();
		c.gridx = 1;
	    c.gridy = 2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.BOTH;
	    colTimetable.add(heure2,c);
		
	}
	/**
	 * Ajouter une indisponibilité à l'emploi du temps de la ressource
	 * @param i l'indisponibilitée à ajouter
	 */
	public void addIndispo(Indisponibilite i){
		GregorianCalendar gcal=new GregorianCalendar();
		gcal.setTime(i.getDebut());
		System.out.println(gcal.get(GregorianCalendar.DAY_OF_WEEK));
		int celldebut =(i.getHdebut().getHours()-start)*4+(i.getHdebut().getMinutes()/15);
		int nbcell=i.getDuree()/15;
		System.out.println("("+celldebut+","+nbcell+")");
		((TimetableColJTable)jourTable.get(gcal.get(GregorianCalendar.DAY_OF_WEEK)+"")).addIndispo(
				i.getLibelle(),celldebut,nbcell);
	}
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f=new JFrame("Test Emploi du temps");		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JTabbedPane tp=new JTabbedPane();
		CreateColTimetable time1=new CreateColTimetable(null,"Emploi du temps n°1",8,15);
		tp.add(time1.getTitle(),time1.getTimetable());
		CreateColTimetable time2=new CreateColTimetable(null,"Emploi du temps n°2",8,15);
		tp.add(time2.getTitle(),time2.getTimetable());
		f.setContentPane(tp);
		/* Affichage de l'interface */
		f.pack();
		f.setVisible(true);
	}
}
