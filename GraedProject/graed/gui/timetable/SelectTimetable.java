/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.timetable;

import graed.exception.InvalidStateException;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
private static int with=200;
private static int height=200;
/**
 * Champs contenus dans la fenêtre de sélection
 */
private JComboBox type;
private JComboBox ressource;
private JRadioButton semestre1;
private String dateDebutS1;
private String dateFinS1;
private JRadioButton semestre2; 
private String dateDebutS2;
private String dateFinS2;
private JRadioButton semestre3;
private String dateDebutS3;
private String dateFinS3;
private JRadioButton autre;
private JFormattedTextField dateDebut;
private JFormattedTextField dateFin;

/**
 * Constructor which open the selection of a timetable
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public SelectTimetable() {
	p=new JPanel();
	p.setSize(with,height);
	
	type = new JComboBox();
	type.addItem("Professeur");
	type.addItem("Salle");
	type.addItem("Formation");
	type.addItem("Matériel");
	
	ressource = new JComboBox();
	
	ButtonGroup group=new ButtonGroup();
	
	semestre1 = new JRadioButton("1er semestre");
	dateDebutS1="Deb s1";
	dateFinS1="Fin s1";
	group.add(semestre1);
	
	semestre2 = new JRadioButton("2ème semestre"); 
	dateDebutS2="Deb s2";
	dateFinS2="Fin s2";
	group.add(semestre2);
	
	semestre3 = new JRadioButton("3ème semestre");
	dateDebutS3="Deb s3";
	dateFinS3="Fin s3";
	group.add(semestre3);
	
	autre = new JRadioButton("autre");  
	group.add(autre);
	
	dateDebut = new JFormattedTextField();	
	dateFin = new JFormattedTextField();
	
	
	/** Listener **/
	semestre1.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(semestre1.isSelected()){
				dateDebut.setText(dateDebutS1);
				dateFin.setText(dateFinS1);
			}
		}
		
	});
	semestre2.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(semestre2.isSelected()){
				dateDebut.setText(dateDebutS2);
				dateFin.setText(dateFinS2);
			}
		}
		
	});
	semestre3.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(semestre3.isSelected()){
				dateDebut.setText(dateDebutS3);
				dateFin.setText(dateFinS3);
			}
		}
		
	});
	autre.addChangeListener(new ChangeListener(){

		public void stateChanged(ChangeEvent e) {
			if(autre.isSelected()){
				dateDebut.setEnabled(true);
				dateDebut.setText("");
				dateFin.setEnabled(true);
				dateFin.setText("");
			}
			else{
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
	p.add(open(),c);	
	return p;
		
}

/**
 * Création du bouton de recherche
 * @return bouton
 */
protected JButton open(){
	JButton b=new JButton("Visualiser");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
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
	frame.setContentPane(new SelectTimetable().OpenWindow());	
	frame.pack();
	frame.setVisible(true);
}
}
