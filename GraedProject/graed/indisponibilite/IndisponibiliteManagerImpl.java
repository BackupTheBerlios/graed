package graed.indisponibilite;

import graed.db.DataBaseManager;
import graed.exception.DataBaseException;
import graed.indisponibilite.event.IndisponibiliteEvent;
import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteManagerImpl implements IndisponibiliteManager {
	private DataBaseManager dbm;
    private static IndisponibiliteManagerImpl instance;
    private List toBeNotified;
    
    private IndisponibiliteManagerImpl() throws RemoteException {
    	dbm = DataBaseManager.getInstance();
    	toBeNotified = new ArrayList();
    }
    
    public static IndisponibiliteManagerImpl getInstance() throws RemoteException  {
    	if( instance == null ) instance = new IndisponibiliteManagerImpl();
    	return instance;
    }
    
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#addIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void addIndisponibilite(Indisponibilite i) throws RemoteException {
		try {
            dbm.add(i);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#deleteIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void deleteIndisponibilite(Indisponibilite i) throws RemoteException {
		try {
            dbm.delete(i);
            fireIndisponibiliteDeleted( new IndisponibiliteEvent(i));
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#updateIndiponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void updateIndiponibilite(Indisponibilite i) throws RemoteException {
		try {
            dbm.update(i);
            fireIndisponibiliteUpdated( new IndisponibiliteEvent(i));
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndiponibilites(graed.indisponibilite.Indisponibilite)
	 */
	public Collection getIndiponibilites(Indisponibilite i)
			throws RemoteException {
		try {
            return dbm.get(i);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#registerForNotification(graed.indisponibilite.event.IndisponibiliteListener)
	 */
	public void registerForNotification(IndisponibiliteListener il)
			throws RemoteException {
		toBeNotified.add(il);
	}
	
	protected void fireIndisponibiliteDeleted( IndisponibiliteEvent ie ) {
		for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
	           ((IndisponibiliteListener)i.next()).indisponibiliteDeleted(ie);
	       }
	}

	protected void fireIndisponibiliteUpdated( IndisponibiliteEvent ie ) {
		for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
	           ((IndisponibiliteListener)i.next()).indisponibiliteUpdated(ie);
	       }
	}
	
	public static void main (String[] args ) throws RemoteException {
		Teacher t = new Teacher("Zipstein","Mark","","","zipstein@univ-mlv.fr");
		Room r = new Room("2B104","Copernic","Copernic",40);
		RessourceManagerImpl.getInstance().addRessource(t);
		RessourceManagerImpl.getInstance().addRessource(r);
		Indisponibilite i = new Indisponibilite();
		i.addRessource(t);
		i.addRessource(r);
		IndisponibiliteManagerImpl.getInstance().addIndisponibilite(i);
	}
	
}
