/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.ressource;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Gonord Nadège
 *
 * The window for alter teachers
 */
public class RoomWindow extends InformationWindow{

/**
 * Window
 */
private JFrame frame;
private static int with=200;
private static int height=200;
/**
 * TextField
 */
private JFormattedTextField nom;
private JFormattedTextField batiment;
private JFormattedTextField lieu;
private JFormattedTextField capacite; 

/**
 * Constructor which open the teacher window
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public RoomWindow(int state, Room r) throws InvalidStateException{
	super(state,r);
}
/**
 * Add a label and a texfield
 * @param p panel
 * @param c constraint
 * @param mask contrainst for the textfield
 * @param name the label
 */
private void addLine(JPanel p,GridBagConstraints c,String mask,JFormattedTextField tf,String name){
	c.gridx = 0;
    c.gridwidth = 1;    
    c.weightx=0; 
   
    p.add(new JLabel(name,SwingConstants.RIGHT),c);
	
    c.gridx = 1;
	c.gridwidth = 2;
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
	tf=new JFormattedTextField();
	
	if(isSee()){
		tf.setEnabled(false);
	}
	p.add(tf,c);
}
/**
 * Add the component for the window create and search
 * @param p panel
 * @param c constraint
 */
private void addJComponent(JPanel p,GridBagConstraints c){
	String mask="UUUUUUUUUUUUUUUUUUUUUUUU";
	c.gridy = 0;
	addLine(p,c,mask,nom, "Nom : ");
	c.gridy = 1;
	addLine(p,c,mask,lieu,"Lieu : ");
	
	mask="*****";
	c.gridy = 2;		
	addLine(p,c,mask,batiment,"Batiment : ");
	
	/* Phone */
	mask="##.##.##.##.##";
	c.gridy = 3;
	addLine(p,c,mask,capacite,"Capacite : ");
	
	c.gridy = 4;
	c.gridx = 0;
	
	if(isCreate()){
		p.add(create(),c);
	}
	else if(isSearch()){
		p.add(search(),c);
	}
	else if(isModify()){
		p.add(modify(),c);
	}	
}
/**
 * Fill the component for the window see and modify
 * @param p panel
 * @param c constraint
 */
private void FillComponent(){
	nom.setText(((Room) getInformation()).getNom());
	batiment.setText(((Room) getInformation()).getBatiment());
	lieu.setText(((Room) getInformation()).getLieu());
	capacite.setText(((Room) getInformation()).getCapacite()+"");
	
}
/**
 * Open and fill the window
 */
protected void OpenWindow(){
	/** Fenêtre d'affichage des données d'un professeur **/
	frame=new JFrame();
	Class clazz=RoomWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	frame.setIconImage(i.getImage());
	if(isSee()){
		frame.setTitle("Consulter une salle");
	}
	else if(isModify()){
		frame.setTitle("Modifier une salle");
	}
	else if(isSearch()){
		frame.setTitle("Rechercher une salle");
	}
	else if(isCreate()){
		frame.setTitle("Créer une salle");
	}
	frame.setSize(with,height);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/** Ajout des composants à la fenêtre **/
	JPanel p=new JPanel();
	GridBagLayout l=new GridBagLayout();
	p.setLayout(l);
	GridBagConstraints c= new GridBagConstraints();
	
	/* Contraintes fixes */
	c.gridheight = 1;
	c.weighty=1;
	c.fill = GridBagConstraints.BOTH;
	c.insets=new Insets(1,1,1,1);
	addJComponent(p,c);
	if(isModify() || isSee()){
		FillComponent();
	}
	
	c.gridx = 2;
	p.add(stop(),c);	
	
	/** Affichage de la fenêtre **/
	frame.setContentPane(p);	
	frame.setVisible(true);
	
}

/**
 * Création du bouton modifier
 * @return bouton
 */
protected JButton modify(){
	JButton b=new JButton("Modifier");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			((Room) getInformation()).setNom(nom.getText());
			((Room) getInformation()).setBatiment(batiment.getText());
			((Room) getInformation()).setLieu(lieu.getText());
			((Room) getInformation()).setCapacite(Integer.parseInt(capacite.getText()));
			System.out.println(((Teacher) getInformation()));
			System.exit(0);
		}		
	});
	return b;
	
}

/**
 * Création du bouton creer
 * @return bouton
 */
protected JButton create(){
	JButton b=new JButton("Creer");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			setInformation(new Room(nom.getText(),lieu.getText(),
					batiment.getText(),Integer.parseInt(capacite.getText())));
			System.out.println(((Teacher) getInformation()));
			System.exit(0);
		}		
	});
	return b;
	
}
/**
 * Création du bouton de recherche
 * @return bouton
 */
protected JButton search(){
	JButton b=new JButton("Chercher");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			setInformation(new Room(nom.getText(),lieu.getText(),
					batiment.getText(),Integer.parseInt(capacite.getText())));
			System.out.println(((Teacher) getInformation()));
			System.exit(0);
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
	Room r=new Room("3B118", "Bat Copernic", 
			"Copernic", 15);
	new RoomWindow(InformationWindow.SEARCH,r);
}
}
