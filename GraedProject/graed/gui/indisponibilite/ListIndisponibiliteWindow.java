/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.indisponibilite;

import graed.exception.InvalidStateException;
import graed.gui.InformationWindow;
import graed.indisponibilite.Indisponibilite;
import graed.indisponibilite.IndisponibiliteManagerImpl;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Teacher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * @author ngonord
 *
 * Classe affichant une liste de professeurs
 */
public class ListIndisponibiliteWindow extends ListIndWindow {

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
	public ListIndisponibiliteWindow(Collection c) {
		super(c);
		frame=new JFrame();		
	}

	/**
	 * Renvoi le tableau permettant le remplissage de la JTable
	 * @return le tableau permettant le remplissage de la JTable
	 */
	private Object[][] fill(){
		int j=0;
		Object[][]o= new Object[super.size()][7];
		for (Iterator i=super.getIteractor();i.hasNext();){
			Indisponibilite t=(Indisponibilite)i.next();
			o[j][0]=t.getLibelle();
			o[j][1]=t.getDebut();
			o[j][2]=t.getFin();
			o[j][3]=t.getHdebut();
			o[j][4]=t.getPeriodicite();			
			o[j][5]=t.getType();	
			o[j][6]=t.getRessources().toString();
			j++;
		}
		return o;
	}
	/* (non-Javadoc)
	 * @see graed.gui.ressource.ListRessourceWindow#OpenWindow()
	 */
	public void OpenWindow() {		
		frame.setSize(with,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p=new JPanel(new BorderLayout());
		frame.setTitle("Liste des indisponibilit�s");
		frame.setContentPane(p);
		
		Object[] o={"Libelle","D�but","Fin","Heure","Fr�quence","Type","Ressources"};
		table=new JTable(fill(),o);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		frame.getContentPane().add(table.getTableHeader(),BorderLayout.NORTH);
		frame.getContentPane().add(table,BorderLayout.CENTER);
		
		JPanel button=new JPanel();
		button.add(see());
		button.add(modify());
		button.add(timetable());
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
				Indisponibilite t = (Indisponibilite) getInd(table.getSelectedRow());	
				frame.setEnabled(false);
				try {
					new IndisponibiliteWindow(InformationWindow.SEE,t).OpenWindow();
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
				Indisponibilite t = (Indisponibilite) getInd(table.getSelectedRow());
				frame.setEnabled(false);
				try {
					new IndisponibiliteWindow(InformationWindow.MODIFY,t).OpenWindow();
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
				Indisponibilite t = (Indisponibilite) getInd(table.getSelectedRow());
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
				Indisponibilite t = (Indisponibilite) getInd(table.getSelectedRow());
				table.remove(table.getSelectedRow());
				System.out.println(table);
				removeInd(t);
				try {
					IndisponibiliteManagerImpl.getInstance().deleteIndisponibilite(t);
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
