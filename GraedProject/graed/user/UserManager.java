/*
 * Created on 22 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.user;

import graed.ressource.RessourceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UserManager extends Remote{
	/**
     * Adds an user.
     * @param u The user to add
     * @throws RemoteException
     */
    public void addUser( UserInterface u ) throws RemoteException;
    /**
     * Deletes aa user.
     * @param r The user to delete
     * @throws RemoteException
     */
    public void deleteUser( UserInterface u ) throws RemoteException;
    /**
     * Updates an user.
     * @param u The user to update.
     * @throws RemoteException
     */
    public void updateUser( UserInterface u ) throws RemoteException;
    /**
     * Gets users using a user as example.
     * @param u The user used as an example
     * @return A collection of <code>User</code>
     * @throws RemoteException
     */
    public Collection getUsers( UserInterface u ) throws RemoteException;
}
