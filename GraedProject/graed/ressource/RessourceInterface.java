/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.ressource;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RessourceInterface extends Remote {
	public void setType(String type) throws RemoteException ;
	
	public String getType() throws RemoteException;
	
	/**
	 * @return Returns the id_ressource.
	 */
	public String getId_ressource() throws RemoteException ;
	/**
	 * @param id_ressource The id_ressource to set.
	 */
	public void setId_ressource(String id_ressource) throws RemoteException ;
}
