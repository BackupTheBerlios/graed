/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.ressource.type;

import graed.ressource.RessourceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface MaterielInterface extends Remote, RessourceInterface {
	/**
     * @return Returns the name.
     */
    public String getName() throws RemoteException;
    /**
     * @param name The name to set.
     */
    public void setName(String name) throws RemoteException;
    /**
     * @return Returns the type.
     */
    public String getTypeMateriel() throws RemoteException;
    /**
     * @param type The type to set.
     */
    public void setTypeMateriel(String typeMateriel) throws RemoteException;
    /**
	 * Controle les donn�es
	 */
	public String control() throws RemoteException;
}
