/*
 * Created on 2 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.timetable;

import graed.client.Client;
import graed.gui.ressource.RoomWindow;
import graed.gui.ressource.TeacherWindow;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.ressource.RessourceInterface;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
		
        UIManager.setLookAndFeel("com.hokage.swing.plaf.HokageLookAndFeel");
        SwingUtilities.updateComponentTreeUI(frame);
		
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
	    							"icons/general/export24.gif",
	    							null);
	    JButton imp = createButton("Imprimer",
				"Imprimer l'emploi du temps sélectionné", 
				"icons/general/print24.gif",
				null);
	    
	    tb.add(exp);
	    tb.add(imp);
	    tb.addSeparator();
	    tb.setBorderPainted(true);
	    tb.setFloatable(false);
	    tb.setOpaque(false);
	    return tb;
	}
	
	protected JButton createButton( String name, String toolTipText, String image, Action a ) {
	    JButton b = new JButton( name );
	    b.setHorizontalTextPosition(SwingConstants.CENTER);
	    b.setVerticalTextPosition(SwingConstants.BOTTOM);
	    b.setToolTipText(toolTipText);
	    b.setIcon(new ImageIcon( getClass().getResource(image)));
	    if( a!=null ) b.setAction(a);
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
