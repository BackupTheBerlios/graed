/*
 * Created on 2 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.timetable;

import graed.indisponibilite.Indisponibilite;
import graed.indisponibilite.IndisponibiliteManagerImpl;
import graed.ressource.Ressource;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CreateMainFrame {
	private JFrame frame;
	private JTabbedPane tp;
	/**
	 * Constructeur
	 *
	 */
	public CreateMainFrame(){
		JFrame frame=new JFrame();
		tp=new JTabbedPane();
		frame.setTitle("Graed project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(fillFrame());
		frame.pack();
		frame.setVisible(true);
	}
	/**
	 * Rempli la fenêtre principale
	 * @return panel contenant les elements de la fenêtre principale
	 */
	private JPanel fillFrame(){
		JPanel p = new JPanel(new BorderLayout());
		p.add(new CreateMenuBar(frame).getMenuBar(),BorderLayout.NORTH);
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
	public void addTimetable(Ressource r,java.sql.Date dateDebut,java.sql.Date dateFin){
		Collection c=null;
		try {
			c=IndisponibiliteManagerImpl.getInstance().getIndisponibilites(
					r,dateDebut,dateFin);
			CreateColTimetable time2=new CreateColTimetable(null,r.getType()+": "+r,8,15);
			tp.add(time2.getTitle(),time2.getTimetable());
			if(c!=null){
				for(Iterator i=c.iterator();i.hasNext();)
					time2.addIndispo((Indisponibilite)i.next());
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
