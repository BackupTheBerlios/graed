package graed.gui.timetable;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.indisponibilite.IndisponibiliteWindow;
import graed.gui.ressource.MaterielWindow;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.Indisponibilite;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Group;
import graed.ressource.type.Materiel;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;
import graed.util.ldap.ConnectLDAP;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author ngonord
 *
 * Création du menu pour l'interface générale
 */
public class CreateMenuBar {
	private JMenuBar barMenu;
	private JFrame frame;
	/**
	 * Constructeur
	 *
	 */
	public CreateMenuBar(JFrame frame){
		this.frame=frame;
		barMenu=new JMenuBar();
		barMenu.add(createMenuRessource());
		barMenu.add(createMenuImport());
		barMenu.add(createMenuInd());
	}
	/**
	 * Renvoie la barre de menu pour la fenêtre principale
	 * @return la barre de menu 
	 */
	public JMenuBar getMenuBar(){
		return barMenu;
	}
	/************************ Menu ressource ******************************/
	/**
	 * Création du menu sélectionner une ressource
	 * @return le menu sélectionner une ressource
	 */
	private JMenu selectRessource(){
		JMenu select= new JMenu("Selectioner");
		/* Items de sélection */
		JMenuItem prof=new JMenuItem("Professeur");
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Teacher t=null;
				try {					
					new TeacherWindow(InformationWindow.SEARCH,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de sélection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem salle=new JMenuItem("Salle");
		salle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Room t=null;
				try {
					new RoomWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de sélection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem materiel=new JMenuItem("Materiel");
		materiel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Materiel t=null;
				try {
					new MaterielWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de sélection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* TODO */
		JMenuItem formation=new JMenuItem("Formation");
		formation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Group t=null;
				/*try {
				 	
					new GroupWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {*/
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de sélection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				//}
				
			}
			
		});
		select.add(prof);
		select.add(salle);
		select.add(materiel);
		select.add(formation);
		return select;
	}
	/**
	 * Création du menu création d'une ressource
	 * @return le menu créer une ressource
	 */
	private JMenu createRessource(){
		JMenu create= new JMenu("Créer");
		/* Items de sélection */
		JMenuItem prof=new JMenuItem("Professeur");
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Teacher t=null;
				try {					
					new TeacherWindow(InformationWindow.CREATE,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem salle=new JMenuItem("Salle");
		salle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Room t=null;
				try {
					new RoomWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem materiel=new JMenuItem("Materiel");
		materiel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Materiel t=null;
				try {
					new MaterielWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* TODO */
		JMenuItem formation=new JMenuItem("Formation");
		formation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Group t=null;
				/*try {
				 	
					new GroupWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {*/
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				//}
				
			}
			
		});
		create.add(prof);
		create.add(salle);
		create.add(materiel);
		create.add(formation);
		return create;
	}
	/**
	 * Création du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuRessource(){
		JMenu ress=new JMenu("Ressource");
		/* Réalisation des sous menus */
		ress.add(selectRessource());
		ress.add(createRessource());
		return ress;
	}
	
	/***********************Importer***************************/
	/**
	 * Création du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuImport(){
		JMenu imp=new JMenu("Importer");
		/* Réalisation des sous menus */
		JMenuItem prof=new JMenuItem("Professeur");
		/* Importer les professeurs de l'arbre LDAP */
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ConnectLDAP ldap=new ConnectLDAP();
				List p= ldap.searchTeachers();
				System.out.println(p.size()+" teacher find:");
				try{
					for (Iterator i=p.iterator();i.hasNext();){
						Teacher t=(Teacher)i.next();
						System.out.println(t);
						RessourceManagerImpl.getInstance().addRessource(t);
					}
				} catch (RemoteException re) {				
					JOptionPane.showMessageDialog(frame,
							"le système ne peut importer les ressources",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* Importer les salles de l'arbre LDAP */
		JMenuItem salle=new JMenuItem("Salle");
		salle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ConnectLDAP ldap=new ConnectLDAP();
				List p= ldap.searchRoom();
				System.out.println(p.size()+" rooms find:");
				try{
					for (Iterator i=p.iterator();i.hasNext();){
						Room r=(Room)i.next();
						System.out.println(r);						
						RessourceManagerImpl.getInstance().addRessource(r);
					}
				} catch (RemoteException re) {
					re.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"le système ne peut importer les ressources",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
			
		});
		
		/* Importer les formations de l'arbre LDAP */
		JMenuItem formation=new JMenuItem("Formation");
		formation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ConnectLDAP ldap=new ConnectLDAP();
				List p= ldap.searchGroup();
				System.out.println(p.size()+" groups find:");
				try{
					for (Iterator i=p.iterator();i.hasNext();){
						Group g=(Group) i.next();
						System.out.println(g);					
						RessourceManagerImpl.getInstance().addRessource(g);
					}
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut importer les ressources",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		imp.add(prof);
		imp.add(salle);
		imp.add(formation);
		return imp;
	}
	
	/***********************Indisponibilite***************************/
	/**
	 * Création du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuInd(){
		JMenu ind=new JMenu("Indisponibilite");
		/* Réalisation des sous menus */
		JMenuItem select=new JMenuItem("Selectionner");
		/* Creer une indisponibilite */
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Indisponibilite t=null;
				try {					
					new IndisponibiliteWindow(InformationWindow.SEARCH,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de recherche",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* Selectionner une indisponibilite */
		JMenuItem creer=new JMenuItem("Creer");
		creer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Indisponibilite t=null;
				try {					
					new IndisponibiliteWindow(InformationWindow.CREATE,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le système ne peut afficher la fenêtre de création",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
			
		});
		
		
		ind.add(select);
		ind.add(creer);
		return ind;
	}
	
	/***************** Test **********************/
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setTitle("test menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** Affichage de la fenêtre **/
		JPanel p = new JPanel(new BorderLayout());
		frame.setContentPane(p);
		p.add(new CreateMenuBar(frame).getMenuBar(),BorderLayout.NORTH);	
		
		frame.pack();
		frame.setVisible(true);
	}
}
