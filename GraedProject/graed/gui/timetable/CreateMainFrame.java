package graed.gui.timetable;



import graed.callback.CallbackRunnable;
import graed.callback.CallbackThread;
import graed.client.Client;
import graed.conf.Configuration;
import graed.exception.ExportException;
import graed.export.Exporter;
import graed.gui.renderer.NotificationRenderer;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.ressource.RessourceInterface;
import graed.auth.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.hokage.swing.JBackgroundPanel;
import com.hokage.swing.JCloseableTabbedPane;
import com.hokage.swing.JSplashScreen;


/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateMainFrame {
	private JFrame frame;
	private JCloseableTabbedPane tp;
	private Hashtable icons;
	private final JList notif;
	private ConcurrentHashMap timetable_list;
	private Hashtable buttons;
	Date debut;
	Date fin;
	private JLabel date_lib;
	private int start=8;
	private int stop=15;
	/**
	 * Constructeur
	 * @return
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 *
	 */
	public CreateMainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	    JSplashScreen splash = new JSplashScreen( "graed/gui/timetable/icons/splash.png", 100000 );
	    date_lib=new JLabel();	
	    debut=null;
		fin=null;
	    
		Subject subj = null;
		JFrame mainFrame = new JFrame();
		
		try{
			GraedGraphicCallbackHandler cbh = new GraedGraphicCallbackHandler(frame);
			
			LoginContext lc = new LoginContext("GraedAuth",cbh);
			lc.login();
			
			subj = lc.getSubject();
			
		}catch(LoginException e){
			e.printStackTrace();
			System.exit(0);
		}
		
	    date_lib=new JLabel();
	    date_lib.setHorizontalAlignment(JLabel.CENTER);
	    date_lib.setVerticalAlignment(JLabel.CENTER);
	    buttons = new Hashtable();
	    notif = new JList();
	    timetable_list=new ConcurrentHashMap();
	    notif.setModel(new DefaultListModel());
	    notif.setCellRenderer(new NotificationRenderer());
	    frame=new JFrame();
		tp=new JCloseableTabbedPane("graed/gui/timetable/icons/fond.png");
		tp.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				CreateColTimetable tmp=(CreateColTimetable) tp.getSelectedComponent();
				if (tmp!=null && !timetable_list.isEmpty()){
					Timetable t=(Timetable) timetable_list.get(tmp);
					if(t!=null)
					date_lib.setText("du " +t.getDateDebut()+" au "+t.getDateFin());
				}
				
			}});
		tp.addContainerListener(new ContainerListener(){
			public void componentAdded(ContainerEvent e) {				
			}

			public void componentRemoved(ContainerEvent e) {
				timetable_list.remove(e.getComponent());
				System.out.println("Component remove");
			}
			
		});
		tp.setOpaque(false);
		frame.setTitle("Graed project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(fillFrame());
		icons = new Hashtable();
		icons.put( "Salle", new ImageIcon(RoomWindow.class.getResource("classe16.gif")) );
		icons.put( "Professeur", new ImageIcon(TeacherWindow.class.getResource("professeur16.jpg")) );
		//icons.put( "Materiel", new ImageIcon(TeacherWindow.class.getResource("classe.gif")) );
		
        //UIManager.setLookAndFeel("com.hokage.swing.plaf.HokageLookAndFeel");
        //SwingUtilities.updateComponentTreeUI(frame);
		
        splash.close();
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Rempli la fenêtre principale
	 * @return panel contenant les elements de la fenêtre principale
	 */
	private JPanel fillFrame(){
		JBackgroundPanel p = new JBackgroundPanel(new BorderLayout());
		JBackgroundPanel bars = new JBackgroundPanel(new BorderLayout());
		p.setBackgroundImage("graed/gui/timetable/fond.png");
		bars.setBackgroundImage("graed/gui/timetable/fond.png");
		bars.add(new CreateMenuBar(frame,tp).getMenuBar(),BorderLayout.NORTH);
		bars.add( createToolBar(), BorderLayout.SOUTH );
				
		p.add(bars,BorderLayout.NORTH);
		p.add(timetable(),BorderLayout.CENTER);
		p.add(createNotificationZone(),BorderLayout.SOUTH);
		return p;
	}
	/**
	 * Partie emploi du temps sélection et graphique
	 * @return JSplitPane correspondant à la partie emploi du temps
	 */
	private JSplitPane timetable(){
		JSplitPane sp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sp.setOpaque(false);
		sp.setDividerSize(3);
		
		sp.setLeftComponent(new SelectTimetable(this).OpenWindow());
		
		/* Affichage graphique d'un emploi du temps */		
		/*CreateColTimetable time1=new CreateColTimetable(null,"Emploi du temps n°1",8,15);
		tp.add(time1.getTitle(),time1.getTimetable());
		CreateColTimetable time2=new CreateColTimetable(null,"Emploi du temps n°2",8,15);
		tp.add(time2.getTitle(),time2.getTimetable());*/
		tp.setPreferredSize(new Dimension((stop-start)*100+80,550));
		sp.setRightComponent(tp);
		
		return sp;
	}
	/**
	 * Création de la zone de notification des changements
	 * @return panel de zone de notification
	 */
	private JPanel createNotificationZone() {
		JPanel notification = new JPanel();	
		JScrollPane js = new JScrollPane(notif);
		js.setPreferredSize(new Dimension(1000,50));
		notification.add(js);
		
		runCallback();
		
		return notification;
	}
	
	/**
	 * Prévenir l'utilistauer des modifications
	 *
	 */
	private void runCallback() {
		CallbackRunnable add = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(false);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" ajouté(e) : "+ri.print());
					notif.validate();
					notif.repaint();
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable update = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(true);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" mis(e) à jour : "+ri.print());
					notif.validate();
					notif.repaint();
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable delete = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(true);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" supprimé(e) : "+ri.print());
					
					notif.validate();
					notif.repaint();
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable addI = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(true);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					IndisponibiliteInterface ri = (IndisponibiliteInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" ajouté(e) : "+ri.print());
					
					notif.validate();
					notif.repaint();
					//if( notif.getLastVisibleIndex()!=(dlm.getSize()-1) )
						notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable updateI = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(true);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					IndisponibiliteInterface ri = (IndisponibiliteInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" mis(e) à jour : "+ri.print());
					
					notif.validate();
					notif.repaint();
					//if( notif.getLastVisibleIndex()!=(dlm.getSize()-1) )
						notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable deleteI = new CallbackRunnable() {

			public void run() {
				try {
					enableRefresh(true);
					GregorianCalendar gc = new GregorianCalendar();
					String hour = (gc.get(Calendar.HOUR_OF_DAY)<10?"0":"")+gc.get(Calendar.HOUR_OF_DAY);
					String minute = (gc.get(Calendar.MINUTE)<10?"0":"")+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					IndisponibiliteInterface ri = (IndisponibiliteInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" supprimé(e) : "+ri.print());
					
					notif.validate();
					notif.repaint();
					//if( notif.getLastVisibleIndex()!=(dlm.getSize()-1) )
						notif.ensureIndexIsVisible(dlm.getSize()-1);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		
		CallbackThread t = new CallbackThread( add,delete,update, Client.getRessourceManager());
		t.start();
		CallbackThread tt = new CallbackThread( addI,deleteI,updateI, Client.getIndisponibiliteManager());
		tt.start();
	}
	
	/**
	 * Ajouter l'emploi du temps d'une ressource
	 * @param r
	 * @param dateDebut
	 * @param dateFin
	 */
	public void addTimetable(RessourceInterface r,java.sql.Date dateDebut,java.sql.Date dateFin){
		Collection c=null;
		
		debut=dateDebut;
		fin=dateFin;
		date_lib.setText("du " +dateDebut+" au "+dateFin);
		
		try {
			c=Client.getIndisponibiliteManager().getIndisponibilites(
					r,dateDebut,dateFin);
			TreeSet trie=new TreeSet(new Comparator(){
				public int compare(Object o1, Object o2){
					if (o1 instanceof IndisponibiliteInterface && o2 instanceof IndisponibiliteInterface)
						try {
							return ((IndisponibiliteInterface)o1).getHdebut().compareTo(((IndisponibiliteInterface)o2).getHdebut());
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return 0;
				}
				
			});
			trie.addAll(c);
			String title="<html>"+r.getType()+": "+r.print()+"<br>du "+dateDebut+" au "+dateFin+"</html>";
			CreateColTimetable time2=new CreateColTimetable(null,title,start,stop);
			tp.addTab(time2.getTitle(),time2, (Icon)icons.get(r.getType()));
			tp.setSelectedIndex(tp.getTabCount()-1);
			if(c!=null){
				for(Iterator i=trie.iterator();i.hasNext();)
					time2.addIndispo((IndisponibiliteInterface)i.next());
			}
			timetable_list.put(time2,new Timetable(r,dateDebut,dateFin));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Mise à jour de tous les emploi du temps
	 *
	 */
	public void refresh(){
		for (Enumeration en=timetable_list.keys();en.hasMoreElements();)
			refresh((CreateColTimetable) en.nextElement());
		enableRefresh(false);
	}
	/**
	 * Mise à jour de l'emploi du temps
	 * @param t Données concernant l'emploi du temps
	 */
	public void refresh(CreateColTimetable t){
		Timetable ti=(Timetable) timetable_list.get(t);
		modify(t,ti.getDateDebut(),ti.getDateFin());
	}
	/**
	 * Mise à jour de l'emploi du temps
	 * @param t Données concernant l'emploi du temps
	 */
	public void modify(CreateColTimetable time2,java.sql.Date dateDebut,java.sql.Date dateFin){
		Timetable t=(Timetable) timetable_list.get(time2);
		t.setDateDebut(dateDebut);
		t.setDateFin(dateFin);
		time2.clear();
		Collection c=null;
		try {
			c=Client.getIndisponibiliteManager().getIndisponibilites(
					t.getR(),dateDebut,dateFin);
			TreeSet trie=new TreeSet(new Comparator(){
				public int compare(Object o1, Object o2){
					if (o1 instanceof IndisponibiliteInterface && o2 instanceof IndisponibiliteInterface)
						try {
							return ((IndisponibiliteInterface)o1).getHdebut().compareTo(((IndisponibiliteInterface)o2).getHdebut());
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return 0;
				}
				
			});
			trie.addAll(c);
			String title="<html>"+t.getR().getType()+": "+t.getR().print()+"<br>du "+dateDebut+" au "+dateFin+"</html>";
			time2.setTitle(title);
			if(c!=null){
				for(Iterator i=trie.iterator();i.hasNext();)
					time2.addIndispo((IndisponibiliteInterface)i.next());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tp.getSelectedIndex()>=0)tp.setTitleAt(tp.getSelectedIndex(),time2.getTitle());
		time2.validate();
		time2.repaint();
	}
	/**
	 * Création de la barre de menu de la fenêtre principale
	 * @return la barre de menu
	 */
	protected JToolBar createToolBar() {
	    JToolBar tb = new JToolBar();
	    JButton exp = createButton("Exporter",
	            					"Exporter au format JPEG", 
	    							"icons/general/Export24.gif",
	    							new ActionListener() {
	    							public void actionPerformed( ActionEvent ae ) {
	    								if(tp.getSelectedComponent()!=null){
	    									javax.swing.JFileChooser jfc = new javax.swing.JFileChooser();
	    									int state = jfc.showSaveDialog(null);
	    									if(state==javax.swing.JFileChooser.APPROVE_OPTION){
	    										try {
	    											Exporter.exportToJpeg( tp.getSelectedComponent(), jfc.getSelectedFile().getCanonicalPath() );
	    										} catch (ExportException e1) {
	    											JOptionPane.showMessageDialog(frame,
	    												"Le système de peut exporter l'emploi du temps",
	    												"Erreur",JOptionPane.ERROR_MESSAGE);
	    										} catch (IOException e1) {
	    											JOptionPane.showMessageDialog(frame,
	    												"Le système de peut exporter l'emploi du temps",
	    												"Erreur",JOptionPane.ERROR_MESSAGE);
	    										}
	    									}			
	    								}
	    							}
	    });
	    JButton imp = createButton("Imprimer",
				"Imprimer l'emploi du temps sélectionné", 
				"icons/general/Print24.gif",
				new ActionListener() {
	    		public void actionPerformed( ActionEvent ae ) {
	    			Exporter.exportToPrinter(frame,tp.getSelectedComponent());
	    		}
	    });
	    
	    JButton ref = createButton("Rafraîchir",
				"Met à jour les emplois du temps", 
				"icons/general/Refresh24.gif",
				new ActionListener() {
	    		public void actionPerformed( ActionEvent ae ) {
	    			refresh();
	    		}
	    });
	    
	    JButton logout = createButton("Deconnexion",
				"Fermer la session utilisateur", 
				"icons/quit24.png",
				new ActionListener() {
	    		public void actionPerformed( ActionEvent ae ) {}
	    });

	    
	    buttons.put("export",exp);
	    buttons.put("print",imp);
	    buttons.put("refresh",ref);
	    buttons.put("logout",logout);
	    
	    
	    /** Choix de la période de l'emploi du temps **/   	   
	   
	    JBackgroundPanel p = new JBackgroundPanel();
	    p.setOpaque(false);
	    p.setBorder(BorderFactory.createTitledBorder("Navigation"));
	    Object[] o={"semaine","mois","trimestre"};
	    final JComboBox jcb=new JComboBox(o);
	    p.add(createButton("","Période précédente","icons/navigation/Back16.gif",
	    		new ActionListener() {
    		public void actionPerformed( ActionEvent ae ) {  
    			if(debut==null || fin==null)return;
    			if(jcb.getSelectedItem().equals("semaine")){ 
    				GregorianCalendar cal=new GregorianCalendar();
    				cal.setTime(debut);    				
    				if(cal.get(GregorianCalendar.DAY_OF_WEEK)>GregorianCalendar.MONDAY){
    					debut=new Date(debut.getTime()-
							(cal.get(GregorianCalendar.DAY_OF_WEEK)-GregorianCalendar.MONDAY)
							*1000*60*60*24);
    					fin=new Date(debut.getTime()+6*1000*60*60*24);    					
    				}
    				else {
    					debut=new Date(debut.getTime()-
							(cal.get(GregorianCalendar.DAY_OF_WEEK)-GregorianCalendar.MONDAY)
							*1000*60*60*24-7*1000*60*60*24);
    					fin=new Date(debut.getTime()+6*1000*60*60*24);  
    				}
    			}
    			else if(jcb.getSelectedItem().equals("mois")){ 
    				GregorianCalendar d=new GregorianCalendar();
    				d.setTime(debut);    				
    				if(d.get(GregorianCalendar.DAY_OF_MONTH)!=1){
    					d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH),1);
    				}
    				else {
    					d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH)-1,1);
    					
    				}
					debut=new Date(d.getTime().getTime());
    				d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH),31);
					fin=new Date(d.getTime().getTime());
    				if (fin.getMonth()!=debut.getMonth()){
    					fin.setTime(fin.getTime()-d.get(GregorianCalendar.DAY_OF_MONTH)*24*60*60*1000);
    				}
    			}
				else if(jcb.getSelectedItem().equals("trimestre")){    
    				Date un=new Date((new java.util.Date(Configuration.getParamValue("premier-trimestre","debut"))).getTime());					
					Date deux=new Date((new java.util.Date(Configuration.getParamValue("deuxieme-trimestre","debut"))).getTime());
					Date trois=new Date((new java.util.Date(Configuration.getParamValue("troisieme-trimestre","debut"))).getTime());
					if(debut.after(trois)){
						debut=trois;
						fin=new Date((new java.util.Date(Configuration.getParamValue("troisieme-trimestre","fin"))).getTime());   					
					}
					else if(debut.after(deux)){
						debut=deux;
						fin=new Date((new java.util.Date(Configuration.getParamValue("deuxieme-trimestre","fin"))).getTime());   					
					}
					else{
						debut=un;
						fin=new Date((new java.util.Date(Configuration.getParamValue("premier-trimestre","fin"))).getTime());   					
					}
    				   				
    			}
				date_lib.setText("du "+debut.toString()+" au "+fin.toString());
    		}
    		}));
	    
	    p.add(jcb);
	    
	    p.add(createButton("","Période suivante","icons/navigation/Forward16.gif",
	    		new ActionListener() {
    		public void actionPerformed( ActionEvent ae ) {
    			if(debut==null || fin==null)return;
    			if(jcb.getSelectedItem().equals("semaine")){    				
    				GregorianCalendar cal=new GregorianCalendar();
    				cal.setTime(debut);    				
    				if(cal.get(GregorianCalendar.DAY_OF_WEEK)>GregorianCalendar.MONDAY){
    					debut=new Date(debut.getTime()-
    							(cal.get(GregorianCalendar.DAY_OF_WEEK)-GregorianCalendar.MONDAY)
								*1000*60*60*24);
    					fin=new Date(debut.getTime()+6*1000*60*60*24);    					
    				}
    				else {
    					debut=new Date(debut.getTime()-
    							(cal.get(GregorianCalendar.DAY_OF_WEEK)-GregorianCalendar.MONDAY)
								*1000*60*60*24+7*1000*60*60*24);
    					fin=new Date(debut.getTime()+6*1000*60*60*24);  
    				}    				
    			}
    			else if(jcb.getSelectedItem().equals("mois")){ 
    				GregorianCalendar d=new GregorianCalendar();
    				d.setTime(debut);    				
    				if(d.get(GregorianCalendar.DAY_OF_MONTH)!=1){
    					d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH),1);
    				}
    				else {
    					d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH)+1,1);
    				}
					debut=new Date(d.getTime().getTime());
    				d.set(d.get(GregorianCalendar.YEAR),d.get(GregorianCalendar.MONTH),31);
    				fin=new Date(d.getTime().getTime());
    				if (fin.getMonth()!=debut.getMonth()){
    					fin.setTime(fin.getTime()-d.get(d.DAY_OF_MONTH)*24*60*60*1000);
    				}
    				
    			}
    			else if(jcb.getSelectedItem().equals("trimestre")){    
    				Date un=new Date((new java.util.Date(Configuration.getParamValue("premier-trimestre","debut"))).getTime());					
					Date deux=new Date((new java.util.Date(Configuration.getParamValue("deuxieme-trimestre","debut"))).getTime());
					Date trois=new Date((new java.util.Date(Configuration.getParamValue("troisieme-trimestre","debut"))).getTime());
					if(debut.after(trois) || debut.equals(trois) || debut.equals(deux)){
						debut=trois;
						fin=new Date((new java.util.Date(Configuration.getParamValue("troisieme-trimestre","fin"))).getTime());   					
					}
					else if(debut.after(deux) || debut.equals(un)){
						debut=deux;
						fin=new Date((new java.util.Date(Configuration.getParamValue("deuxieme-trimestre","fin"))).getTime());   					
					}
					else{
						debut=un;
						fin=new Date((new java.util.Date(Configuration.getParamValue("premier-trimestre","fin"))).getTime());   					
					}
    				 				
    			}
    			date_lib.setText("du "+debut.toString()+" au "+fin.toString());
    		}	
    		}));
	    date_lib.setPreferredSize(new Dimension(230,40));
	    p.add(date_lib);
	    p.add(createButton("","Voir","icons/general/Zoom16.gif",
	    		new ActionListener() {
    		public void actionPerformed( ActionEvent ae ) {
    			if(debut!=null && fin!=null){
    				modify((CreateColTimetable) tp.getSelectedComponent(),debut,fin);
    			}
    		}	
    		}));
	    p.setMaximumSize(new Dimension(530,75));
	    
	    tb.add(exp);
	    tb.add(imp);
	    tb.addSeparator(new Dimension(8,24));
	    tb.add(ref);
	    tb.addSeparator(new Dimension(8,24));
	    tb.add(logout);
	    tb.addSeparator(new Dimension(8,24));
	    tb.add(p);
	    	    
	    tb.setBorderPainted(true);
	    tb.setFloatable(false);
	    tb.setOpaque(true);
	    enableRefresh(false);
		    
	    return tb;
	}
	/**
	 * Creer un bouton pour la fenêtre principale
	 * @param name nom du bouton
	 * @param toolTipText infobulle
	 * @param image l'image du bouton
	 * @param a l'action associée
	 * @return le nouveau bouton créé
	 */
	protected JButton createButton( String name, String toolTipText, String image, ActionListener a ) {
	    JButton b = new JButton( name );
	    b.setHorizontalTextPosition(SwingConstants.CENTER);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setToolTipText(toolTipText);
	    b.setIcon(new ImageIcon(getClass().getResource(image)));
	    if( a!=null ) b.addActionListener(a);
	    return b;
	}
	
	/**
	 * Classe permettant de recalculer un emploi du temps
	 * @author ngonord
	 * 
	 */
	private class Timetable{
		/** Champs privés de la classe **/
		private RessourceInterface r;
		private java.sql.Date dateDebut;
		private java.sql.Date dateFin;
		
		
		/**
		 * Constructeur
		 * @param r la ressource concernée par l'emploi du temps
		 * @param dateDebut date de début de l'emploi du temps
		 * @param dateFin date de fin de l'emploi du temps
		 */
		public Timetable(RessourceInterface r, java.sql.Date dateDebut,
				java.sql.Date dateFin) {
			super();
			this.r = r;
			this.dateDebut = dateDebut;
			this.dateFin = dateFin;
		}
		/**
		 * @return Returns the dateDebut.
		 */
		public java.sql.Date getDateDebut() {
			return dateDebut;
		}
		/**
		 * @param dateDebut The dateDebut to set.
		 */
		public void setDateDebut(java.sql.Date dateDebut) {
			this.dateDebut = dateDebut;
		}
		/**
		 * @return Returns the dateFin.
		 */
		public java.sql.Date getDateFin() {
			return dateFin;
		}
		/**
		 * @param dateFin The dateFin to set.
		 */
		public void setDateFin(java.sql.Date dateFin) {
			this.dateFin = dateFin;
		}
		/**
		 * @return Returns the r.
		 */
		public RessourceInterface getR() {
			return r;
		}
		/**
		 * @param r The r to set.
		 */
		public void setR(RessourceInterface r) {
			this.r = r;
		}
	}

	protected void enableRefresh(boolean b) {
		((JButton)buttons.get("refresh")).setEnabled(b);
	}
	
	/** test ***
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.installLookAndFeel("Hokage","com.hokage.swing.plaf.HokageLookAndFeel" );
        new CreateMainFrame();
	}
}
