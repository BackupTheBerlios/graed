
package graed.ressource;

import graed.callback.Callback;
import graed.callback.CallbackSender;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;


/**
 * @author Helder DE SOUSA
 */
public interface RessourceManager extends CallbackSender, Remote{
    /**
     * Adds a resource.
     * @param r The resource to add
     * @throws RemoteException
     */
    public void addRessource( RessourceInterface r ) throws RemoteException;
    /**
     * Deletes a ressource.
     * @param r The ressource to delete
     * @throws RemoteException
     */
    public void deleteRessource( RessourceInterface r ) throws RemoteException;
    /**
     * Updates a resource.
     * @param r The resource to update.
     * @throws RemoteException
     */
    public void updateRessource( RessourceInterface r ) throws RemoteException;
    /**
     * Gets resources using a resource as example.
     * @param r The resource used as an example
     * @return A collection of <code>Ressource</code>
     * @throws RemoteException
     */
    public Collection getRessources( RessourceInterface r ) throws RemoteException;
        
    /**
     * Returns the availables types for ressources.
     * @return An array containing all types.
     */
    public String[] getRessourcesTypes() throws RemoteException;
    /**
     * Gets all ressources corresponding to a type.
     * @param type The type of the ressources to fetch
     * @return A collection of ressources of the given type
     */
    public Collection getRessourcesByType( String type ) throws RemoteException;
    public RessourceInterface createRessource( String type ) throws RemoteException;
    public RessourceInterface createRessource( Class type ) throws RemoteException;
}
