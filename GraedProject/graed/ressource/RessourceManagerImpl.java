/*
 * Created on 25 f�vr. 2005
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
        
        String[] files = new File(directoryTypes).list(new FilenameFilter() {
        	public boolean accept( File dir, String name) {
        		return name.endsWith(".class");
        	}
        });
        
        for( int i=0; i<files.length; ++i) {
        	 try {
				files[i] = files[i].split("\\.")[0];
				String packageType = directoryTypes.replaceAll("/",".");
				Ressource r = (Ressource) Class.forName(packageType+"."+files[i]).newInstance();
				types.put( r.getRessourceType() , r.getClass() );
			} catch (Exception e) {
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
    public Collection getRessources(Ressource r) throws RemoteException {
            try {
                return dbm.get(r);
            } catch (DataBaseException e) {
                throw (RemoteException)new RemoteException().initCause(e);
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
    public void addRessource(Ressource r) throws RemoteException {
        try {
            dbm.add(r);
        } catch (DataBaseException e) {
            throw (RemoteException)new RemoteException().initCause(e);
        }
    }

    /**
     * @see graed.ressource.RessourceManager#deleteRessource(graed.ressource.Ressource)
     */
    public void deleteRessource(Ressource r) throws RemoteException {
        try {
            dbm.delete(r);
            fireRessourceDeleted( new RessourceEvent(r));
        } catch (DataBaseException e) {
            throw (RemoteException)new RemoteException().initCause(e);
        }
    }

    /**
     * @see graed.ressource.RessourceManager#updateRessource(graed.ressource.Ressource)
     */
    public void updateRessource(Ressource r) throws RemoteException {
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
   		
   		return null;
   }

}