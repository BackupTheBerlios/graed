/*
 * Classe de test d'interface
 */
package graed.gui.timetable;


import graed.ressource.Ressource;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * @author Dusk93
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateColTimetable {
	private final JFrame colTimetable;
	private Ressource r;
	
	public CreateColTimetable(Ressource r,String title, int start, int stop){
		this.r=r;
		colTimetable=new JFrame();
		colTimetable.setTitle(title);
		colTimetable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		colTimetable.setSize(900,600);
		GridBagLayout l=new GridBagLayout();
		
		colTimetable.setContentPane(CreateTables(start,stop));
		
		/* Affichage de l'interface */
		colTimetable.pack();
		colTimetable.setVisible(true);
	}

	/**
	 * Creation de la table affichant les heures
	 * @param start heure de depart
	 * @param stop heure de fin
	 * @return la table nouvellement crée
	 */
	private JTable CreateHoursTable(int start, int stop){
		/* Table affichant les heures */
		
		JTable hours =new JTable(1,(stop+1-start)*4);
		hours.setCellSelectionEnabled(true);
		hours.setFont(new Font("Dialog",Font.PLAIN,8));
		hours.setPreferredSize( new Dimension( (stop-start)*100,20 ));
		for(int i=start;i<=stop;++i){
			hours.setValueAt(i+"h",0,(i-start)*4);			
		}
		
		return hours;	
	}
	private JTable CreateDaysTable(){
		/* Table affichant les jours */
		JTable days =new JTable(5,1);
		days.setBackground(Color.LIGHT_GRAY);
		days.setEnabled(false);
		days.setPreferredSize( new Dimension( 100,170 ));
		days.setRowHeight(32);
		days.setValueAt("Lundi",0,0);
		days.setValueAt("Mardi",1,0);
		days.setValueAt("Mercredi",2,0);
		days.setValueAt("Jeudi",3,0);
		days.setValueAt("Vendredi",4,0);
		return days;
	}
	private TimetableColJTable CreateIndispoTable(String name, TimetableDefaultListSelectionModel mdl, int start, int stop){
		TimetableColJTable t= new TimetableColJTable(new TimetableDefaultTableModel(stop-start+1,name ),mdl);
		t.setPreferredSize( new Dimension( (stop-start)*100,30 ));
		return t;
	}
	private JPanel CreateTables(int start, int stop){
		JPanel p=new JPanel();
		GridBagLayout l=new GridBagLayout();
		
		p.setLayout(l);
		p.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		GridBagConstraints c= new GridBagConstraints();
		
		/* Table affichant les heures */
		JTable heure =CreateHoursTable(start, stop);	
		c.gridx = 1;
	    c.gridy = 0;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.weighty=1;
	    c.fill = GridBagConstraints.BOTH;
		p.add(heure,c);
		
		/* Table affichant les jours */
		JTable jour =CreateDaysTable();
		c.gridx = 0;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=0;
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
		p.add(jour,c);
	
		
		/* Tables affichant les cours */
		JPanel col=new JPanel();
		col.setLayout(new GridLayout(5,1));
		col.setPreferredSize( new Dimension( (stop-start)*100,170 ));
		TimetableDefaultListSelectionModel mdl=new TimetableDefaultListSelectionModel();
		
		   
		TimetableColJTable t1= CreateIndispoTable("lundi", mdl, start, stop);
		t1.addIndispo("bla1",1,20);
		col.add(t1);
		
		TimetableColJTable t2= CreateIndispoTable("mardi", mdl, start, stop);
		t2.addIndispo("bla2",5,1);
		col.add(t2);
	
		TimetableColJTable t3= CreateIndispoTable("mercredi", mdl, start, stop);
		t3.addIndispo("bla3",10,7);
		col.add(t3);
		
		TimetableColJTable t4= CreateIndispoTable("jeudi", mdl, start, stop);
		t4.addIndispo("bla4",2,7);
		col.add(t4);
		
		TimetableColJTable t5= CreateIndispoTable("vendredi", mdl, start, stop);
		t5.addIndispo("bla5",5,7);
		t5.addIndispo("bla6",4,3);
		t5.removeIndispo(4);
		col.add(t5);
		
		
		c.gridx = 1;
	    c.gridy = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.weightx=1;
	    c.fill = GridBagConstraints.BOTH;
		p.add(col,c);
		
		/* Table affichant les heures */
		JTable heure2 =CreateHoursTable(start, stop);
		c.gridx = 1;
	    c.gridy = 2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.BOTH;
		p.add(heure2,c);
		
		return p;
	}
	public static void main(String[] args) {
		new CreateColTimetable(null,"Test",8,13);
		
	}
}
