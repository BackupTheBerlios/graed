package graed.db;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;


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
	 */
	public void add(Object dbo){
	}
	/**
	 * Correspond à la requête delete dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 */
	public void delete(Object dbo){
	}
	/**
	 * Correspond à la requête update dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 */
	public void update( Object dbo ) {
	}
	
	/**
	 * Correspond à la requête select dans la BD
	 * @param clazz Classe des objets à récupérer
	 * @param where
	 * @return liste des objets correspondant à la requête de sélection
	 */
	public Object[] get( Class clazz, List where ) {
		return null;
	}
}
