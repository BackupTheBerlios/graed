/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import graed.ressource.RessourceInterface;

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
	 * Renvoie un it�rateur sur la collection
	 * @return un it�rateur sur la collection
	 */
	public Iterator getIteractor(){
		return list_ress.iterator();
	}
	/**
	 * Renvoie la ressource � la position i
	 * @param i position de la ressource
	 * @return la ressource � la position i
	 */
	public RessourceInterface getRessource(int i){
		if(i<0)i=0;
		return (RessourceInterface) list_ress.toArray()[i];
	}
	/**
	 * Renvoie la ressource � la position i
	 * @param i position de la ressource
	 * @return la ressource � la position i
	 */
	public void removeRessource(RessourceInterface r){		
		list_ress.remove(r);
	}
	/**
	 * Cr�ation du bouton annuler
	 * @return bouton
	 */
	protected abstract JButton stop();
	/**
	 * Ouvre la fen�tre contenant la liste
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
	 * Cr�ation du bouton de consultation
	 * @return Bouton de consultation
	 */
	public abstract JButton see();
	/**
	 * Cr�ation du bouton de modification
	 * @return Bouton de modification
	 */
	public abstract JButton modify();
	/**
	 * Cr�ation du bouton de suppression
	 * @return bouton de suppression
	 */
	public abstract JButton del();
	/**
	 * Cr�ation du bouton d'affichage de l'emploi du temps
	 * @return bouton d'affichage de l'emploi du temps
	 */
	//public abstract JButton timetable();
	
	
}
