/*
 * Created on 25 févr. 2005
 */
package graed.ressource;

import graed.db.DataBaseManager;
import graed.exception.DataBaseException;
import graed.ressource.event.RessourceEvent;
import graed.ressource.event.RessourceListener;

import java.io.File;
import java.io.FilenameFilter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Helder DE SOUSA
 */
public class RessourceManagerImpl extends UnicastRemoteObject implements RessourceManager {
    private DataBaseManager dbm;
    private static RessourceManagerImpl instance;
    private List toBeNotified;
    private Hashtable types;
    private final static String directoryTypes = "graed/ressource/type";
    
    private RessourceManagerImpl() throws RemoteException {
        dbm = DataBaseManager.getInstance();
        toBeNotified = new ArrayList();
        
        types = new Hashtable();
        
        String[] files = new File("build/"+directoryTypes).list(new FilenameFilter() {
        	public boolean accept( File dir, String name) {
        		return !name.endsWith("Interface.class")&&!name.endsWith("Stub.class");
        	}
        });
        
        for( int i=0; i<files.length; ++i) {
        	 try {
				files[i] = files[i].split("\\.")[0];
				String packageType = directoryTypes.replaceAll("/",".");
				Ressource r = (Ressource) Class.forName(packageType+"."+files[i]).newInstance();
				
				System.out.println( "Type : "+r.getType()+" Classe : "+r.getClass() );
				
				types.put( r.getType() , r.getClass() );
			} catch (Exception e) {
				e.printStackTrace();
				throw new RemoteException(e.getMessage());
			}
        }
    }
    
    public static RessourceManagerImpl getInstance() throws RemoteException {
        if( instance == null ) instance = new RessourceManagerImpl();
        return instance;
    }
    
    /**
     * @see graed.ressource.RessourceManager#getRessources(graed.ressource.Ressource)
     */
    public Collection getRessources(RessourceInterface r) throws RemoteException {
            try {
                return dbm.get(r);
            } catch (DataBaseException e) {
            	throw new RemoteException(e.getMessage());
            }
    }

    /**
     * @see graed.ressource.RessourceManager#registerForNotification(java.lang.Object)
     */
    public void registerForNotification(RessourceListener rl) throws RemoteException {
        toBeNotified.add(rl);
    }

    /**
     * @see graed.ressource.RessourceManager#addRessource(graed.ressource.Ressource)
     */
    public void addRessource(RessourceInterface r) throws RemoteException {
        try {
            dbm.add(r);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @see graed.ressource.RessourceManager#deleteRessource(graed.ressource.Ressource)
     */
    public void deleteRessource(RessourceInterface r) throws RemoteException {
        try {
            dbm.delete(r);
            fireRessourceDeleted( new RessourceEvent(r));
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @see graed.ressource.RessourceManager#updateRessource(graed.ressource.Ressource)
     */
    public void updateRessource(RessourceInterface r) throws RemoteException {
        try {
            dbm.update(r);
            fireRessourceUpdated( new RessourceEvent(r));
        } catch (DataBaseException e) {
            throw (RemoteException)new RemoteException().initCause(e);
        }
    }
    
    
   protected void fireRessourceDeleted( RessourceEvent re  ) {
       for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
           ((RessourceListener)i.next()).ressourceDeleted(re);
       }
   }
   
   protected void fireRessourceUpdated( RessourceEvent re  ) {
       for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
           ((RessourceListener)i.next()).ressourceUpdated(re);
       }
   }

   /**
    * @see graed.ressource.RessourceManager#getressourcesTypes()
    */
   public String[] getRessourcesTypes() throws RemoteException {
   		Set s = types.keySet();
   		String[] st = new String[s.size()];
   		return (String[])types.keySet().toArray(st);
   }

   /**
    * @see graed.ressource.RessourceManager#getRessourcesByType(java.lang.String)
    */
   public Collection getRessourcesByType(String type) throws RemoteException {
   		try {
   			System.out.println(type);
			return dbm.get(((Class)types.get(type)).newInstance());
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RemoteException(e.getMessage());
		}
   }
   
   public static void main( String[] args ) throws RemoteException {
   		RessourceManager rm = RessourceManagerImpl.getInstance();
   		Collection c = rm.getRessourcesByType("Salle");
   		for( Iterator i=c.iterator(); i.hasNext(); ) {
   			System.out.println(i.next());
   		}
   }

/**
 * @see graed.ressource.RessourceManager#createRessource(java.lang.String)
 */
public RessourceInterface createRessource(String type) throws RemoteException {
	return createRessource( (Class)types.get(type) );
}

/**
 * @see graed.ressource.RessourceManager#createRessource(java.lang.Class)
 */
public RessourceInterface createRessource(Class type) throws RemoteException {
	try {
		return (RessourceInterface)type.newInstance();
	} catch (Exception e) {
		e.printStackTrace();
		throw new RemoteException(e.getMessage());
	}
}

}
