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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateMainFrame {
	private JFrame frame;
	private CloseableTabbedPane tp;
	private Hashtable icons;
	/**
	 * Constructeur
	 *
	 */
	public CreateMainFrame(){
		frame=new JFrame();
		tp=new CloseableTabbedPane();		
		frame.setTitle("Graed project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(fillFrame());
		frame.pack();
		frame.setVisible(true);
		icons = new Hashtable();
		icons.put( "Salle", new ImageIcon(RoomWindow.class.getResource("classe.gif")) );
		icons.put( "Professeur", new ImageIcon(TeacherWindow.class.getResource("professeur.gif")) );
		//icons.put( "Materiel", new ImageIcon(TeacherWindow.class.getResource("classe.gif")) );
	}
	/**
	 * Rempli la fenêtre principale
	 * @return panel contenant les elements de la fenêtre principale
	 */
	private JPanel fillFrame(){
		JPanel p = new JPanel(new BorderLayout());
		p.add(new CreateMenuBar(frame,tp).getMenuBar(),BorderLayout.NORTH);
		p.add(timetable(),BorderLayout.CENTER);
		return p;
	}
	/**
	 * Partie emploi du temps sélection et graphique
	 * @return JSplitPane correspondant à la partie emploi du temps
	 */
	private JSplitPane timetable(){
		JSplitPane sp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
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
	/** test ****/
	public static void main(String[] args) {
		new CreateMainFrame();
	}
}
