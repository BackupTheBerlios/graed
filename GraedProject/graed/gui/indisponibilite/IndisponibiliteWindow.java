/*
 * Created on 23 févr. 2005
 */
package graed.gui.indisponibilite;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.indisponibilite.Indisponibilite;
import graed.indisponibilite.IndisponibiliteManagerImpl;
import graed.ressource.Ressource;
import graed.ressource.RessourceManagerImpl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.toedter.calendar.JDateChooser;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteWindow extends InformationWindow{
    /**
     * Window
     */
    private JFrame frame=new JFrame();
    private static int with=500;
    private static int height=500;
    /**
     * TextField
     */
    private JFormattedTextField libelle;
    private JDateChooser date_debut;
	private JDateChooser date_fin;
	private JFormattedTextField hdebut;
	private JFormattedTextField duree;
	private JComboBox periodicite;/* occ, hebdo */
	private JComboBox type;/* Cours, TD, TP */
    private JComboBox type_ress;/* getRessType */
    private JList list_ress;
    private JList select_ress;
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
        date_debut = new JDateChooser();
    	date_fin = new JDateChooser();
    	hdebut = new JFormattedTextField();
    	duree = new JFormattedTextField();
    	periodicite = new JComboBox(fillPeriodicite());/* occ, hebdo */
    	type = new JComboBox(fillType());/* Cours, TD, TP */
    	ress_found=new Hashtable();
        type_ress= new JComboBox(fillTypeRess());/* getRessType */
        type_ress.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Collection coll=(Collection) ress_found.get((String) type_ress.getSelectedItem());
				DefaultListModel dlm = (DefaultListModel)list_ress.getModel();
				dlm.clear();
				for( Iterator i=coll.iterator();i.hasNext();)
					dlm.addElement(i.next());
				frame.validate();
				frame.repaint();
			}
        	});
        ress_select=new Hashtable();      
        Collection coll=(Collection) ress_found.get((String) type_ress.getSelectedItem());
        list_ress=new JList();
        list_ress.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
        DefaultListModel dlm = new DefaultListModel();
        for( Iterator it=coll.iterator();it.hasNext();)
        	dlm.addElement(it.next());
        list_ress.setModel(dlm);
        select_ress=new JList(new DefaultListModel());
    }
    /**
     * Rempli le champs periodicite de la fenêtre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillPeriodicite(){
    	Object[] o = {"ponctuel","hebdomadaire","bihebdomadaire"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fenêtre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillType(){
    	Object[] o = {"Cours","TD","TP"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fenêtre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillTypeRess(){
    	Object[] o=null;
		try {
			RessourceManagerImpl rmi = RessourceManagerImpl.getInstance();
			o = rmi.getRessourcesTypes();
			for (int i=0;i<o.length;++i){
				ress_found.put(o[i],rmi.getRessourcesByType((String)o[i]));//Collection
				System.out.println(rmi.getRessourcesByType((String)o[i]));
			}			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(frame,
					"Accès concurrent: La fenêtre ne peut être affichée",
					"Erreur",JOptionPane.ERROR_MESSAGE);
		
		}
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
     * Selectionner une ressource pour l'indisponibilité
     * @return bouton de selection de ressource
     */
    private JButton select(){
    	JButton select=new JButton(">");
    	select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object[] o=list_ress.getSelectedValues();
				for (int i=0;i<o.length;++i){
					Collection coll=(Collection) ress_found.get((String) type_ress.getSelectedItem());
					((DefaultListModel)list_ress.getModel()).removeElement(o[i]);
					coll.remove(o[i]);
					((DefaultListModel)select_ress.getModel()).addElement(o[i]);
					frame.validate();
					frame.repaint();
				}				
			}
    		});
    	return select;
    }
    /**
     * Déselectionner une ressource pour l'indisponibilité
     * @return bouton de déselection de ressource
     */
    private JButton noselect(){
    	JButton noselect=new JButton("<");
    	noselect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Object[] o=select_ress.getSelectedValues();
				for (int i=0;i<o.length;++i){
					Collection coll=(Collection) ress_found.get(((Ressource)o[i]).getType());
					((DefaultListModel)select_ress.getModel()).removeElement(o[i]);
					coll.add(o[i]);
					((DefaultListModel)list_ress.getModel()).addElement(o[i]);
					frame.validate();
					frame.repaint();
				}				
			}
    		});
    	return noselect;
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
    	addLine(p,c,mask,date_debut,"Date de début : ");
    	c.gridy = 3;		
    	addLine(p,c,mask,date_fin,"Date de fin : ");    	
    	c.gridy = 4;	
    	addLine(p,c,mask,hdebut,"Heure de début : ");
    	c.gridy = 5;	
    	addLine(p,c,mask,duree,"Durée (min) : ");
    	
    	
        c.gridy = 6;
        addLine(p,c,mask,periodicite, "Fréquence : ");
        
        
        c.gridx = 0;
        c.gridwidth = 1;    
        c.weightx=0; 
        c.gridy = 7;     
        
        p.add(type_ress,c);
        
       
        
        c.gridx = 0;
        c.gridy = 8;   
        JScrollPane jsp=new JScrollPane(list_ress);
        jsp.setPreferredSize(new Dimension(200,75));
        p.add(jsp,c);
    	
        c.gridx=1; 
        JPanel p_button=new JPanel(new BorderLayout());
        p_button.add(select(),BorderLayout.NORTH);
        p_button.add(noselect(),BorderLayout.SOUTH);
        p.add(p_button,c);
        
        c.gridx=2;   
        JScrollPane jsp2=new JScrollPane(select_ress);
        jsp2.setPreferredSize(new Dimension(200,75));
        p.add(jsp2,c);
    	
        c.gridx = 0;
        c.gridy = 9;
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
    			setInformation(new Indisponibilite(
    					new Date(date_debut.getDate().getTime()), 
    					new Date(date_fin.getDate().getTime()), 
						Time.valueOf(hdebut.getText()),
						new Integer(duree.getText()).intValue(),
						(String)periodicite.getSelectedItem(),
    					libelle.getText(),
						(String)type.getSelectedItem()));
    			System.out.println(((Indisponibilite) getInformation()));
    			try {
					IndisponibiliteManagerImpl.getInstance().addIndisponibilite(((Indisponibilite) getInformation()));
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame,
							"l'indisponibilité ne peut être crée ",
							"Erreur",JOptionPane.ERROR_MESSAGE);	
				}
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
    			/*setInformation(new Indisponibilite());
    			System.out.println(((Indisponibilite) getInformation()));
    			*/
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
    	Indisponibilite i=new Indisponibilite();
    	new IndisponibiliteWindow(InformationWindow.CREATE,i).OpenWindow();
    }
}
