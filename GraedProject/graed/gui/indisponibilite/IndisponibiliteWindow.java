/*
 * Created on 23 févr. 2005
 */
package graed.gui.indisponibilite;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.IndWindow;
import graed.gui.factory.SpinnerFactory;
import graed.gui.model.SpinnerTimeModel;
import graed.indisponibilite.Indisponibilite;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.indisponibilite.IndisponibiliteManagerImpl;
import graed.ressource.Ressource;
import graed.ressource.RessourceManager;
import graed.ressource.event.RessourceEvent;

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
import java.util.Set;

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
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.toedter.calendar.JDateChooser;

/**
 * @author Helder DE SOUSA
 * Création de la fenêtre de recherche, création, consultation et modification
 * d'une indisponibilité
 */
public class IndisponibiliteWindow extends IndWindow{
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
	private JSpinner hdebut;
	private JSpinner duree;
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
    public IndisponibiliteWindow(int state, IndisponibiliteInterface i) throws InvalidStateException{
    	super(state,i);
    	libelle = new JFormattedTextField();
    	if (state!=IndWindow.SEARCH){
    		date_debut = new JDateChooser("d/MMMM/yyyy",false);
    		date_fin = new JDateChooser("d/MMMM/yyyy",false);
    		hdebut = SpinnerFactory.createTimeSpinner(new Date(1000*60*60*7),new Date(1000*60*60*7),new Date(1000*60*60*18));
    		duree = SpinnerFactory.createTimeSpinner(new Date(1000*60*60*-1),new Date(1000*60*60*-1),new Date(1000*60*60*18));
    	}
    	else
    	{
    		date_debut = new JDateChooser("d/MMMM/yyyy",true);
    		date_debut.setDate(new Date(0));
    		date_fin = new JDateChooser("d/MMMM/yyyy",true);
    		date_fin.setDate(new Date(0));
    		hdebut = SpinnerFactory.createTimeSpinner(new Date(1000*60*60*-1),new Date(1000*60*60*-1),new Date(1000*60*60*18));
    		duree = SpinnerFactory.createTimeSpinner(new Date(1000*60*60*-1),new Date(1000*60*60*-1),new Date(1000*60*60*18));
    	}
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
    	Object[] o = {"","ponctuel","hebdomadaire","bihebdomadaire"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fenêtre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillType(){
    	Object[] o = {"","Cours","TD","TP"};
    	return o;
    }
    /**
     * Rempli le champs periodicite de la fenêtre
     * @return tableau contenant les objets qui seront mis dans la combo
     */
    private Object[] fillTypeRess(){
    	Object[] o=null;
		try {
			RessourceManager rm = Client.getRessourceManager();;
			o = rm.getRessourcesTypes();
			for (int i=0;i<o.length;++i){
				ress_found.put(o[i],rm.getRessourcesByType((String)o[i]));//Collection
				System.out.println(rm.getRessourcesByType((String)o[i]));
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
    	if(isSee()){
    		select.setEnabled(false);
    	}
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
					if(((Ressource)o[i]).getType().equals(type_ress.getSelectedItem())){
							((DefaultListModel)list_ress.getModel()).addElement(o[i]);
					}
					frame.validate();
					frame.repaint();
				}				
			}
    		});
    	if(isSee()){
    		noselect.setEnabled(false);
    	}
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
        if(isSee()){
    		jsp2.setEnabled(false);
    	}
    	
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
    	
    	try {
			libelle.setText(((IndisponibiliteInterface) getInformation()).getLibelle());
		   	this.date_debut.setDate(((IndisponibiliteInterface) getInformation()).getDebut());
		   	this.date_fin.setDate(((IndisponibiliteInterface) getInformation()).getFin());
		   	duree.setValue(new Date(((IndisponibiliteInterface) getInformation()).getDuree()*60000-3600000));
		   	this.hdebut.setValue(((IndisponibiliteInterface) getInformation()).getHdebut());
		   	this.periodicite.setSelectedItem(((IndisponibiliteInterface) getInformation()).getPeriodicite());
		   	this.type.setSelectedItem(((IndisponibiliteInterface) getInformation()).getType());
		   	Set s=((IndisponibiliteInterface) getInformation()).getRessources();
		   	for(Iterator i=s.iterator();i.hasNext();)
		   		((DefaultListModel)this.select_ress.getModel()).addElement((Ressource)i.next());
    	} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
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
    			((Indisponibilite) getInformation()).setDebut(new Date(date_debut.getDate().getTime())); 
    			((Indisponibilite) getInformation()).setFin(new Date(date_fin.getDate().getTime()));
    			((Indisponibilite) getInformation()).setHdebut(((SpinnerTimeModel)hdebut.getModel()).getSQLTime());
    			((Indisponibilite) getInformation()).setDuree((int)(((SpinnerTimeModel)duree.getModel()).getSQLTime().getTime()/60000)+60);
    			((Indisponibilite) getInformation()).setPeriodicite((String)periodicite.getSelectedItem());
    			((Indisponibilite) getInformation()).setLibelle(libelle.getText());
    			((Indisponibilite) getInformation()).setType((String)type.getSelectedItem());
    			Set s=((Indisponibilite) getInformation()).getRessources();
    			s.clear();
    			for(int i=0;i<select_ress.getModel().getSize();++i){
    				((Indisponibilite) getInformation()).addRessource((Ressource)select_ress.getModel().getElementAt(i));
    			}
    			System.out.println(((Indisponibilite) getInformation()));
    			try {
					IndisponibiliteManagerImpl.getInstance().addIndisponibilite(((Indisponibilite) getInformation()));
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame,
							"l'indisponibilité ne peut être modifiée ",
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
    			try {
    			setInformation(new Indisponibilite(
    					new Date(date_debut.getDate().getTime()), 
    					new Date(date_fin.getDate().getTime()), 
						((SpinnerTimeModel)hdebut.getModel()).getSQLTime(),
						(int)(((SpinnerTimeModel)duree.getModel()).getSQLTime().getTime()/60000)+60,
						(String)periodicite.getSelectedItem(),
    					libelle.getText(),
						(String)type.getSelectedItem()));
    			System.out.println("calcul durée:"+((SpinnerTimeModel)duree.getModel()).getSQLTime()+ " "+(int)((SpinnerTimeModel)duree.getModel()).getSQLTime().getTime());
    			for(int i=0;i<select_ress.getModel().getSize();++i){
    				((Indisponibilite) getInformation()).addRessource((Ressource)select_ress.getModel().getElementAt(i));
    			}
    			System.out.println(((Indisponibilite) getInformation()));
    			
					IndisponibiliteManagerImpl.getInstance().addIndisponibilite(((Indisponibilite) getInformation()));
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame,
							"l'indisponibilité ne peut être crée ",
							"Erreur",JOptionPane.ERROR_MESSAGE);	
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
    			Date debut=date_debut.getDate().getTime()==new Date(0).getTime()?null:new Date(date_debut.getDate().getTime());
    			Date fin=date_fin.getDate().getTime()==new Date(0).getTime()?null:new Date(date_fin.getDate().getTime());
    			Time h=((SpinnerTimeModel)hdebut.getModel()).getSQLTime().getTime()<=0?null:((SpinnerTimeModel)hdebut.getModel()).getSQLTime();
    			int d=(int)(((SpinnerTimeModel)duree.getModel()).getSQLTime().getTime()/60000)+60;
    			if(d<0)d=0;
    			String lib=libelle.getText().length()==0?null:libelle.getText();
    			String p=((String)periodicite.getSelectedItem()).length()==0?null:(String)periodicite.getSelectedItem();
    			String ty=((String)type.getSelectedItem()).length()==0?null:((String)type.getSelectedItem());
    			System.out.println(debut+" "+fin+" "+h+" "+d+" "+p+" "+ty);
    			setInformation(new Indisponibilite(
    					debut, 
    					fin, 
						h,
						d,
						p,
    					lib,
						ty));    			
    			for(int i=0;i<select_ress.getModel().getSize();++i){
    				((Indisponibilite) getInformation()).addRessource((Ressource)select_ress.getModel().getElementAt(i));    				
    			}    
    			System.out.println(((Indisponibilite) getInformation()).getRessources());
    			Collection l=null;			
    			
    				l= (Collection) IndisponibiliteManagerImpl.getInstance().getIndisponibilites(((Indisponibilite) getInformation()));
				
				System.out.println("List:"+l);	
				if(l!=null && !l.isEmpty()){
					frame.setEnabled(false);
					new ListIndisponibiliteWindow(l).OpenWindow();					
				}
    			frame.dispose();
    			} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"Le système de peut récuperer les indisponibilités",
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
    	try {
    		IndisponibiliteInterface i=Client.getIndisponibiliteManager().createIndisponibilite();
    		new IndisponibiliteWindow(IndWindow.SEARCH,i).OpenWindow();
    	} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
}
