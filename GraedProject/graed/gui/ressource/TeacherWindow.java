/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.ressource;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
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
public class TeacherWindow extends InformationWindow{

/**
 * Window
 */
private JFrame frame;
private static int with=200;
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
public TeacherWindow(int state, Teacher t) throws InvalidStateException{
	super(state,t);
}
/**
 * Add a label and a texfield
 * @param p panel
 * @param c constraint
 * @param mask contrainst for the textfield
 * @param name the label
 * @param value the value of the textfield
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
	name.setText(((Teacher) getInformation()).getName());
	firstName.setText(((Teacher) getInformation()).getFirstName());
	office.setText(((Teacher) getInformation()).getOffice());
	phone.setText(((Teacher) getInformation()).getPhone());
	email.setText(((Teacher) getInformation()).getEmail());
	
}
/**
 * Open and fill the window
 */
protected void OpenWindow(){
	frame=new JFrame();
	Class clazz=TeacherWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	frame.setIconImage(i.getImage());
	frame.setTitle("Teacher");
	frame.setSize(with,height);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
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
			((Teacher) getInformation()).setName(name.getText());
			((Teacher) getInformation()).setFirstName(firstName.getText());
			((Teacher) getInformation()).setOffice(office.getText());
			((Teacher) getInformation()).setPhone(phone.getText());
			((Teacher) getInformation()).setEmail(email.getText());
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
			setInformation(new Teacher(name.getText(),firstName.getText(),
					office.getText(),phone.getText(),email.getText()));
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
			setInformation(new Teacher(name.getText(),firstName.getText(),
					office.getText(),phone.getText(),email.getText()));
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
	Teacher t=new Teacher("GONORD", "Nadege", 
			"2B117", "0164022461", "nade77@neuf.fr");
	new TeacherWindow(InformationWindow.SEARCH,t);
}
}
