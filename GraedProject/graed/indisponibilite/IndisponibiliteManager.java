
package graed.indisponibilite;

import graed.indisponibilite.event.IndisponibiliteListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


/**
 * @author Helder DE SOUSA
 */
public interface IndisponibiliteManager extends Remote{
    /**
     * Adds a resource.
     * @param r The resource to add
     * @throws RemoteException
     */
    public void addIndisponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * Deletes a Indiponibilite.
     * @param i The Indiponibilite to delete
     * @throws RemoteException
     */
    public void deleteIndisponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * Updates a resource.
     * @param i The resource to update.
     * @throws RemoteException
     */
    public void updateIndiponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * Gets resources using a resource as example.
     * @param i The resource used as an example
     * @return A collection of <code>Indiponibilite</code>
     * @throws RemoteException
     */
    public Collection getIndiponibilites( Indisponibilite i ) throws RemoteException;
    /**
     * Objects can register on an implementation of the Indiponibilite manager to be notified of changes.
     * @param il The object to register.
     * @throws RemoteException
     */
    public void registerForNotification( IndisponibiliteListener il ) throws RemoteException;
}
