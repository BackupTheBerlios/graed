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
	private final JPanel colTimetable;
	private Ressource r;
	String title;
	
	public CreateColTimetable(Ressource r,String title, int start, int stop){
		this.r=r;
		colTimetable=new JPanel();
		colTimetable.setSize((stop-start)*100+60,300);
		CreateTables(start,stop);		
	}
	public JPanel getTimetable(){
		return colTimetable;
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
		hours.setBackground(Color.LIGHT_GRAY);
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
	private JTable CreateDaysTable(){
		/* Table affichant les jours */
		JTable days =new JTable(5,1);
		days.setBackground(Color.LIGHT_GRAY);
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
	private TimetableColJTable CreateIndispoTable(String name, TimetableDefaultListSelectionModel mdl, int start, int stop){
		TimetableColJTable t= new TimetableColJTable(new TimetableDefaultTableModel(stop-start+1,name ),mdl);
		t.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		t.setPreferredSize( new Dimension( (stop-start)*100,colTimetable.getHeight()/6 ));
		t.setRowHeight(colTimetable.getHeight()/6);
		
		return t;
	}
	private void CreateTables(int start, int stop){
		GridBagLayout l=new GridBagLayout();
		
		colTimetable.setLayout(l);
		colTimetable.setBorder(BorderFactory.createLineBorder(Color.BLACK) );
		colTimetable.setBackground(Color.LIGHT_GRAY);
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
		
		   
		TimetableColJTable t1= CreateIndispoTable("lundi", mdl, start, stop);
		t1.addIndispo("bla1",1,15);
		col.add(t1);
		
		TimetableColJTable t2= CreateIndispoTable("mardi", mdl, start, stop);
		t2.addIndispo("bla2",5,1);
		col.add(t2);
	
		TimetableColJTable t3= CreateIndispoTable("mercredi", mdl, start, stop);
		t3.addIndispo("bla3",10,7);
		col.add(t3);
		
		TimetableColJTable t4= CreateIndispoTable("jeudi", mdl, start, stop);
		t4.addIndispo("toto aime titi qui aime tata mais tutu n'est pas là",2,7);
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
	    colTimetable.add(col,c);
		
		/* Table affichant les heures */
		JTable heure2 =CreateHoursTable(start, stop);
		c.gridx = 1;
	    c.gridy = 2;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.fill = GridBagConstraints.BOTH;
	    colTimetable.add(heure2,c);
		
	}
	public static void main(String[] args) {
		JFrame f=new JFrame("Test Emploi du temps");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(new CreateColTimetable(null,"Emploi du temps",8,15).getTimetable());
		/* Affichage de l'interface */
		f.pack();
		f.setVisible(true);
	}
}
