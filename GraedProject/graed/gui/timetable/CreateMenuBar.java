package graed.gui.timetable;

import graed.exception.ExportException;
import graed.exception.InvalidStateException;
import graed.export.Exporter;
import graed.gui.InformationWindow;
import graed.gui.indisponibilite.IndisponibiliteWindow;
import graed.gui.ressource.GroupWindow;
import graed.gui.ressource.MaterielWindow;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.GroupInterface;
import graed.ressource.type.MaterielInterface;
import graed.ressource.type.RoomInterface;
import graed.ressource.type.TeacherInterface;
import graed.user.UserInterface;
import graed.user.UserWindow;
import graed.util.ldap.ConnectLDAP;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.hokage.swing.plaf.HokageLookAndFeel;

/**
 * @author ngonord
 *
 * Cr�ation du menu pour l'interface g�n�rale
 */
public class CreateMenuBar {
	private JMenuBar barMenu;
	private JFrame frame;
	private JTabbedPane tp;
	
	/**
	 * Constructeur
	 *
	 */
	public CreateMenuBar(JFrame frame,JTabbedPane tp){
		this.frame=frame;
		this.tp=tp;
		barMenu=new JMenuBar();
		barMenu.setOpaque(false);
		barMenu.add(createMenuExport());
		barMenu.add(createMenuRessource());
		barMenu.add(createMenuImport());
		barMenu.add(createMenuInd());
		barMenu.add(createMenuLF());
		barMenu.add(createMenuConf());
		//barMenu.add(createMenuEDT());
		
	}
	/**
	 * Renvoie la barre de menu pour la fen�tre principale
	 * @return la barre de menu 
	 */
	public JMenuBar getMenuBar(){
		return barMenu;
	}
	/************************ Menu ressource ******************************/
	/**
	 * Cr�ation du menu s�lectionner une ressource
	 * @return le menu s�lectionner une ressource
	 */
	private JMenu selectRessource(){
		JMenu select= new JMenu("Selectioner");
		/* Items de s�lection */
		JMenuItem prof=new JMenuItem("Professeur");
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TeacherInterface t=null;
				try {					
					new TeacherWindow(InformationWindow.SEARCH,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de s�lection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem salle=new JMenuItem("Salle");
		salle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RoomInterface t=null;
				try {
					new RoomWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de s�lection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem materiel=new JMenuItem("Materiel");
		materiel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MaterielInterface t=null;
				try {
					new MaterielWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de s�lection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* TODO */
		JMenuItem formation=new JMenuItem("Formation");
		formation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GroupInterface t=null;
				try {
				 	
					new GroupWindow(InformationWindow.SEARCH,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de s�lection",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		select.add(prof);
		select.add(salle);
		select.add(materiel);
		select.add(formation);
		return select;
	}
	/**
	 * Cr�ation du menu cr�ation d'une ressource
	 * @return le menu cr�er une ressource
	 */
	private JMenu createRessource(){
		JMenu create= new JMenu("Cr�er");
		/* Items de s�lection */
		JMenuItem prof=new JMenuItem("Professeur");
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				TeacherInterface t=null;
				try {					
					new TeacherWindow(InformationWindow.CREATE,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem salle=new JMenuItem("Salle");
		salle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RoomInterface t=null;
				try {
					new RoomWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		JMenuItem materiel=new JMenuItem("Materiel");
		materiel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				MaterielInterface t=null;
				try {
					new MaterielWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* TODO */
		JMenuItem formation=new JMenuItem("Formation");
		formation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				GroupInterface t=null;
				try {
				 	
					new GroupWindow(InformationWindow.CREATE,t).OpenWindow();
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		create.add(prof);
		create.add(salle);
		create.add(materiel);
		create.add(formation);
		return create;
	}
	/**
	 * Cr�ation du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuRessource(){
		JMenu ress=new JMenu("Ressource");
		/* R�alisation des sous menus */
		ress.add(selectRessource());
		ress.add(createRessource());
		return ress;
	}
	
	/***********************Importer***************************/
	/**
	 * Cr�ation du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuImport(){
		JMenu imp=new JMenu("Importer");
		/* R�alisation des sous menus */
		JMenuItem prof=new JMenuItem("Professeur");
		/* Importer les professeurs de l'arbre LDAP */
		prof.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ConnectLDAP ldap=new ConnectLDAP();
				List p= ldap.searchTeachers();
				System.out.println(p.size()+" teacher find:");
				try{
					for (Iterator i=p.iterator();i.hasNext();){
						TeacherInterface t=(TeacherInterface)i.next();
						System.out.println(t);
						RessourceManagerImpl.getInstance().addRessource(t);
					}
				} catch (RemoteException re) {				
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut importer les ressources",
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
						RoomInterface r=(RoomInterface)i.next();
						System.out.println(r);						
						RessourceManagerImpl.getInstance().addRessource(r);
					}
				} catch (RemoteException re) {
					re.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut importer les ressources",
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
						GroupInterface g=(GroupInterface) i.next();
						System.out.println(g);					
						RessourceManagerImpl.getInstance().addRessource(g);
					}
				} catch (RemoteException re) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut importer les ressources",
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
	 * Cr�ation du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuInd(){
		JMenu ind=new JMenu("Indisponibilite");
		/* R�alisation des sous menus */
		JMenuItem select=new JMenuItem("Selectionner");
		/* Creer une indisponibilite */
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				IndisponibiliteInterface t=null;
				try {					
					new IndisponibiliteWindow(InformationWindow.SEARCH,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de recherche",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		/* Selectionner une indisponibilite */
		JMenuItem creer=new JMenuItem("Creer");
		creer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				IndisponibiliteInterface t=null;
				try {					
					new IndisponibiliteWindow(InformationWindow.CREATE,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
				
				
			}
			
		});
		
		
		ind.add(select);
		ind.add(creer);
		return ind;
	}
	/***********************Emploi du temps***************************/
	/**
	 * Cr�ation du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuEDT(){
		JMenu edt=new JMenu("Emploi du temps");
		/* R�alisation des sous menus */
		JMenuItem close=new JMenuItem("Fermer onglet");
		/* Fermer un emploi du temps */
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tp.remove(tp.getSelectedIndex());				
			}
			
		});
		/* Fermer tous les emploi du temps */
		JMenuItem closeAll=new JMenuItem("Fermer tous les onglets");
		closeAll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tp.removeAll();		
				
			}
			
		});
		
		
		edt.add(close);
		edt.add(closeAll);
		return edt;
	}
	/***********************Exporter***************************/
	/**
	 * Cr�ation du menu ressource
	 * @return menu ressource
	 */
	private JMenu createMenuExport(){
		JMenu edt=new JMenu("File");
		/* R�alisation des sous menus */
		JMenuItem exp=new JMenuItem("Exporter JPG");
		/* Exporter un emploi du temps */
		exp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(tp.getSelectedComponent()!=null){
					javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
					int state = jfc.showSaveDialog(null);
					if(state==javax.swing.JFileChooser.APPROVE_OPTION){
						try {
							Exporter.exportToJpeg( tp.getSelectedComponent(), jfc.getSelectedFile().getCanonicalPath() );
						} catch (ExportException e1) {
							JOptionPane.showMessageDialog(frame,
								"Le syst�me de peut exporter l'emploi du temps",
								"Erreur",JOptionPane.ERROR_MESSAGE);
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(frame,
								"Le syst�me de peut exporter l'emploi du temps",
								"Erreur",JOptionPane.ERROR_MESSAGE);
						}
					}			
				}
			}
			
		});
		/* Imprimer un emploi du temps */
		JMenuItem print=new JMenuItem("Imprimer");
		print.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(tp.getSelectedComponent()!=null){
					System.out.println(frame+" "+tp.getSelectedComponent());
					Exporter.exportToPrinter(frame,tp.getSelectedComponent());
				}
			}
			
		});
		
		
		edt.add(exp);
		edt.add(print);
		return edt;
	}

	/************ Apparence ************************/
	private JMenu createMenuLF() {
	    JMenu menu = new JMenu("Apparence");
		ButtonGroup bg = new ButtonGroup();
		Map map = getLookAndFeelsMap();
		for(Iterator i = map.keySet().iterator(); i.hasNext(); ){
		    Object key = i.next();
			final String classe = (String)map.get(key);
			System.out.println(key+" : "+classe);
			boolean set = classe.equals(HokageLookAndFeel.class.getName());
			JRadioButtonMenuItem item = new JRadioButtonMenuItem((String) key,set);
			item.addActionListener(new ActionListener(){ 
				public void actionPerformed(ActionEvent ae){ 
					try{ 
						UIManager.setLookAndFeel(classe); 
					}catch(Exception e){
						e.printStackTrace();	
					} 
					SwingUtilities.updateComponentTreeUI(frame); 
				} 
			}); 
			bg.add(item); 
			menu.add(item);  	
		}
		return menu;
	}
	
	/*****************Configuration **************/
	private JMenu createMenuConf(){
	    JMenu menu = new JMenu("Configuration");
	    JMenuItem newUser = new JMenuItem("Cr�er un utilisateur");
		newUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				UserInterface t=null;
				try {					
					new UserWindow(InformationWindow.CREATE,t).OpenWindow();					
				} catch (InvalidStateException e1) {
					JOptionPane.showMessageDialog(frame,
							"le syst�me ne peut afficher la fen�tre de cr�ation",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	    
	    JMenuItem modifyUser = new JMenuItem("Modifier un utilisateur");
	    
	    JMenuItem deleteUser = new JMenuItem("Supprimer un utilisateur");
	    
	    JMenuItem configSoft = new JMenuItem("Configurer l'application");
	    
	    menu.add(newUser);
	    menu.add(modifyUser);
	    menu.add(deleteUser);
	    menu.add(configSoft);
	    return menu;
	}
	/***************** Test **********************/
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		frame.setTitle("test menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/** Affichage de la fen�tre **/
		JPanel p = new JPanel(new BorderLayout());
		frame.setContentPane(p);
		//p.add(new CreateMenuBar(frame).getMenuBar(),BorderLayout.NORTH);	
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private Map getLookAndFeelsMap(){
		UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		Map map = new TreeMap();
		for(int i=0; i<info.length;i++){
			String nomLF = info[i].getName();
			String nomClasse = info[i].getClassName();
			map.put(nomLF,nomClasse); 	
		}
		return map;	
	}
	
}
