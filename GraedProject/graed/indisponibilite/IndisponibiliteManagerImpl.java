package graed.indisponibilite;

import graed.db.DataBaseManager;
import graed.exception.DataBaseException;
import graed.indisponibilite.event.IndisponibiliteEvent;
import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.Ressource;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.Example;
import net.sf.hibernate.expression.Expression;

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
        	e.printStackTrace();
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
	public Collection getIndisponibilites(Indisponibilite i)
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
	
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndisponibilitesBetween(java.util.Date, java.util.Date)
	 */
	public Collection getIndisponibilitesBetween(Date begin, Date end) throws RemoteException {
		Criteria c = dbm.createCriteria(Indisponibilite.class);
		c.add(Expression.between("debut", begin, end ));
		try {
			return c.list();
		} catch(Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	public Collection getIndisponibilitesForRessource( Ressource r ) throws RemoteException {
		try {
			Criteria c = dbm.createCriteria(Indisponibilite.class)
						.createCriteria("ressources")
							.add( Example.create(r).excludeZeroes().ignoreCase().enableLike() );
		
			return c.list();
		} catch(Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	public static void main( String[] args ) throws RemoteException {
		Ressource t = new Teacher("Zipstein", "Mark", "", "", "zipstein@univ-mlv.fr");
		Ressource r = new Room("2b104","Copernic","Copernic",40);
		Indisponibilite in = new Indisponibilite( new Date(), new Date(), 2, "Unique", "Réseau", "Cours");
		in.addRessource(t);
		in.addRessource(r);
		
		RessourceManagerImpl.getInstance().addRessource(t);
		RessourceManagerImpl.getInstance().addRessource(r);
		
		IndisponibiliteManagerImpl.getInstance().addIndisponibilite(in);
		
		Indisponibilite inn = new Indisponibilite();
		inn.addRessource(new Teacher("Forax", null, null, null, null));
		
		/*Collection c = IndisponibiliteManagerImpl.getInstance().getIndisponibilites(inn);
		for( Iterator i = c.iterator(); i.hasNext(); ) {
			System.out.println(i.next());
		}*/
	}
}
