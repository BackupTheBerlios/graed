/*
 * Created on 25 févr. 2005
 */
package graed.ressource;

import graed.callback.Callback;
import graed.db.DataBaseManager;
import graed.db.DataBaseUtil;
import graed.exception.DataBaseException;
import graed.indisponibilite.IndisponibiliteInterface;
import graed.indisponibilite.IndisponibiliteManagerImpl;

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

import net.sf.hibernate.Query;

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
        		System.out.println(">>>> "+name+" "+!(name.endsWith("Interface.class")||name.endsWith("Stub.class")));
        		return (!(name.endsWith("Interface.class")
        					||name.endsWith(".java")
							||name.endsWith("Stub.class")
						))&&name.endsWith(".class");							
        	}
        });
        
        for( int i=0; i<files.length; ++i) {
        	try {
				files[i] = files[i].split("\\.")[0];
				String packageType = directoryTypes.replaceAll("/",".");
				Ressource r = (Ressource) Class.forName(packageType+"."+files[i]).newInstance();
				
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
            	r.setId_ressource(null);
            	return dbm.get(r);
            } catch (DataBaseException e) {
            	throw new RemoteException(e.getMessage());
            }
    }

    /**
     * @see graed.ressource.RessourceManager#registerForNotification(java.lang.Object)
     */
    public void registerForNotification(Callback c) throws RemoteException {
        toBeNotified.add(c);
    }

    /**
     * @see graed.ressource.RessourceManager#addRessource(graed.ressource.Ressource)
     */
    public void addRessource(RessourceInterface r) throws RemoteException {
        try {
            dbm.add(r);
            fireRessourceAdded( r);
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
            /*IndisponibiliteManagerImpl im = IndisponibiliteManagerImpl.getInstance();
            Collection c=im.getIndisponibilites(r);
            Ressource rr = (Ressource)DataBaseUtil.convertStub(dbm.getSession(),r);
            for( Iterator i = c.iterator(); i.hasNext(); ) {
            	IndisponibiliteInterface ii = (IndisponibiliteInterface)i.next();
            	ii.getRessources().remove(rr);
            	im.updateIndiponibilite(ii);
            }*/
            fireRessourceDeleted( r);
        } catch (Exception e) {
        	throw new RemoteException(e.getMessage());
        }
    }

    /**
     * @see graed.ressource.RessourceManager#updateRessource(graed.ressource.Ressource)
     */
    public void updateRessource(RessourceInterface r) throws RemoteException {
        try {
            dbm.update(r);
            fireRessourceUpdated( r);
        } catch (DataBaseException e) {
            throw (RemoteException)new RemoteException().initCause(e);
        }
    }
    
    
   protected void fireRessourceDeleted( RessourceInterface re  ) {

   	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
        Callback c =(Callback)i.next(); 
   		try {
        	c.notify(re, Callback.DELETE);
		} catch (Exception e) {
			try {
				unregister(c);
			} catch (RemoteException ignored) {
			}
		}
    }
   }
   
   protected void fireRessourceUpdated( RessourceInterface re  ) {

   	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
        Callback c =(Callback)i.next(); 
   		try {
        	c.notify(re, Callback.UPDATE);
		} catch (Exception e) {
			try {
				unregister(c);
			} catch (RemoteException ignored) {
			}
		}
    }
   }
   
   protected void fireRessourceAdded( RessourceInterface re  ) {
   
   	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
        Callback c =(Callback)i.next(); 
   		try {
        	c.notify(re, Callback.ADD);
		} catch (Exception e) {
			try {
				unregister(c);
			} catch (RemoteException ignored) {
			}
		}
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
	System.out.println( type );
	System.out.println( (Class)types.get(type) );
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

/* (non-Javadoc)
 * @see graed.ressource.RessourceManager#unregister(graed.callback.Callback)
 */
public void unregister(Callback c) throws RemoteException {
	toBeNotified.remove(c);
}
}
