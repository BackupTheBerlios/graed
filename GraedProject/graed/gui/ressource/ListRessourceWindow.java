/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.ressource;

import java.util.Collection;
import java.util.Iterator;

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
}
