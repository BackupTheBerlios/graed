/*
 * Created on 30 mars 2005
 *
 */

/**
 * @author tom
 */
package graed.gui.user;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.event.RessourceEvent;
import graed.user.User;
import graed.user.UserInterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class UserWindow extends InformationWindow implements Serializable{

/**
 * Window
 */
private JFrame frame;
private static int with=300;
private static int height=250;
/**
 * TextField
 */
private JFormattedTextField name;
private JFormattedTextField firstName;
private JFormattedTextField login;
private JFormattedTextField office;
private JFormattedTextField phone; 
private JFormattedTextField email;
private JComboBox fonction;
private JPasswordField passwd1;
private JPasswordField passwd2;

/**
 * Constructor which open the teacher window
 * @param state the state of the window
 * @param t the teacher
 * @throws InvalidStateException
 */
public UserWindow(int state, UserInterface t) throws InvalidStateException{
	super(state,t);
	name = new JFormattedTextField();
	firstName = new JFormattedTextField();
	login = new JFormattedTextField();
	office = new JFormattedTextField();
	phone = new JFormattedTextField(); 
	email = new JFormattedTextField();
	String[] fonct={"","administrateur","secrétaire","professeur","service audiovisuel","etudiant"};
	fonction = new JComboBox(fonct);
	passwd1 = new JPasswordField();
	passwd2 = new JPasswordField();
	if(state==InformationWindow.MODIFY || state==InformationWindow.SEE){
		/*try {
			Client.getRessourceManager().registerForNotification(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}*/
	}
}
/**
 * Add a label and a texfield
 * @param p panel
 * @param c constraint
 * @param mask contrainst for the textfield
 * @param name the label
 */
private void addLine(JPanel p,GridBagConstraints c,String mask,JComponent tf,String name){
	c.gridx = 0;
    c.gridwidth = 1;    
    c.weightx=0; 
   
    p.add(new JLabel(name,SwingConstants.RIGHT),c);
	
    c.gridx = 1;
	c.gridwidth = 2;
	c.weightx=1;

	
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
	c.gridy++;
	addLine(p,c,mask,firstName,"Prénom : ");
	c.gridy ++;
	addLine(p,c,mask,login,"Login : ");
	
	mask="*****";
	c.gridy ++;		
	addLine(p,c,mask,office,"Bureau : ");
	
	/* Phone */
	mask="##.##.##.##.##";
	c.gridy ++;
	addLine(p,c,mask,phone,"Téléphone : ");
	
	mask="************************";
	c.gridy ++;
	addLine(p,c,mask,email,"Courriel : ");

	c.gridy ++;
	addLine(p,c,mask,fonction,"Rôle :");

	if(isCreate() || isModify()){
	c.gridy ++;
	addLine(p,c,mask,passwd1,"Mot de passe :");
	
	c.gridy ++;
	addLine(p,c,mask,passwd2,"Retaper le mdp :");
	}
	c.gridy ++;	
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
		name.setText(((UserInterface) getInformation()).getName());	
		firstName.setText(((UserInterface) getInformation()).getFirstName());
		login.setText(((UserInterface) getInformation()).getLogin());
		office.setText(((UserInterface) getInformation()).getOffice());
		phone.setText(((UserInterface) getInformation()).getPhone());
		email.setText(((UserInterface) getInformation()).getEmail());
		fonction.setSelectedItem(((UserInterface) getInformation()).getFonction());
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
	Class clazz=UserWindow.class;
	//ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	//frame.setIconImage(i.getImage());
	if(isSee()){
		frame.setTitle("Consulter un utilisateur");
	}
	else if(isModify()){
		frame.setTitle("Modifier un utilisateur");
	}
	else if(isSearch()){
		frame.setTitle("Rechercher un utilisateur");
	}
	else if(isCreate()){
		frame.setTitle("Créer un utilisateur");
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
				((UserInterface) getInformation()).setName(name.getText());			
				((UserInterface) getInformation()).setFirstName(firstName.getText());
				((UserInterface) getInformation()).setLogin(login.getText());
				((UserInterface) getInformation()).setOffice(office.getText());
				((UserInterface) getInformation()).setPhone(phone.getText());
				((UserInterface) getInformation()).setEmail(email.getText());
				((UserInterface) getInformation()).setFonction((String) fonction.getSelectedItem());
				if(new String(passwd1.getPassword()).equals(new String(passwd2.getPassword()))){
					try{
					    String hpass = User.encodePassword(new String(passwd1.getPassword()));
						((UserInterface) getInformation()).setPassword(hpass);
						System.out.println(hpass);
					}catch(NoSuchAlgorithmException e){
						e.printStackTrace();
					}
				}
				else{
				    
				    System.out.println("les mdp ne correstpondent pas");
				}
				String control=((UserInterface) getInformation()).control();
    			if(control!=null){
    				JOptionPane.showMessageDialog(frame,
							control,
							"Attention",JOptionPane.INFORMATION_MESSAGE);	
    				return;
    			}
    			Client.getUserManager().updateUser(((UserInterface) getInformation()));
    			
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
				"L'utilisateur ne peut être modifiée ",
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
		public void actionPerformed(ActionEvent arg0){
			if(name.getText()!=null &&
					firstName.getText()!=null && email.getText()!=null){
					try {
						UserInterface ti=(UserInterface) Client.getUserManager().createUser();
						setInformation(ti);		
						((UserInterface) getInformation()).setName(name.getText());			
						((UserInterface) getInformation()).setFirstName(firstName.getText());
						((UserInterface) getInformation()).setLogin(login.getText());
						((UserInterface) getInformation()).setOffice(office.getText());
						((UserInterface) getInformation()).setPhone(phone.getText());
						((UserInterface) getInformation()).setEmail(email.getText());
						((UserInterface) getInformation()).setFonction((String) fonction.getSelectedItem());
						if(new String(passwd1.getPassword()).equals(new String(passwd2.getPassword()))){
							try{
							    String hpass = User.encodePassword(new String(passwd1.getPassword()));
								((UserInterface) getInformation()).setPassword(hpass);
								System.out.println(hpass);
							}catch(NoSuchAlgorithmException e){
								e.printStackTrace();
							}
						}
						else{
						    
						    System.out.println("les mdp ne correstpondent pas");
						}
						String control=((UserInterface) getInformation()).control();
		    			if(control!=null){
		    				JOptionPane.showMessageDialog(frame,
									control,
									"Attention",JOptionPane.INFORMATION_MESSAGE);	
		    				return;
		    			}
		    			Client.getUserManager().addUser(((UserInterface) getInformation()));
					} catch (RemoteException e) {						
						JOptionPane.showMessageDialog(frame,
						"L'utilisateur ne peut être crée",
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
				
				UserInterface ti=(UserInterface) Client.getUserManager().createUser();
				//setInformation(ti);		
				ti.setName(name_prof);			
				ti.setFirstName(firstName_prof);
				ti.setOffice(office_prof);
				ti.setPhone(phone_prof);
				ti.setEmail(email_prof);
				System.out.println("--------->"+ti.print());	
				
				Collection l=null;			
				l= (Collection) Client.getUserManager().getUsers(ti);
				System.out.println("List:"+l);	
				frame.setEnabled(false);
				new ListUserWindow(l).OpenWindow();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
							"Le système ne peut récuperer les utilisateurs : "+e.getMessage(),
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
