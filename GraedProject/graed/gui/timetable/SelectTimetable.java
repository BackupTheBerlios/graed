/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.timetable;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.JBackgroundPanel;
import graed.gui.renderer.RessourceListRenderer;
import graed.ressource.RessourceInterface;
import graed.util.Graphic;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

/**
 * @author Gonord Nadège
 *
 * The window for alter teachers
 */
public class SelectTimetable {

/**
 * Window
 */
private JPanel p;
private static int with=150;
private static int height=200;
private CreateMainFrame main;
/**
 * Champs contenus dans la fenêtre de sélection
 */
private JComboBox type;
private JComboBox ressource;
private JRadioButton semestre1;
private JRadioButton semestre2; 
private JRadioButton semestre3;
private JRadioButton autre;
private JDateChooser dateDebut;
private JDateChooser dateFin;

/**
 * Constructor which open the selection of a timetable
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public SelectTimetable(CreateMainFrame main) {
	this.main=main;
	
	Date d=new Date();
	Date f=new Date();
	
	p=new JBackgroundPanel( "graed/gui/timetable/icons/fond.png" );
	p.setSize(with,height);
	
	
	String[] ressTypes=new String[0];
	try {
		
		ressTypes=Client.getRessourceManager().getRessourcesTypes();
	} catch (RemoteException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	
	ressource = new JComboBox();
	ressource.setRenderer(new RessourceListRenderer());
	type = new JComboBox(ressTypes);
	type.addItemListener(new ItemListener(){

		public void itemStateChanged(ItemEvent e) {
			if(e.getItem()==type.getSelectedItem()){
				try {
					ressource.removeAllItems();
					Collection c=Client.getRessourceManager().getRessourcesByType((String)type.getSelectedItem());//Collection
					if(c!=null){
						for( Iterator it=c.iterator();it.hasNext();)
							ressource.addItem(it.next());
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
	});
	
	ButtonGroup group=new ButtonGroup();
	
	semestre1 = new JRadioButton("1er semestre");
	/*d="Deb s1";
	f="Fin s1";*/
	Graphic.makeTransparent(semestre1);
	group.add(semestre1);
	
	semestre2 = new JRadioButton("2ème semestre"); 
	/*d="Deb s2";
	f="Fin s2";*/
	group.add(semestre2);
	Graphic.makeTransparent(semestre2);
	
	semestre3 = new JRadioButton("3ème semestre");
	/*d="Deb s3";
	f="Fin s3";*/
	group.add(semestre3);
	Graphic.makeTransparent(semestre3);
	
	autre = new JRadioButton("autre");  
	group.add(autre);
	Graphic.makeTransparent(autre);
	
	dateDebut = new JDateChooser("dd/MM/yyyy",false);	
	dateFin = new JDateChooser("dd/MM/yyyy",false);
	
	
	/** Listener **/
	addSelectedListener(semestre1,new Date(104,8,01),new Date(104,11,31));
	addSelectedListener(semestre2,new Date(105,0,01),new Date(105,03,31));
	addSelectedListener(semestre3,new Date(105,4,01),new Date(105,8,31));
	
	autre.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(autre.isSelected()){
				dateDebut.setEnabled(true);
				dateDebut.setDate(new Date());
				dateFin.setEnabled(true);
				dateFin.setDate(new Date());
			}
			else{
				dateDebut.setEnabled(false);
				dateFin.setEnabled(false);
			}
		}
		
	});
}

/**
 * Affiche les dates du semestre sélectionné
 * @param jrb bouton radio sélectionné
 * @param d date de début du semestre
 * @param f date de fin du semestre
 */
private void addSelectedListener( final JRadioButton jrb,final Date d,final Date f){
	jrb.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(jrb.isSelected()){
				dateDebut.setDate(d);
				dateFin.setDate(f);
				dateDebut.setEnabled(false);
				dateFin.setEnabled(false);
			}
		}
		
	});
}
/**
 * Add a label and a texfield
 * @param p panel
 * @param c constraint
 * @param mask contrainst for the textfield
 * @param name the label
 */
private void addLine(JPanel p,GridBagConstraints c,JComponent jc,String name){
	c.gridx = 0;
    c.gridwidth = 1;    
    c.weightx=0; 
   
    p.add(new JLabel(name),c);
	
    c.gridx = 1;
	c.gridwidth = 1;
	c.weightx=1;
	
	
	/*if(mask!=null){
		MaskFormatter mf;		
		try {
			mf = new MaskFormatter (mask);
			tf=new JFormattedTextField(mf);			
			tf.setFocusLostBehavior(JFormattedTextField.PERSIST);
			System.out.println(mask);			
		} catch (ParseException e) {
			tf=new JFormattedTextField();
			System.out.println("mask null");
		}		
	}
	*/
	
	p.add(jc,c);
}
/**
 * Add the component for the window create and search
 * @param p panel
 * @param c constraint
 */
private void addJComponent(JPanel p,GridBagConstraints c){
	
	p.add(type,c);
	addLine(p,c,type, "Type : ");
	
	c.gridy = 1;
	addLine(p,c,ressource, "Ressource : ");
    
    c.gridy = 2;	
	c.gridx = 1;
    c.gridwidth = 2;    
    c.weightx=1; 
	p.add(semestre1,c);
	
	c.gridy = 3;	
	p.add(semestre2,c);
	
	c.gridy = 4;	
	p.add(semestre3,c);
	
	c.gridy = 5;	
	p.add(autre,c);
	
	c.gridy = 6;
	addLine(p,c,dateDebut, "Date de début : ");
	
	c.gridy = 7;
	addLine(p,c,dateFin,"Date de fin : ");
		
}
/**
 * Open and fill the window
 */
protected JPanel OpenWindow(){
	
	/** Ajout des composants à la fenêtre **/
	
	GridBagLayout l=new GridBagLayout();
	p.setLayout(l);
	GridBagConstraints c= new GridBagConstraints();
	
	/* Contraintes fixes */
	c.gridheight = 1;
	c.weighty=1;
	c.fill = GridBagConstraints.BOTH;
	c.insets=new Insets(1,1,1,1);
	addJComponent(p,c);
	c.gridx = 1;
	c.gridy = 8;
	p.add(search(),c);	
	return p;
		
}

/**
 * Création du bouton de recherche
 * @return bouton
 */
protected JButton search(){
	JButton b=new JButton("Chercher");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			/*Collection c=null;
			try {
				c=IndisponibiliteManagerImpl.getInstance().getIndisponibilites(
						(Ressource)ressource.getSelectedItem(),
						new java.sql.Date(dateDebut.getDate().getTime()),
						new java.sql.Date(dateFin.getDate().getTime()));
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(c);*/
			main.addTimetable((RessourceInterface)ressource.getSelectedItem(),
						new java.sql.Date(dateDebut.getDate().getTime()),
						new java.sql.Date(dateFin.getDate().getTime()));
			}		
	});
	return b;
	
}
/**
 * Test the class
 * @param args
 * @throws InvalidStateException
 */
public static void main (String[] args) throws InvalidStateException{
	JFrame frame=new JFrame();
	frame.setTitle("Consulter un emploi du temps");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/** Affichage de la fenêtre **/
	//frame.setContentPane(new SelectTimetable().OpenWindow());	
	frame.pack();
	frame.setVisible(true);
}
}
