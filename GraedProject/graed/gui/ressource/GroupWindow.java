/*
 * Created on 4 mars 2005
 *
 */
package graed.gui.ressource;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.renderer.RessourceListRenderer;
import graed.ressource.event.RessourceEvent;
import graed.ressource.type.GroupInterface;
import graed.ressource.type.TeacherInterface;

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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Gonord Nadège
 *
 * The window for alter groups
 */
public class GroupWindow extends InformationWindow{

/**
 * Window
 */
private JFrame frame;
private static int with=400;
private static int height=200;
/**
 * TextField
 */
private JFormattedTextField name;
private JFormattedTextField description;
private JFormattedTextField email;
private JFormattedTextField option;
private JComboBox directeur;

/**
 * Constructor which open the group window
 * @param state the state of the window
 * @param t the group
 * @throws InvalidStateException
 */
public GroupWindow(int state, GroupInterface t) throws InvalidStateException{
	super(state,t);
	name = new JFormattedTextField();
	description = new JFormattedTextField();
	option = new JFormattedTextField();
	email = new JFormattedTextField();
	directeur = new JComboBox();
	directeur.setRenderer(new RessourceListRenderer());
	Collection c;//Collection
	try {
		c = Client.getRessourceManager().getRessourcesByType("Professeur");
		if(c!=null){
			for( Iterator it=c.iterator();it.hasNext();)
				directeur.addItem(it.next());		}
	}catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
 * Add the component for the frame
 * @param p panel
 * @param c constraint
 */
private void addJComponent(JPanel p,GridBagConstraints c){
	String mask="UUUUUUUUUUUUUUUUUUUUUUUU";
	c.gridy = 0;
	c.gridx = 0;
	addLine(p,c,mask,name, "Nom : ");
	c.gridy ++;
	addLine(p,c,mask,description,"Description : ");
	
	mask="*****";
	c.gridy ++;		
	addLine(p,c,mask,option,"Option(s) : ");	
	
	mask="************************";
	c.gridy ++;
	addLine(p,c,mask,email,"Courriel : ");
	
	c.gridy ++;
	addLine(p,c,mask,directeur,"Directeur formation : ");
	
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
		name.setText(((GroupInterface) getInformation()).getName());	
		description.setText(((GroupInterface) getInformation()).getDescription());
		option.setText(((GroupInterface) getInformation()).getOptions());
		email.setText(((GroupInterface) getInformation()).getMail());
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
	Class clazz=GroupWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("classe.gif"));
	frame.setIconImage(i.getImage());
	if(isSee()){
		frame.setTitle("Consulter une formation");
	}
	else if(isModify()){
		frame.setTitle("Modifier une formation");
	}
	else if(isSearch()){
		frame.setTitle("Rechercher une formation");
	}
	else if(isCreate()){
		frame.setTitle("Créer une formation");
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
	frame.pack();
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
				((GroupInterface) getInformation()).setName(name.getText());			
				((GroupInterface) getInformation()).setDescription(description.getText());
				((GroupInterface) getInformation()).setOptions(option.getText());
				((GroupInterface) getInformation()).setMail(email.getText());
				String control=((GroupInterface) getInformation()).control();
    			if(control!=null){
    				JOptionPane.showMessageDialog(frame,
							control,
							"Attention",JOptionPane.INFORMATION_MESSAGE);	
    				return;
    			}
				Client.getRessourceManager().updateRessource(((GroupInterface) getInformation()));
			} catch (RemoteException e) {
			JOptionPane.showMessageDialog(frame,
				"La formation ne peut être modifiée ",
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
					description.getText()!=null){
				try {
					GroupInterface g=(GroupInterface) Client.getRessourceManager().createRessource("Formation");
					g.setName(name.getText());
					g.setDescription(description.getText());
					g.setMail(email.getText());
					g.setOptions(option.getText());
					System.out.println(g);				
					String control=g.control();
	    			if(control!=null){
	    				JOptionPane.showMessageDialog(frame,
								control,
								"Attention",JOptionPane.INFORMATION_MESSAGE);	
	    				return;
	    			}
					Client.getRessourceManager().addRessource(g);
					} catch (RemoteException e) {	
						e.printStackTrace();
						JOptionPane.showMessageDialog(frame,
						"La formation ne peut être crée",
						"Erreur",JOptionPane.ERROR_MESSAGE);	
					}
					frame.dispose();
			}
			else{
				JOptionPane.showMessageDialog(frame,
						"Veuillez renseigner les champs nom et description",
						"Attention",JOptionPane.INFORMATION_MESSAGE);
			}
					
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
			String n = name.getText().length()==0?null:name.getText();
			String d = description.getText().length()==0?null:description.getText();
			String op = option.getText().length()==0?null:option.getText();
			String m = email.getText().length()==0?null:email.getText();
			TeacherInterface prof = (TeacherInterface) (((String)directeur.getSelectedItem().toString()).length()==0?null:directeur.getSelectedItem());
			
			GroupInterface g=(GroupInterface) Client.getRessourceManager().createRessource("Formation");
			g.setName(n);
			g.setDescription(d);
			g.setMail(m);
			g.setOptions(op);
			g.setProf_responsable(prof);
			System.out.println(g);		
			Collection l=null;			
					l= (Collection) Client.getRessourceManager().getRessources(((GroupInterface) getInformation()));
				
			
			System.out.println("List:"+l);	
			frame.setEnabled(false);
			new ListGroupWindow(l).OpenWindow();
			frame.dispose();
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(frame,
						"Le système de peut récuperer les formations",
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
	GroupInterface t=null;
	new GroupWindow(InformationWindow.SEARCH,t).OpenWindow();
}
}
