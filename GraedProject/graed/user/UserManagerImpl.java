/*
 * Created on 22 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.user;

import graed.db.DataBaseManager;
import graed.db.DataBaseUtil;
import graed.exception.DataBaseException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UserManagerImpl extends UnicastRemoteObject implements UserManager{

	/**
	 * Le gestionnaire de base de données utilisé.
	 */
	private DataBaseManager dbm;
	/**
	 * L'instance de ce manager.
	 */
    private static UserManagerImpl instance;
    
    
    private UserManagerImpl() throws RemoteException {
    	dbm = DataBaseManager.getInstance();
    }
    
    /**
     * Récupère l'instance du manager.
     * @return L'instance du manager.
     * @throws RemoteException
     */
    public static UserManagerImpl getInstance() throws RemoteException  {
    	if( instance == null ) instance = new UserManagerImpl();
    	return instance;
    }
    
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#addIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void addUser(UserInterface i) throws RemoteException {
		try {
			User u = (User)DataBaseUtil.convertStub(dbm.getSession(), i);
			dbm.add(u);
        } catch (DataBaseException e) {
        	e.printStackTrace();
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#deleteIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void deleteUser(UserInterface i) throws RemoteException {
		try {
			User u = (User)DataBaseUtil.convertStub(dbm.getSession(), i);
            dbm.delete(u);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#updateIndiponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void updateUser(UserInterface i) throws RemoteException {
		try {
			User u = (User)DataBaseUtil.convertStub(dbm.getSession(), i);
			dbm.update(u);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndiponibilites(graed.indisponibilite.Indisponibilite)
	 */
	public Collection getUsers(UserInterface i)
			throws RemoteException {
		return null;
	}

	public UserInterface createUser() throws RemoteException {
		return new User();
	}
	
}
