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
	 * Correspond � la requ�te insert dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void add(Object dbo){
	}
	/**
	 * Correspond � la requ�te delete dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void delete(Object dbo){
	}
	/**
	 * Correspond � la requ�te update dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void update( Object dbo ) {
	}
	
	/**
	 * Correspond � la requ�te select dans la BD
	 * @param clazz Classe des objets � r�cup�rer
	 * @param where
	 * @return liste des objets correspondant � la requ�te de s�lection
	 */
	public Object[] get( Class clazz, List where ) {
		return null;
	}
}
