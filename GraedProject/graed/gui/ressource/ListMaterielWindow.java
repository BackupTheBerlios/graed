/*
 * Created on 1 mars 2005
 *
 */
package graed.gui.ressource;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.ressource.type.MaterielInterface;

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
 * Classe affichant une liste de mat�riels
 */
public class ListMaterielWindow extends ListRessourceWindow {

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
	public ListMaterielWindow(Collection c) {
		super(c);
		frame=new JFrame();		
		Object[] o={"Nom","Type"};
		table=new JTable(new DefaultTableModel(fill(),o));
		
	}

	/**
	 * Renvoi le tableau permettant le remplissage de la JTable
	 * @return le tableau permettant le remplissage de la JTable
	 */
	private Object[][] fill(){
		int j=0;
		Object[][]o= new Object[super.size()][2];
		try{
			for (Iterator i=super.getIteractor();i.hasNext();){
				MaterielInterface t=(MaterielInterface)i.next();
				o[j][0]=t.getName();
				o[j][1]=t.getTypeMateriel();	
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
		Class clazz=ListMaterielWindow.class;
		ImageIcon i=new ImageIcon(clazz.getResource("classe.gif"));
		frame.setIconImage(i.getImage());
		frame.setSize(with,height);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel(new BorderLayout());
		frame.setTitle("Liste des materiels");
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
		/** Affichage de la fen�tre **/
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#see()
	 */
	public JButton see() {
		JButton b=new JButton("Consulter");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MaterielInterface t = (MaterielInterface) getRessource(table.getSelectedRow());	
				frame.setEnabled(false);
				try {
					new MaterielWindow(InformationWindow.SEE,t).OpenWindow();
					frame.setEnabled(true);
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(frame,
							"Vous ne pouvez consulter cette ressource",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
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
				MaterielInterface t = (MaterielInterface) getRessource(table.getSelectedRow());
				frame.setEnabled(false);
				try {
					new MaterielWindow(InformationWindow.MODIFY,t).OpenWindow();
					frame.setEnabled(true);
				} catch (InvalidStateException e) {
					JOptionPane.showMessageDialog(frame,
							"Vous ne pouvez consulter cette ressource",
							"Erreur",JOptionPane.ERROR_MESSAGE);
				}
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
				MaterielInterface t = (MaterielInterface) getRessource(table.getSelectedRow());
				//frame.setEnabled(false);
				JOptionPane.showMessageDialog(frame,
							"Cette option n'est pas encore disponible",
							"Erreur",JOptionPane.WARNING_MESSAGE);
				frame.dispose();
				
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
				MaterielInterface t = (MaterielInterface) getRessource(table.getSelectedRow());
				try {
					Client.getRessourceManager().deleteRessource(t);
				} catch (RemoteException e) {
					JOptionPane.showMessageDialog(frame,
							"Cette ressource ne peut etre supprim�e",
							"Erreur",JOptionPane.WARNING_MESSAGE);
				}
				frame.dispose();
				
			}		
		});
		return b;
	}
	/**
	 * Cr�ation du bouton annuler
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
