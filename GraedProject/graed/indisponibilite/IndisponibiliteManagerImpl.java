package graed.indisponibilite;

import graed.db.DataBaseManager;
import graed.exception.DataBaseException;
import graed.indisponibilite.event.IndisponibiliteEvent;
import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.Ressource;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
public class IndisponibiliteManagerImpl implements IndisponibiliteManager, Serializable{
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

	/**
	 * @see graed.indisponibilite.IndisponibiliteManager#registerForNotification(graed.indisponibilite.event.IndisponibiliteListener)
	 */
	public void registerForNotification(IndisponibiliteListener il)
			throws RemoteException {
		toBeNotified.add(il);
	}
	
	/**
	 * Previent les listeners qu'une indisponibilite a été supprimée.
	 * @param ie L'évennement à dispatcher
	 */
	protected void fireIndisponibiliteDeleted( IndisponibiliteEvent ie ) {
		for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
	           ((IndisponibiliteListener)i.next()).indisponibiliteDeleted(ie);
	       }
	}

	/**
	 * Previent les listeners qu'une indisponibilite a été mise à jour.
	 * @param ie L'évennement à dispatcher
	 */
	protected void fireIndisponibiliteUpdated( IndisponibiliteEvent ie ) {
		for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
	           ((IndisponibiliteListener)i.next()).indisponibiliteUpdated(ie);
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
	public Collection getIndisponibilites( Ressource r ) throws RemoteException {
		
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
	public Collection getIndisponibilites( Ressource r, Date begin, Date end ) throws RemoteException {
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
}
