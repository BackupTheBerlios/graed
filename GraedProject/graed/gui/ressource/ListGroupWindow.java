/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.type.GroupInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author ngonord
 *
 * Classe affichant une liste de professeurs
 */
public class ListGroupWindow extends ListRessourceWindow {

	/**
	 * Window
	 */
	private JFrame frame;
	private JTable table;
	private static int with=300;
	private static int height=200;
	/**
	 * Constructor of the window
	 * @param c
	 */
	public ListGroupWindow(Collection c) {
		super(c);
		frame=new JFrame();	
		Object[] o={"Nom","Description","Options","Professeur Responsable","Secrétaire","Courriel"};
		table=new JTable(new DefaultTableModel(fill(),o));
		
	}

	/**
	 * Renvoi le tableau permettant le remplissage de la JTable
	 * @return le tableau permettant le remplissage de la JTable
	 */
	private Object[][] fill(){
		Object[][]o=null;
		try {
			int j=0;
			 o=new Object[super.size()][6];
			for (Iterator i=super.getIteractor();i.hasNext();){
				GroupInterface t=(GroupInterface)i.next();			
				o[j][0]=t.getName();			
				o[j][1]=t.getDescription();
				o[j][2]=t.getOptions();			
				o[j][3]=t.getProf_responsable();
				o[j][4]=t.getUser();	
				o[j][5]=t.getMail();
				j++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return o;
	}
	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#OpenWindow()
	 */
	public void OpenWindow() {
		Class clazz=ListGroupWindow.class;
		ImageIcon i=new ImageIcon(clazz.getResource("classe.gif"));
		frame.setIconImage(i.getImage());
		frame.setSize(with,height);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel(new BorderLayout());
		frame.setTitle("Liste des formations");
		frame.setContentPane(p);
		
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		frame.getContentPane().add(table.getTableHeader(),BorderLayout.NORTH);
		frame.getContentPane().add(table,BorderLayout.CENTER);
		
		JPanel button=new JPanel();
		button.add(see());
		button.add(modify());
		//button.add(timetable());
		button.add(del());
		button.add(stop());
		frame.getContentPane().add(button,BorderLayout.SOUTH);
		
		frame.pack();
		/** Affichage de la fenêtre **/
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#see()
	 */
	public JButton see() {
		JButton b=new JButton("Consulter");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GroupInterface t = (GroupInterface) getRessource(table.getSelectedRow());	
				frame.setEnabled(false);
				try {
					new GroupWindow(InformationWindow.SEE,t).OpenWindow();
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(frame,
							"Vous ne pouvez consulter cette ressource",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				frame.setEnabled(true);
			}		
		});
		return b;
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#modify()
	 */
	public JButton modify() {
		JButton b=new JButton("Modifier");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GroupInterface t = (GroupInterface) getRessource(table.getSelectedRow());
				frame.setEnabled(false);
				try {
					new GroupWindow(InformationWindow.MODIFY,t).OpenWindow();
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(frame,
							"Vous ne pouvez modifier cette ressource",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
				frame.setEnabled(true);
			}		
		});
		return b;
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#timetable()
	 */
	public JButton timetable() {
		JButton b=new JButton("Afficher EDP");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GroupInterface t = (GroupInterface) getRessource(table.getSelectedRow());
				//frame.setEnabled(false);
				JOptionPane.showMessageDialog(frame,
							"Cette option n'est pas encore disponible",
							"Erreur",JOptionPane.WARNING_MESSAGE);
				
			}		
		});
		return b;
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#del()
	 */
	public JButton del() {
		JButton b=new JButton("Supprimer");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GroupInterface t = (GroupInterface) getRessource(table.getSelectedRow());
				table.remove(table.getSelectedRow());
				System.out.println(table);
				removeRessource(t);
				try {
					Client.getRessourceManager().deleteRessource(t);
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"Cette ressource ne peut etre supprimée",
							"Erreur",JOptionPane.WARNING_MESSAGE);
				}
				frame.validate();
				frame.repaint();
				
			}		
		});
		return b;
	}
	/**
	 * Création du bouton annuler
	 * @return bouton
	 */
	protected JButton stop(){
		JButton b=new JButton("Annuler");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}		
		});
		return b;
		
	}

}
