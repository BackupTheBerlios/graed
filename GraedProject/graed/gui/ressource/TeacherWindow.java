/*
 * Created on 21 févr. 2005
 *
 */
package graed.gui.ressource;

import graed.exception.InvalidStateException;
import graed.ressource.type.Teacher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

/**
 * @author Gonord Nadège
 *
 * The window for alter teachers
 */
public class TeacherWindow {

/**
 * The different state to open the window
 */
private static int CREATE=1;
private static int MODIFY=2;
private static int SEE=3;
private static int SEARCH=4;
/**
 * The current state
 */
private int state;
private Teacher t;

/**
 * Window
 */

private int with=200;
private int height=200;
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
	if((state!=CREATE && state!=MODIFY
			&& state!=SEE && state!=SEARCH)
			|| (t==null && 
			state!=CREATE && state!=SEARCH))
		throw new InvalidStateException();
	this.state=state;
	this.t=t;
	OpenWindow();
}
/**
 * Add a label and a texfield
 * @param p panel
 * @param c constraint
 * @param mask contrainst for the textfield
 * @param name the label
 * @param value the value of the textfield
 */
private void addLine(JPanel p,GridBagConstraints c,String mask,JFormattedTextField tf,String name, String value){
	c.gridx = 0;
    c.gridwidth = 1;    
    c.weightx=0; 
   
    p.add(new JLabel(name,SwingConstants.RIGHT),c);
	
    c.gridx = 1;
	c.gridwidth = 2;
	c.weightx=1;
	
	
	if(mask!=null){
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
	else{
		tf=new JFormattedTextField();
		System.out.println("mask null");
	}
	
	if((state==SEE || state==MODIFY)&& value!=null)
		tf.setText(value);	
	p.add(tf,c);
}
/**
 * Add the component for the window create and search
 * @param p panel
 * @param c constraint
 */
private void CreateOrSearch(JPanel p,GridBagConstraints c){
	String mask="UUUUUUUUUUUUUUUUUUUUUUUU";
	c.gridy = 0;
	addLine(p,c,mask,name, "Nom : ", null);
	c.gridy = 1;
	addLine(p,c,mask,firstName,"Prénom : ", null);
	
	mask="*****";
	c.gridy = 2;		
	addLine(p,c,mask,office,"Bureau : ", null);
	
	/* Phone */
	mask="##.##.##.##.##";
	c.gridy = 3;
	addLine(p,c,mask,phone,"Téléphone : ", null);
	
	mask="************************";
	c.gridy = 4;
	addLine(p,c,mask,email,"Courriel : ", null);
	
	c.gridy = 5;
	c.gridx = 0;
	
	if(state==CREATE){
		p.add(new JButton("Créer"),c);
	}
	else{
		p.add(new JButton("Chercher"),c);
	}
}
/**
 * Add the component for the window see and modify
 * @param p panel
 * @param c constraint
 */
private void SeeOrModify(JPanel p,GridBagConstraints c){
	String mask="UUUUUUUUUUUUUUUUUUUUUUUU";
	c.gridy = 0;
	addLine(p,c,mask,name, "Nom : ", t.getName());
	c.gridy = 1;
	addLine(p,c,mask,firstName,"Prénom : ", t.getFirstName());
	
	mask="*****";
	c.gridy = 2;		
	addLine(p,c,mask,office,"Bureau : ", t.getOffice());
	
	/* Phone */
	mask="##.##.##.##.##";
	c.gridy = 3;
	addLine(p,c,mask,phone,"Téléphone : ", t.getPhone());
	
	mask="************************";
	c.gridy = 4;
	addLine(p,c,mask,email,"Courriel : ", t.getEmail());
	
	c.gridy = 5;
	c.gridx = 0;
	
	if(state==MODIFY){
		p.add(modify(),c);
	}	
}
/**
 * Open and fill the window
 */
private void OpenWindow(){
	JFrame frame=new JFrame();
	Class clazz=TeacherWindow.class;
	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
	frame.setIconImage(i.getImage());
	frame.setTitle("Teacher");
	frame.setSize(with,height);
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
	if(state==CREATE || state==SEARCH){
		CreateOrSearch(p,c);
	}
	else{
		SeeOrModify(p,c);
	}
	
	c.gridx = 2;
	p.add(new JButton("Annuler"),c);	
	
	frame.setContentPane(p);	
	frame.setVisible(true);
	
}

private JButton modify(){
	JButton b=new JButton("Modifier");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
							
				System.out.println(name.getText());
				t.setName(name.getText());
			t.setFirstName(firstName.getText());
			t.setOffice(office.getText());
			t.setPhone(phone.getText());
			t.setEmail(email.getText());
			System.out.println(t);
			
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
	new TeacherWindow(TeacherWindow.MODIFY,t);
	System.out.println(t);
}
}
