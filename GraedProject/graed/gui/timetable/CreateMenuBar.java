package graed.gui.timetable;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.ressource.MaterielWindow;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.ressource.type.Group;
import graed.ressource.type.Materiel;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

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
