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
import graed.ressource.type.Materiel;
import graed.ressource.type.MaterielInterface;

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
public class MaterielWindow extends InformationWindow{

/**
 * Window
 */
private JFrame frame;
private static int with=200;
private static int height=100;
/**
 * TextField
 */
private JFormattedTextField name;
private JFormattedTextField type;

/**
 * Constructor which open the teacher window
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public MaterielWindow(int state, MaterielInterface m) throws InvalidStateException{
	super(state,m);
	name=new JFormattedTextField();
	type=new JFormattedTextField();
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
	addLine(p,c,mask,name, "Nom : ");
	mask="UUUUUUUUUUUUUUUUUUUUUUUU";
	c.gridy = 1;
	addLine(p,c,mask,type,"Type : ");
	
	
	c.gridy = 2;
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
		name.setText(((MaterielInterface) getInformation()).getName());
		type.setText(((MaterielInterface) getInformation()).getTypeMateriel());
	} catch (RemoteException e) {
		e.printStackTrace();
	}
}
/**
 * Open and fill the window
 */
public void OpenWindow(){
	/** Fenêtre d'affichage des données d'un professeur **/
	frame=new JFrame();
	Class clazz=MaterielWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	frame.setIconImage(i.getImage());
	if(isSee()){
		frame.setTitle("Consulter un materiel");
	}
	else if(isModify()){
		frame.setTitle("Modifier un materiel");
	}
	else if(isSearch()){
		frame.setTitle("Rechercher un materiel");
	}
	else if(isCreate()){
		frame.setTitle("Créer un materiel");
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
				((MaterielInterface) getInformation()).setName(name.getText());
				((MaterielInterface) getInformation()).setTypeMateriel(type.getText());
				System.out.println(((Materiel) getInformation()));			
				
				Client.getRessourceManager().updateRessource(((MaterielInterface) getInformation()));
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
					"Le matériel ne peut être modifié ",
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
			if(name.getText()!=null && type.getText()!=null){	
				try {
					setInformation(new Materiel(name.getText(),type.getText()));			
				System.out.println(((MaterielInterface) getInformation()));				
					
						Client.getRessourceManager().addRessource(((MaterielInterface) getInformation()));
					} catch (RemoteException e) {						
						JOptionPane.showMessageDialog(frame,
						"Le matériel ne peut être cré",
						"Erreur",JOptionPane.ERROR_MESSAGE);	
						e.printStackTrace();
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
				String name_materiel = name.getText().length()==0?null:name.getText();
				String type_materiel = type.getText().length()==0?null:type.getText();
				MaterielInterface mi = (MaterielInterface)Client.getRessourceManager().createRessource("Materiel");
				mi.setName(name_materiel);
				mi.setTypeMateriel(type_materiel);
				setInformation(mi);			
				System.out.println(((MaterielInterface) getInformation()));			
				Collection l=null;			
					l= (Collection) Client.getRessourceManager().getRessources(((MaterielInterface) getInformation()));
							
				System.out.println(l);	
				frame.setEnabled(false);
				new ListMaterielWindow(l).OpenWindow();
				frame.dispose();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
						"Le système de peut récuperer les materiels",
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
	/*Materiel r=new Materiel("mat", null);
	Collection l=null;
	
		try {
			l= (Collection) RessourceManagerImpl.getInstance().getRessources(r);
		} catch (RemoteException e) {
			System.out.println("Ne peut recup le matériel");
		}
	
	System.out.println(l);
	for (Iterator i=l.iterator();i.hasNext();){
		new MaterielWindow(InformationWindow.SEE,((Materiel)i.next())).OpenWindow();
	}*/
	Materiel m=null;
	new MaterielWindow(InformationWindow.SEARCH,m).OpenWindow();
}
}
