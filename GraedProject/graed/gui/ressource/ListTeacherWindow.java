/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListTeacherWindow extends ListRessourceWindow {

	/**
	 * Window
	 */
	private JFrame frame;
	private static int with=300;
	private static int height=00;
	/**
	 * Constructor of the window
	 * @param c
	 */
	public ListTeacherWindow(Collection c) {
		super(c);
		frame=new JFrame();
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#OpenWindow()
	 */
	public void OpenWindow() {
		Class clazz=TeacherWindow.class;
		ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
		frame.setIconImage(i.getImage());

	}

}
