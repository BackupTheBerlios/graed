package graed.db;

import graed.exception.DataBaseException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Criterion;
import net.sf.hibernate.expression.Order;


/**
 * Classe s'occupant de la gestion des objets dans la BD
 * Design Pattern : Singleton
 */
public class DataBaseManager{
    DataBaseManager instance;
	protected Session session;
	
	private DataBaseManager() throws HibernateException {
	    session = DataBaseUtil.currentSession();
	}
	
	DataBaseManager getInstance() throws HibernateException {
	    if( instance == null ) 
	        instance = new DataBaseManager();
	    return instance;
	}
	
	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
	    super.finalize();
	    DataBaseUtil.closeSession();
	}
	
	/**
	 * Correspond à la requête insert dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 * @throws HibernateException
	 */
	public void add(Object dbo) throws DataBaseException{
	    try {
	        Transaction tx = session.beginTransaction();
	        session.save(dbo);
	        tx.commit();
	    } catch( HibernateException he ) {
	        throw (DataBaseException)new DataBaseException( "Erreur lors de l'ajout dans la base de donnée").initCause(he);
	    }
	}
	/**
	 * Correspond à la requête delete dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 * @throws DataBaseException
	 */
	public void delete(Object dbo) throws DataBaseException{
	    try {
	        Transaction tx = session.beginTransaction();
	        session.delete(dbo);
	        tx.commit();
	    } catch( HibernateException he ) {
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la suppression de la base de donnée").initCause(he);
	    }
	}
	/**
	 * Correspond à la requête update dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 * @throws DataBaseException
	 */
	public void update( Object dbo ) throws DataBaseException {
	    try {
	        Transaction tx = session.beginTransaction();
	        session.update(dbo);
	        tx.commit();
	    } catch( HibernateException he ) {
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la mise à jour de la base de donnée").initCause(he);
	    }
	}
	
	/**
	 * Correspond à la requête select dans la BD
	 * @param clazz Classe des objets à récupérer
	 * @param criterions les critères de recherche à utiliser
	 * @param orders les ordres de tris
	 * @return liste des objets correspondant à la requête de sélection
	 * @throws DataBaseException
	 */
	public Object[] get( Class clazz, Criterion[] criterions, Order[] orders ) throws DataBaseException {
	    Criteria c = session.createCriteria(clazz);
	    
	    for( int i=0; i<criterions.length; ++i )
	        c.add(criterions[i]);
	    
	    for( int i=0; i<orders.length; ++i )
	        c.addOrder(orders[i]);
	    
		try {
            return c.list().toArray();
        } catch (HibernateException he) {
            throw (DataBaseException)new DataBaseException( "Erreur lors de la récupération de données de la base de donnée").initCause(he);
        }
        
	}
}
