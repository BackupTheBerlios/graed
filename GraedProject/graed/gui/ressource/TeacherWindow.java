/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.ressource;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.event.RessourceEvent;
import graed.ressource.type.TeacherInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
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
public class TeacherWindow extends InformationWindow implements Serializable{

/**
 * Window
 */
private JFrame frame;
private static int with=300;
private static int height=200;
/**
 * TextField
 */
private JFormattedTextField name;
private JFormattedTextField firstName;
private JFormattedTextField office;
private JFormattedTextField phone; 
private JFormattedTextField email;

/**
 * Constructor which open the teacher window
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public TeacherWindow(int state, TeacherInterface t) throws InvalidStateException{
	super(state,t);
	name = new JFormattedTextField();
	firstName = new JFormattedTextField();
	office = new JFormattedTextField();
	phone = new JFormattedTextField(); 
	email = new JFormattedTextField();
	if(state==InformationWindow.MODIFY || state==InformationWindow.SEE){
		try {
			Client.getRessourceManager().registerForNotification(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
	}
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
	c.gridy = 1;
	addLine(p,c,mask,firstName,"Prénom : ");
	
	mask="*****";
	c.gridy = 2;		
	addLine(p,c,mask,office,"Bureau : ");
	
	/* Phone */
	mask="##.##.##.##.##";
	c.gridy = 3;
	addLine(p,c,mask,phone,"Téléphone : ");
	
	mask="************************";
	c.gridy = 4;
	addLine(p,c,mask,email,"Courriel : ");
	
	c.gridy = 5;
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
		name.setText(((TeacherInterface) getInformation()).getName());	
		firstName.setText(((TeacherInterface) getInformation()).getFirstName());
		office.setText(((TeacherInterface) getInformation()).getOffice());
		phone.setText(((TeacherInterface) getInformation()).getPhone());
		email.setText(((TeacherInterface) getInformation()).getEmail());
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
/**
 * Open and fill the window
 */
public void OpenWindow(){
	/** Fenêtre d'affichage des données d'un professeur **/
	frame=new JFrame();
	Class clazz=TeacherWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	frame.setIconImage(i.getImage());
	if(isSee()){
		frame.setTitle("Consulter un professeur");
	}
	else if(isModify()){
		frame.setTitle("Modifier un professeur");
	}
	else if(isSearch()){
		frame.setTitle("Rechercher un professeur");
	}
	else if(isCreate()){
		frame.setTitle("Créer un professeur");
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
			try {
				((TeacherInterface) getInformation()).setName(name.getText());			
				((TeacherInterface) getInformation()).setFirstName(firstName.getText());
				((TeacherInterface) getInformation()).setOffice(office.getText());
				((TeacherInterface) getInformation()).setPhone(phone.getText());
				((TeacherInterface) getInformation()).setEmail(email.getText());
				System.out.println(((TeacherInterface) getInformation()));			
				Client.getRessourceManager().updateRessource(((TeacherInterface) getInformation()));
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
				"Le professeur ne peut être modifiée ",
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
			if(name.getText()!=null &&
					firstName.getText()!=null && email.getText()!=null){
					try {
						TeacherInterface ti=(TeacherInterface) Client.getRessourceManager().createRessource("Professeur");
						setInformation(ti);		
						((TeacherInterface) getInformation()).setName(name.getText());			
						((TeacherInterface) getInformation()).setFirstName(firstName.getText());
						((TeacherInterface) getInformation()).setOffice(office.getText());
						((TeacherInterface) getInformation()).setPhone(phone.getText());
						((TeacherInterface) getInformation()).setEmail(email.getText());
						System.out.println(((TeacherInterface) getInformation()));	
						Client.getRessourceManager().addRessource(((TeacherInterface) getInformation()));
					} catch (RemoteException e) {						
						JOptionPane.showMessageDialog(frame,
						"Le professeur ne peut être crée",
						"Erreur",JOptionPane.ERROR_MESSAGE);	
					}
				
			}
			else{
				JOptionPane.showMessageDialog(frame,
						"Veuillez renseigner les champs nom, prenom et courriel",
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
				String name_prof = name.getText().length()==0?null:name.getText();
				String firstName_prof = firstName.getText().length()==0?null:firstName.getText();
				String office_prof = office.getText().length()==0?null:office.getText();
				String phone_prof = phone.getText().length()==0?null:phone.getText();
				String email_prof = email.getText().length()==0?null:email.getText();
				
				TeacherInterface ti=(TeacherInterface) Client.getRessourceManager().createRessource("Professeur");
				//setInformation(ti);		
				ti.setName(name_prof);			
				ti.setFirstName(firstName_prof);
				ti.setOffice(office_prof);
				ti.setPhone(phone_prof);
				ti.setEmail(email_prof);
				System.out.println("--------->"+ti.print());	
				
				Collection l=null;			
				l= (Collection) Client.getRessourceManager().getRessources(ti);
				System.out.println("List:"+l);	
				frame.setEnabled(false);
				new ListTeacherWindow(l).OpenWindow();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
							"Le système de peut récuperer les professeurs : "+e.getMessage(),
							"Erreur",JOptionPane.ERROR_MESSAGE);
			}			
			frame.dispose();
			
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
/**
 * Test the class
 * @param args
 * @throws InvalidStateException
 */
public static void main (String[] args) throws InvalidStateException{
	/*Teacher t=new Teacher("Gonord", null, null,null,null);
	Collection l=null;

	try {
		l= (Collection) RessourceManagerImpl.getInstance().getRessources(t);
	} catch (RemoteException e) {
		System.out.println("Ne peut recup le professeur");
	}

	System.out.println(l);
	for (Iterator i=l.iterator();i.hasNext();){
		new TeacherWindow(InformationWindow.MODIFY,((Teacher)i.next())).OpenWindow();
	}*/
	TeacherInterface t=null;
	new TeacherWindow(InformationWindow.SEARCH,t).OpenWindow();
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


}
