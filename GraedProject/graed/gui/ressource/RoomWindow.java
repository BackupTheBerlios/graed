/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.ressource;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.event.RessourceEvent;
import graed.ressource.type.Room;
import graed.ressource.type.RoomInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
public RoomWindow(int state, RoomInterface r) throws InvalidStateException{
	super(state,r);
	nom=new JFormattedTextField();
	batiment=new JFormattedTextField();
	lieu=new JFormattedTextField();
	capacite=new JFormattedTextField("0");
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
	try {
		nom.setText(((RoomInterface) getInformation()).getNom());
		batiment.setText(((RoomInterface) getInformation()).getBatiment());
		lieu.setText(((RoomInterface) getInformation()).getLieu());
		capacite.setText(((RoomInterface) getInformation()).getCapacite()+"");
	} catch( RemoteException e ) {
		e.printStackTrace();
	}
}
/**
 * Open and fill the window
 */
public void OpenWindow(){
	/** Fenêtre d'affichage des données d'un professeur **/
	frame=new JFrame();
	Class clazz=RoomWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("classe.gif"));
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
	//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
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
			try {
				((RoomInterface) getInformation()).setNom(nom.getText());
				((RoomInterface) getInformation()).setBatiment(batiment.getText());
				((RoomInterface) getInformation()).setLieu(lieu.getText());
				((RoomInterface) getInformation()).setCapacite(Integer.parseInt(capacite.getText()));
				System.out.println(((RoomInterface) getInformation()));
				Client.getRessourceManager().updateRessource(((RoomInterface) getInformation()));
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
					"La salle ne peut être modifiée ",
					"Erreur",JOptionPane.ERROR_MESSAGE);	
			}
			frame.dispose();
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
			if(nom.getText()!=null &&
					lieu.getText()!=null && batiment.getText()!=null){
				try {
					RoomInterface room = (RoomInterface)Client.getRessourceManager().createRessource("Salle");
					room.setNom(nom.getText());
					room.setBatiment(batiment.getText());
					room.setCapacite(Integer.parseInt(capacite.getText()));
					room.setLieu(lieu.getText());
					setInformation(room);			
					System.out.println(((Room) getInformation()));
				
					
					RessourceManagerImpl.getInstance().addRessource(((Room) getInformation()));
				} catch (RemoteException e) {						
						JOptionPane.showMessageDialog(frame,
						"La salle ne peut être crée",
						"Erreur",JOptionPane.ERROR_MESSAGE);	
				}
				
			}
			else{
				JOptionPane.showMessageDialog(frame,
						"Veuillez renseigner tous les champs",
						"Attention",JOptionPane.INFORMATION_MESSAGE);
			}
			frame.dispose();
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
			try {
				String nom_salle = nom.getText().length()==0?null:nom.getText();
			String lieu_salle = lieu.getText().length()==0?null:lieu.getText();
			String batiment_salle = batiment.getText().length()==0?null:batiment.getText();
			String capacite_salle = capacite.getText().length()==0?null:capacite.getText();
			
			RoomInterface room = (RoomInterface)Client.getRessourceManager().createRessource("Salle");
			room.setNom(nom_salle);
			room.setBatiment(batiment_salle);
			room.setCapacite(Integer.parseInt(capacite_salle));
			room.setLieu(lieu_salle);
			
			setInformation(room);
			
			System.out.println(((RoomInterface) getInformation()));			
			Collection l=null;			
				
					l= (Collection) Client.getRessourceManager().getRessources(((RoomInterface) getInformation()));
				
			
			System.out.println(l);	
			frame.setEnabled(false);
			new ListRoomWindow(l).OpenWindow();
			frame.dispose();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
						"Le système de peut récuperer les salles",
						"Erreur",JOptionPane.ERROR_MESSAGE);
			}
		}		
	});
	return b;
	
}
/**
 * Création du bouton annuler
 * @return bouton
 */
protected JButton stop(){
	JButton b=new JButton("Annuler");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
		}		
	});
	return b;
}
/* (non-Javadoc)
 * @see graed.ressource.event.RessourceListener#ressourceDeleted(graed.ressource.event.RessourceEvent)
 */
public void ressourceDeleted(RessourceEvent re) {
	JOptionPane.showMessageDialog(frame,
			"La ressource a été supprimée",
			"Erreur",JOptionPane.ERROR_MESSAGE);	
	frame.dispose();
	
}
/* (non-Javadoc)
 * @see graed.ressource.event.RessourceListener#ressourceUpdated(graed.ressource.event.RessourceEvent)
 */
public void ressourceUpdated(RessourceEvent re) {
	JOptionPane.showMessageDialog(frame,
			"La ressource a été modifiée",
			"Erreur",JOptionPane.ERROR_MESSAGE);	
	frame.dispose();
	
}
/**
 * Test the class
 * @param args
 * @throws InvalidStateException
 */
public static void main (String[] args) throws InvalidStateException{
	/*Room r=new Room("test", null, 
			null, 0);
	Collection l=null;
	
		try {
			l= (Collection) RessourceManagerImpl.getInstance().getRessources(r);
		} catch (RemoteException e) {
			System.out.println("Ne peut recup la salle");
		}
	
	System.out.println(l);
	for (Iterator i=l.iterator();i.hasNext();){
		new RoomWindow(InformationWindow.MODIFY,((Room)i.next())).OpenWindow();
	}*/
	Room r=null;
	new RoomWindow(InformationWindow.SEARCH,r).OpenWindow();
}
}
