/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import graed.ressource.Ressource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;

/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ListRessourceWindow {
	private Collection list_ress;
	public ListRessourceWindow(Collection c){
		this.list_ress = c;
	}
	/**
	 * Renvoie un itérateur sur la collection
	 * @return un itérateur sur la collection
	 */
	public Iterator getIteractor(){
		return list_ress.iterator();
	}
	/**
	 * Renvoie la ressource à la position i
	 * @param i position de la ressource
	 * @return la ressource à la position i
	 */
	public Ressource getRessource(int i){
		if(i<0)i=0;
		return (Ressource) list_ress.toArray()[i];
	}
	/**
	 * Création du bouton annuler
	 * @return bouton
	 */
	protected JButton stop(){
		JButton b=new JButton("Annuler");
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}		
		});
		return b;
		
	}
	/**
	 * Ouvre la fenêtre contenant la liste
	 *
	 */
	public abstract void OpenWindow();
	/**
	 * Retourne la taille de la collection
	 * @return la taille de la collection
	 */
	public int size(){
		return list_ress.size();
	}
	/**
	 * Création du bouton de consultation
	 * @return Bouton de consultation
	 */
	public abstract JButton see();
	/**
	 * Création du bouton de modification
	 * @return Bouton de modification
	 */
	public abstract JButton modify();
	/**
	 * Création du bouton d'affichage de l'emploi du temps
	 * @return bouton d'affichage de l'emploi du temps
	 */
	public abstract JButton timetable();
	
}
