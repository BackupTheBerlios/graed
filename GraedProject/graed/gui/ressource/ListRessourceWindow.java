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
	public Iterator getIteractor(){
		return list_ress.iterator();
	}
	public abstract void OpenWindow();
}
