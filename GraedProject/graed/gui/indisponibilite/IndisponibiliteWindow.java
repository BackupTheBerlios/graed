/*
 * Created on 23 févr. 2005
 */
package graed.gui.indisponibilite;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.Indisponibilite;

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
 * @author Helder DE SOUSA
 */
public class IndisponibiliteWindow extends InformationWindow{
    /**
     * Window
     */
    private JFrame frame=new JFrame();
    private static int with=200;
    private static int height=200;
    /**
     * TextField
     */
    private JFormattedTextField libelle;

    /**
     * Constructor which open the teacher window
     * @param state the state of the window
     * @param t the teacher
     * @throws InvalidStateException
     */
    public IndisponibiliteWindow(int state, Indisponibilite i) throws InvalidStateException{
    	super(state,i);
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
    	addLine(p,c,mask,libelle, "Libelle : ");
    	c.gridy = 1;
    	//addLine(p,c,mask,firstName,"Prénom : ");
    	
    	mask="*****";
    	c.gridy = 2;		
    	//addLine(p,c,mask,office,"Bureau : ");
    	
    	/* Phone */
    	mask="##.##.##.##.##";
    	c.gridy = 3;
    	//addLine(p,c,mask,phone,"Téléphone : ");
    	
    	mask="************************";
    	c.gridy = 4;
    	//addLine(p,c,mask,email,"Courriel : ");
    	
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
    	/*libelle.setText(((Indisponibilite) getInformation()).getName());
    	firstName.setText(((Indisponibilite) getInformation()).getFirstName());
    	office.setText(((Indisponibilite) getInformation()).getOffice());
    	phone.setText(((Indisponibilite) getInformation()).getPhone());
    	email.setText(((Indisponibilite) getInformation()).getEmail());*/
    	
    }
    /**
     * Open and fill the window
     */
    protected void OpenWindow(){
    	frame=new JFrame();
    	Class clazz=TeacherWindow.class;
    	ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
    	frame.setIconImage(i.getImage());
    	frame.setTitle("Indisponibilite");
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
    			/*((Indisponiblite) getInformation()).setName(name.getText());
    			((Indisponiblite) getInformation()).setFirstName(firstName.getText());
    			((Indisponiblite) getInformation()).setOffice(office.getText());
    			((Indisponiblite) getInformation()).setPhone(phone.getText());
    			((Indisponiblite) getInformation()).setEmail(email.getText());
    			System.out.println(((Indisponiblite) getInformation()));*/
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
    			setInformation(new Indisponibilite());
    			System.out.println(((Indisponibilite) getInformation()));
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
    			setInformation(new Indisponibilite());
    			System.out.println(((Indisponibilite) getInformation()));
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
    	Indisponibilite i=new Indisponibilite();
    	new IndisponibiliteWindow(InformationWindow.SEARCH,i);
    }
}
