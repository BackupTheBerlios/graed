package graed.indisponibilite;

import graed.callback.Callback;
import graed.db.DataBaseManager;
import graed.db.DataBaseUtil;
import graed.exception.DataBaseException;
import graed.indisponibilite.event.IndisponibiliteEvent;
import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.Ressource;
import graed.ressource.RessourceInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.expression.Expression;

/**
 * @author Helder DE SOUSA
 * Classe permettant la gestion des indisponibilités.
 * 
 * Design pattern : singleton
 */
public class IndisponibiliteManagerImpl extends UnicastRemoteObject implements IndisponibiliteManager{
	/**
	 * Le gestionnaire de base de données utilisé.
	 */
	private DataBaseManager dbm;
	/**
	 * L'instance de ce manager.
	 */
    private static IndisponibiliteManagerImpl instance;
    /**
     * Liste des listeners écoutants ce manager.
     */
    private List toBeNotified;
    
    private IndisponibiliteManagerImpl() throws RemoteException {
    	dbm = DataBaseManager.getInstance();
    	toBeNotified = new ArrayList();
    }
    
    /**
     * Récupère l'instance du manager.
     * @return L'instance du manager.
     * @throws RemoteException
     */
    public static IndisponibiliteManagerImpl getInstance() throws RemoteException  {
    	if( instance == null ) instance = new IndisponibiliteManagerImpl();
    	return instance;
    }
    
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#addIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void addIndisponibilite(IndisponibiliteInterface i) throws RemoteException {
		try {
			Indisponibilite in = (Indisponibilite)DataBaseUtil.convertStub(dbm.getSession(), i);
			Set s = new HashSet();
			for( Iterator it = in.getRessources().iterator(); it.hasNext(); ) {
				s.add(
						DataBaseUtil.convertStub(dbm.getSession(), it.next())
						);
			}
			in.getRessources().clear();
			in.setRessources(s);
            dbm.add(in);
            fireIndisponibiliteAdded( i);
        } catch (DataBaseException e) {
        	e.printStackTrace();
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#deleteIndisponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void deleteIndisponibilite(IndisponibiliteInterface i) throws RemoteException {
		try {
			Indisponibilite in = (Indisponibilite)DataBaseUtil.convertStub(dbm.getSession(), i);
			Set s = new HashSet();
			for( Iterator it = in.getRessources().iterator(); it.hasNext(); ) {
				s.add(
						DataBaseUtil.convertStub(dbm.getSession(), it.next())
						);
			}
			in.getRessources().clear();
			in.setRessources(s);
            dbm.delete(in);
            fireIndisponibiliteDeleted( i);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#updateIndiponibilite(graed.indisponibilite.Indisponibilite)
	 */
	public void updateIndiponibilite(IndisponibiliteInterface i) throws RemoteException {
		System.out.println(i.print());
		try {
			System.out.println("Update");
			System.out.println("Ressources : "+i.getRessources());
			
			Set set = i.getRessources();
			i.setRessources(new HashSet());
			Indisponibilite in = (Indisponibilite)DataBaseUtil.convertStub(dbm.getSession(), i);
			i.setRessources(set);
			Set s = new HashSet();
			
			for( Iterator it = set.iterator(); it.hasNext(); ) {
				s.add(
						DataBaseUtil.convertStub(dbm.getSession(), it.next())
						);
			}
			in.getRessources().clear();
			in.setRessources(s);
			dbm.update(in);
            fireIndisponibiliteUpdated( i);
        } catch (DataBaseException e) {
        	throw new RemoteException(e.getMessage());
        }
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndiponibilites(graed.indisponibilite.Indisponibilite)
	 */
	public Collection getIndisponibilites(IndisponibiliteInterface i)
			throws RemoteException {
		try {
			Criteria c = dbm.createCriteria(Indisponibilite.class);
			
			if( i.getDebut() != null ) c.add(Expression.ge("debut",i.getDebut()) );
			if( i.getFin() != null ) c.add(Expression.le("fin",i.getFin()) );
			if( i.getDuree() != 0 ) c.add(Expression.eq( "duree", new Integer(i.getDuree()) ) );
			if( i.getHdebut()!=null ) c.add( Expression.eq("hdebut", i.getHdebut()) ) ;
			if( i.getLibelle() != null ) c.add( Expression.eq("libelle", i.getLibelle()).ignoreCase() ) ;
			if( i.getType() != null ) c.add( Expression.eq("type", i.getType()).ignoreCase() ) ;
			if( i.getPeriodicite() != null ) c.add( Expression.eq("periodicite", i.getPeriodicite()) ) ;
			
			boolean first = true;
			Criteria sub = c;
			
			for( Iterator ii = i.getRessources().iterator(); ii.hasNext(); ) {
				if( first ) {
					first = false;
					sub = c.createCriteria("ressources");
				}
				Ressource r = (Ressource)ii.next();
				sub.add(Expression.eq("id_ressource", r.getId_ressource()));
			}
			
			return sub.list();
        
		} catch (Exception e) {
			e.printStackTrace();
        	throw new RemoteException(e.getMessage());
        }
	}

	protected Criteria getCriteriaBetween( Date begin, Date end ) {
		return dbm.createCriteria(Indisponibilite.class)
			.add(Expression.or(
					Expression.between("debut", begin, end ),
					Expression.between("fin", begin, end ))
			);
	}
	
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndisponibilitesBetween(java.util.Date, java.util.Date)
	 */
	public Collection getIndisponibilites(Date begin, Date end) throws RemoteException {
		try {
			return getCriteriaBetween(begin, end).list();
		} catch(Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndisponibilitesBetween(graed.ressource.Ressource)
	 */
	public Collection getIndisponibilites( RessourceInterface r ) throws RemoteException {
		
		try {
			return dbm.createCriteria(Indisponibilite.class)
			.createCriteria("ressources")
				.add( Expression.eq("id_ressource", r.getId_ressource()) )
					.list();
		} catch(Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#getIndisponibilitesBetween(graed.ressource.Ressource, java.util.Date, java.util.Date)
	 */
	public Collection getIndisponibilites( RessourceInterface r, Date begin, Date end ) throws RemoteException {
		try {
			return dbm.createCriteria(Indisponibilite.class)
			.add(Expression.or(
					Expression.between("debut", begin, end ),
					Expression.between("fin", begin, end ))
			).createCriteria("ressources")
				.add( Expression.eq("id_ressource", r.getId_ressource())).list();
		} catch(Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#createIndisponibilite()
	 */
	public IndisponibiliteInterface createIndisponibilite(Date debut, Date fin, Time hdebut, int duree, String periodicite,
			String libelle, String type) throws RemoteException {
		return new Indisponibilite( debut,  fin,  hdebut,  duree,  periodicite,
				 libelle,  type);
	}
	
	public IndisponibiliteInterface createIndisponibilite() throws RemoteException {
		return new Indisponibilite();
	}
	
	/**
	 * @see graed.ressource.RessourceManager#unregister(graed.callback.Callback)
	 */
	public void unregister(Callback c) throws RemoteException {
		toBeNotified.remove(c);
	}
	
	/**
     * @see graed.ressource.RessourceManager#registerForNotification(java.lang.Object)
     */
    public void registerForNotification(Callback c) throws RemoteException {
        toBeNotified.add(c);
    }
    
    protected void fireIndisponibiliteDeleted( IndisponibiliteInterface in  ) {

       	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
            Callback c =(Callback)i.next(); 
       		try {
            	c.notify(in, Callback.DELETE);
    		} catch (Exception e) {
    			try {
    				unregister(c);
    			} catch (RemoteException ignored) {
    			}
    		}
        }
    }
    
    protected void fireIndisponibiliteUpdated( IndisponibiliteInterface in  ) {

       	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
            Callback c =(Callback)i.next(); 
       		try {
            	c.notify(in, Callback.UPDATE);
    		} catch (Exception e) {
    			try {
    				unregister(c);
    			} catch (RemoteException ignored) {
    			}
    		}
        }
    }
    
    protected void fireIndisponibiliteAdded( IndisponibiliteInterface in  ) {

       	for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
            Callback c =(Callback)i.next(); 
       		try {
            	c.notify(in, Callback.ADD);
    		} catch (Exception e) {
    			try {
    				unregister(c);
    			} catch (RemoteException ignored) {
    			}
    		}
        }
    }
}
