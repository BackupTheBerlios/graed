/*
 * Created on 23 f�vr. 2005
 */
package graed.gui.indisponibilite;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.Indisponibilite;
import graed.ressource.RessourceManagerImpl;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteWindow extends InformationWindow{
    /**
     * Window
     */
    private JFrame frame=new JFrame();
    private static int with=300;
    private static int height=200;
    /**
     * TextField
     */
    private JFormattedTextField libelle;
    private JFormattedTextField date_debut;
	private JFormattedTextField date_fin;
	private JFormattedTextField duree;
	private JComboBox periodicite;/* occ, hebdo */
	private JComboBox type;/* Cours, TD, TP */
    private JComboBox type_ress;/* getRessType */
    private Hashtable ress_found;
    private Hashtable ress_select;
	

    /**
     * Constructor which open the indisponibility window
     * @param state the state of the window
     * @param i the indisponibility
     * @throws InvalidStateException
     */
    public IndisponibiliteWindow(int state, Indisponibilite i) throws InvalidStateException{
    	super(state,i);
    	libelle = new JFormattedTextField();
        date_debut = new JFormattedTextField();
    	date_fin = new JFormattedTextField();
    	duree = new JFormattedTextField();
    	periodicite = new JComboBox(fillPeriodicite());/* occ, hebdo */
    	type = new JComboBox(fillType());/* Cours, TD, TP */
    	ress_found=new Hashtable();
        type_ress= new JComboBox(fillTypeRess());/* getRessType */
        ress_select=new Hashtable();
    }
    /**
     * Rempli le champs periodicite de la fen�tre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillPeriodicite(){
    	Object[] o = {"ponctuel","hebdomadaire","bihebdomadaire"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fen�tre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillType(){
    	Object[] o = {"Cours","TD","TP"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fen�tre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillTypeRess(){
    	Object[] o={"Prof","Salle","Mat","Form"};
		/*try {
			RessourceManagerImpl rmi = RessourceManagerImpl.getInstance();
			o = rmi.getRessourcesTypes();
			for (int i=0;i<o.length;++i){
				ress_found.put(o[i],rmi.getRessourcesByType((String)o[i]));//Collection
			}
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(frame,
					"Acc�s concurrent: La fen�tre ne peut �tre affich�e",
					"Erreur",JOptionPane.ERROR_MESSAGE);
		
		}*/
		return o;
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
       
        p.add(new JLabel(name),c);
    	
        c.gridx = 1;
    	c.gridwidth = 2;
    	c.weightx=1;    	
    	   	
    	if(isSee()){
    		tf.setEnabled(false);
    	}
    	p.add(tf,c);
    }
    /**
     * Add the component for the window 
     * @param p panel
     * @param c constraint
     */
    private void addJComponent(JPanel p,GridBagConstraints c){
    	String mask="UUUUUUUUUUUUUUUUUUUUUUUU";
    	c.gridy = 0;
    	addLine(p,c,mask,libelle, "Libelle : ");
    	
    	c.gridy = 1;
        addLine(p,c,mask,type, "Type : ");
               
    	mask="##/##/####";
    	c.gridy = 2;
    	addLine(p,c,mask,date_debut,"Date de d�but : ");
    	c.gridy = 3;		
    	addLine(p,c,mask,date_fin,"Date de fin : ");
    	c.gridy = 4;		
    	addLine(p,c,mask,duree,"Dur�e (min) : ");
    	
    	
        c.gridy = 5;
        addLine(p,c,mask,periodicite, "Fr�quence : ");
        
        
        c.gridx = 0;
        c.gridwidth = 1;    
        c.weightx=0; 
        c.gridy = 6;        
        p.add(type_ress,c);
    	
    	
        c.gridy = 7;
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
    public void OpenWindow(){
    	frame=new JFrame();    	
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
     * Cr�ation du bouton modifier
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
     * Cr�ation du bouton creer
     * @return bouton
     */
    protected JButton create(){
    	JButton b=new JButton("Creer");
    	b.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent arg0) {
    			/*setInformation(new Indisponibilite(date_debut.getText(), Date fin, int duree, String periodicite,
    					String libelle, String type));
    			System.out.println(((Indisponibilite) getInformation()));
    			*/System.exit(0);
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
    			/*setInformation(new Indisponibilite());
    			System.out.println(((Indisponibilite) getInformation()));
    			*/System.exit(0);
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
    	new IndisponibiliteWindow(InformationWindow.CREATE,i).OpenWindow();
    }
}
