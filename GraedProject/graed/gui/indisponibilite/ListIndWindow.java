/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.indisponibilite;

import graed.indisponibilite.IndisponibiliteInterface;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;

/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ListIndWindow {
	private Collection list_ind;
	public ListIndWindow(Collection c){
		this.list_ind = c;
	}
	/**
	 * Renvoie un itérateur sur la collection
	 * @return un itérateur sur la collection
	 */
	public Iterator getIteractor(){
		return list_ind.iterator();
	}
	/**
	 * Renvoie l'indisponibilité à la position i
	 * @param i position de l'indisponibilité
	 * @return l'indisponibilité à la position i
	 */
	public IndisponibiliteInterface getInd(int i){
		if(i<0)i=0;
		return (IndisponibiliteInterface) list_ind.toArray()[i];
	}
	/**
	 * Renvoie l'indisponibilité à la position i
	 * @param i position de l'indisponibilité
	 * @return l'indisponibilité à la position i
	 */
	public void removeInd(IndisponibiliteInterface r){		
		list_ind.remove(r);
	}
	/**
	 * Création du bouton annuler
	 * @return bouton
	 */
	protected abstract JButton stop();
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
		return list_ind.size();
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
	 * Création du bouton de suppression
	 * @return bouton de suppression
	 */
	public abstract JButton del();
	/**
	 * Création du bouton d'affichage de l'emploi du temps
	 * @return bouton d'affichage de l'emploi du temps
	 */
	//public abstract JButton timetable();
	
	
}
