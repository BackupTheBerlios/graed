/*
 * Created on 2 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.timetable;

import graed.callback.CallbackRunnable;
import graed.callback.CallbackThread;
import graed.client.Client;
import graed.exception.ExportException;
import graed.export.Exporter;
import graed.gui.renderer.NotificationRenderer;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.ressource.RessourceInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
	/**
	 * Constructeur
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 *
	 */
	public CreateMainFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	    JSplashScreen splash = new JSplashScreen( "graed/gui/timetable/icons/splash.png", 100000 );
	    notif = new JList();
	    notif.setModel(new DefaultListModel());
	    notif.setCellRenderer(new NotificationRenderer());
	    frame=new JFrame();
		tp=new JCloseableTabbedPane("graed/gui/timetable/icons/fond.png");
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
		CreateColTimetable time1=new CreateColTimetable(null,"Emploi du temps n°1",8,15);
		tp.add(time1.getTitle(),time1.getTimetable());
		CreateColTimetable time2=new CreateColTimetable(null,"Emploi du temps n°2",8,15);
		tp.add(time2.getTitle(),time2.getTimetable());
		sp.setRightComponent(tp);
		
		return sp;
	}
	
	private JPanel createNotificationZone() {
		JPanel notification = new JPanel();
		/*DefaultListModel dlm = new DefaultListModel();
		dlm.addElement("Zone de notification");
		notif.setModel(dlm);*/
		JScrollPane js = new JScrollPane(notif);
		js.setPreferredSize(new Dimension(1000,50));
		//notification.setPreferredSize(new Dimension(800,50));
		notification.add(js);
		
		runCallback();
		
		return notification;
	}
	
	
	private void runCallback() {
		CallbackRunnable add = new CallbackRunnable() {

			public void run() {
				try {
					GregorianCalendar gc = new GregorianCalendar();
					String hour = gc.get(Calendar.HOUR_OF_DAY)<10?"0":""+gc.get(Calendar.HOUR_OF_DAY);
					String minute = gc.get(Calendar.MINUTE)<10?"0":""+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" ajouté(e) : "+ri.print());
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					notif.validate();
					notif.repaint();
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable update = new CallbackRunnable() {

			public void run() {
				try {
					GregorianCalendar gc = new GregorianCalendar();
					String hour = gc.get(Calendar.HOUR_OF_DAY)<10?"0":""+gc.get(Calendar.HOUR_OF_DAY);
					String minute = gc.get(Calendar.MINUTE)<10?"0":""+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" mis(e) à jour : "+ri.print());
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					notif.validate();
					notif.repaint();
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		CallbackRunnable delete = new CallbackRunnable() {

			public void run() {
				try {
					GregorianCalendar gc = new GregorianCalendar();
					String hour = gc.get(Calendar.HOUR_OF_DAY)<10?"0":""+gc.get(Calendar.HOUR_OF_DAY);
					String minute = gc.get(Calendar.MINUTE)<10?"0":""+gc.get(Calendar.MINUTE);
					String time = hour+":"+minute;
					DefaultListModel dlm = (DefaultListModel)notif.getModel();
					RessourceInterface ri = (RessourceInterface)getSource();
					dlm.addElement(time+"    "+ri.getType()+" supprimé(e) : "+ri.print());
					notif.ensureIndexIsVisible(dlm.getSize()-1);
					notif.validate();
					notif.repaint();
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			
		};
		
		CallbackThread t = new CallbackThread( add,delete,update, Client.getRessourceManager());
		t.start();

	}
	
	/**
	 * Ajouter l'emploi du temps d'une ressource
	 * @param r
	 * @param dateDebut
	 * @param dateFin
	 */
	public void addTimetable(RessourceInterface r,java.sql.Date dateDebut,java.sql.Date dateFin){
		Collection c=null;
		try {
			c=Client.getIndisponibiliteManager().getIndisponibilites(
					r,dateDebut,dateFin);
			CreateColTimetable time2=new CreateColTimetable(null,r.getType()+": "+r.print(),8,15);
			tp.addTab(time2.getTitle(),time2.getTimetable(), (Icon)icons.get(r.getType()));
			if(c!=null){
				for(Iterator i=c.iterator();i.hasNext();)
					time2.addIndispo((IndisponibiliteInterface)i.next());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(c);
	}
	
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
	    
	    tb.add(exp);
	    tb.add(imp);
	    tb.addSeparator();
	    tb.setBorderPainted(true);
	    tb.setFloatable(false);
	    tb.setOpaque(false);
	    return tb;
	}
	
	protected JButton createButton( String name, String toolTipText, String image, ActionListener a ) {
	    JButton b = new JButton( name );
	    b.setHorizontalTextPosition(SwingConstants.CENTER);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setToolTipText(toolTipText);
	    b.setIcon(new ImageIcon(getClass().getResource(image)));
	    if( a!=null ) b.addActionListener(a);
	    return b;
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
