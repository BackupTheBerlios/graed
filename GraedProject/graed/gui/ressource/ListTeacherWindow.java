/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import graed.ressource.type.Teacher;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

	private Object[][] fill(){
		int j=0;
		Object[][]o= new Object[5][super.size()];
		for (Iterator i=super.getIteractor();i.hasNext();){
			Teacher t=(Teacher)i.next();
			o[0][j]=t.getName();
			o[1][j]=t.getFirstName();
			o[2][j]=t.getOffice();
			o[3][j]=t.getPhone();
			o[4][j]=t.getEmail();			
			j++;
		}
		return o;
	}
	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#OpenWindow()
	 */
	public void OpenWindow() {
		Class clazz=TeacherWindow.class;
		ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
		frame.setIconImage(i.getImage());
		frame.setSize(with,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane p=new JScrollPane();
		frame.setTitle("Liste des professeurs");
		frame.setContentPane(p);
		Object[] o={"Nom","Prénom","Bureau","Tel","Courriel"};
		JTable t=new JTable(fill(),o);
		p.add(t);
		
		/** Affichage de la fenêtre **/
		frame.setVisible(true);
	}

}
