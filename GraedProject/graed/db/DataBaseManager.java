package graed.db;

import java.util.List;

import graed.exception.DataBaseException;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Example;


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
	        throw (DataBaseException)new DataBaseException( "Erreur lors de l'ajout dans la base de données").initCause(he);
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
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la suppression de la base de données").initCause(he);
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
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la mise à jour de la base de données").initCause(he);
	    }
	}
	
	/**
	 * Correspond à la requête select dans la BD
	 * @param example Objet servant d'example
	 * @return liste des objets correspondant à la requête de sélection
	 * @throws DataBaseException
	 */
	public List get( Object example ) throws DataBaseException {
	    Criteria c = session.createCriteria(example.class);
	    // On ignore les valeurs zéro, la recherche est insensible à la case et utilise like pour les comparaison de strings
        c.add( Example.create(example).excludeZeroes().ignoreCase().enableLike();
        try {
            return c.list();
        }catch( HibernateException he ) {
            throw (DataBaseException)new  DataBaseException( "Erreur lors de la récupération de données à partir de la base de données").initCause(he);
        }
	}
}
