/*
 * Created on 21 f�vr. 2005
 *
 */
package graed.gui.ressource;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Materiel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Gonord Nad�ge
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
public MaterielWindow(int state, Materiel m) throws InvalidStateException{
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
	name.setText(((Materiel) getInformation()).getName());
	type.setText(((Materiel) getInformation()).getTypeMateriel());
	
}
/**
 * Open and fill the window
 */
public void OpenWindow(){
	/** Fen�tre d'affichage des donn�es d'un professeur **/
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
		frame.setTitle("Cr�er un materiel");
	}
	frame.setSize(with,height);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/** Ajout des composants � la fen�tre **/
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
	
	/** Affichage de la fen�tre **/
	frame.setContentPane(p);	
	frame.setVisible(true);
	
}

/**
 * Cr�ation du bouton modifier
 * @return bouton
 */
protected JButton modify(){
	JButton b=new JButton("Modifier");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			((Materiel) getInformation()).setName(name.getText());
			((Materiel) getInformation()).setTypeMateriel(type.getText());
			System.out.println(((Materiel) getInformation()));			
				try {
					RessourceManagerImpl.getInstance().updateRessource(((Materiel) getInformation()));
				} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
					"Le mat�riel ne peut �tre modifi� ",
					"Erreur",JOptionPane.ERROR_MESSAGE);	
				}
			System.exit(0);
		}		
	});
	return b;
	
}

/**
 * Cr�ation du bouton creer
 * @return bouton
 */
protected JButton create(){
	JButton b=new JButton("Creer");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(name.getText()!=null && type.getText()!=null){	
				setInformation(new Materiel(name.getText(),type.getText()));			
				System.out.println(((Materiel) getInformation()));				
					try {
						RessourceManagerImpl.getInstance().addRessource(((Materiel) getInformation()));
					} catch (RemoteException e) {						
						JOptionPane.showMessageDialog(frame,
						"Le mat�riel ne peut �tre cr�",
						"Erreur",JOptionPane.ERROR_MESSAGE);	
						e.printStackTrace();
					}
				
			}
			else{
				JOptionPane.showMessageDialog(frame,
						"Veuillez renseigner tous les champs",
						"Attention",JOptionPane.INFORMATION_MESSAGE);
			}
			System.exit(0);
		}		
	});
	return b;
	
}
/**
 * Cr�ation du bouton de recherche
 * @return bouton
 */
protected JButton search(){
	JButton b=new JButton("Chercher");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			String name_materiel = name.getText().length()==0?null:name.getText();
			String type_materiel = type.getText().length()==0?null:type.getText();
			setInformation(new Materiel(name_materiel,type_materiel));			
			System.out.println(((Materiel) getInformation()));			
			Collection l=null;			
				try {
					l= (Collection) RessourceManagerImpl.getInstance().getRessources(((Materiel) getInformation()));
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame,
							"Le syst�me de peut r�cuperer les materiels",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			
			System.out.println(l);			
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
	/*Materiel r=new materiel("m", null);
	Collection l=null;
	
		try {
			l= (Collection) RessourceManagerImpl.getInstance().getRessources(r);
		} catch (RemoteException e) {
			System.out.println("Ne peut recup le mat�riel");
		}
	
	System.out.println(l);
	for (Iterator i=l.iterator();i.hasNext();){
		new MaterielWindow(InformationWindow.MODIFY,((Room)i.next())).OpenWindow();
	}*/
	Materiel m=null;
	new MaterielWindow(InformationWindow.CREATE,m).OpenWindow();
}
}