/*
 * Created on 30 mars 2005
 *
 */
package graed.gui.user;

import graed.client.Client;
import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.gui.ressource.ListRessourceWindow;
import graed.user.UserInterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.*;

import javax.swing.*;

/**
 * @author root
 *
 */
public class ListUserWindow extends ListRessourceWindow {

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
	public ListUserWindow(Collection c) {
		super(c);
		frame=new JFrame();		
	}

	/**
	 * Renvoi le tableau permettant le remplissage de la JTable
	 * @return le tableau permettant le remplissage de la JTable
	 */
	private Object[][] fill(){
		int j=0;
		Object[][]o= new Object[super.size()][8];
		try {					
			for (Iterator i=super.getIteractor();i.hasNext();){
				UserInterface t=(UserInterface)i.next();	
				o[j][0]=t.getName();
				o[j][1]=t.getFirstName();
				o[j][2]=t.getLogin();
				o[j][3]=t.getOffice();
				o[j][4]=t.getPhone();
				o[j][5]=t.getEmail();
				o[j][6]=t.getFonction();
				o[j][7]=t.getPassword();
				
				
				j++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListUserWindow#OpenWindow()
	 */
	public void OpenWindow() {
		Class clazz=ListUserWindow.class;
		//ImageIcon i=new ImageIcon(clazz.getResource("professeur.jpg"));
		//frame.setIconImage(i.getImage());
		frame.setSize(with,height);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel(new BorderLayout());
		frame.setTitle("Liste des utilisateurs");
		frame.setContentPane(p);
		
		Object[] o={"Nom","Pr�nom","Login","Bureau","Tel","Courriel","Fonction","Password"};
		table=new JTable(fill(),o);
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
				UserInterface t = (UserInterface) getRessource(table.getSelectedRow());	
				frame.setEnabled(false);
				try {
					new UserWindow(InformationWindow.SEE,t).OpenWindow();
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
				UserInterface t = (UserInterface) getRessource(table.getSelectedRow());
				frame.setEnabled(false);
				try {
					new UserWindow(InformationWindow.MODIFY,t).OpenWindow();
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
				UserInterface t = (UserInterface) getRessource(table.getSelectedRow());
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
				UserInterface t = (UserInterface) getRessource(table.getSelectedRow());
				table.remove(table.getSelectedRow());
				System.out.println(table);
				removeRessource(t);
				try {
					Client.getUserManager().deleteUser(t);
				} catch (RemoteException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(frame,
							"Cette ressource ne peut etre supprim�e",
							"Erreur",JOptionPane.WARNING_MESSAGE);
				}
				frame.validate();
				frame.repaint();
				
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
