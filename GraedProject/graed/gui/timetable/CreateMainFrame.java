/*
 * Created on 2 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.timetable;

import java.awt.BorderLayout;

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
		sp.setLeftComponent(new SelectTimetable().OpenWindow());
		
		/* Affichage graphique d'un emploi du temps */		
		CreateColTimetable time1=new CreateColTimetable(null,"Emploi du temps n°1",8,15);
		tp.add(time1.getTitle(),time1.getTimetable());
		CreateColTimetable time2=new CreateColTimetable(null,"Emploi du temps n°2",8,15);
		tp.add(time2.getTitle(),time2.getTimetable());
		sp.setRightComponent(tp);
		
		return sp;
	}
	/** test ****/
	public static void main(String[] args) {
		new CreateMainFrame();
	}
}
