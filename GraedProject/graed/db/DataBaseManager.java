package graed.db;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import graed.exception.DataBaseException;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Example;


/**
 * @author Helder DE SOUSA
 * Classe s'occupant de la gestion des objets dans la BD
 * 
 * Design Pattern : Singleton
 */
public class DataBaseManager implements Serializable{
	/**
	 * Instance du gestionnaire de base de donn�es.
	 */
    private static DataBaseManager instance;
    /**
     * Session � la base de donn�es.
     */
	private static Session session;
	
	private DataBaseManager() {
	    try {
	        session = DataBaseUtil.currentSession();
	    } catch( HibernateException he ) {
	        throw new RuntimeException("Impossible d'obtenir une session base de donn�es : " + he.getMessage(), he);
	    }
	}
	
	/**
	 * R�cup�re l'instance du gestionnaire de base de donn�es.
	 * @return L'instance.
	 */
	public static DataBaseManager getInstance() {
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
	 * Ajoute un objet � la base de donn�es.
	 * @param dbo L'objet � ajouter.
	 * @throws HibernateException
	 */
	public void add(Object dbo) throws DataBaseException{
	    try {
	        Transaction tx = session.beginTransaction();
	        session.save(convertStub(dbo));
	        tx.commit();
	    } catch( HibernateException he ) {
	    	
	    	Throwable[] t = he.getThrowables();
	    	
	    	for( int i=0; i<t.length; ++i ) {
	    		if( t[i] instanceof java.sql.SQLException ) {
	    			Exception e = ((java.sql.SQLException)t[i]).getNextException();
	    			e.printStackTrace();
	    		}
	    	}
	    	
	    	
	        throw (DataBaseException)new DataBaseException( "Erreur lors de l'ajout dans la base de donn�es").initCause(he);
	    }
	}
	/**
	 * Supprime un objet de la base de donn�es.
	 * @param dbo L'objet � supprimer.
	 * @throws DataBaseException
	 */
	public void delete(Object dbo) throws DataBaseException{
	    try {
	        Transaction tx = session.beginTransaction();
	        session.delete(convertStub(dbo));
	        tx.commit();
	    } catch( HibernateException he ) {
	    	he.printStackTrace();
	    	
	    	Throwable[] t = he.getThrowables();
	    	
	    	for( int i=0; i<t.length; ++i ) {
	    		if( t[i] instanceof java.sql.SQLException ) {
	    			Exception e = ((java.sql.SQLException)t[i]).getNextException();
	    			e.printStackTrace();
	    		}
	    	}
	    	
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la suppression de la base de donn�es").initCause(he);
	    }
	}
	/**
	 * Met � jour un objet � la base de donn�es. 
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 * @throws DataBaseException
	 */
	public void update( Object dbo ) throws DataBaseException {
	    try {
	        Transaction tx = session.beginTransaction();
	        session.update(convertStub(dbo));
	        tx.commit();
	    } catch( HibernateException he ) {
	        throw (DataBaseException)new DataBaseException( "Erreur lors de la mise � jour de la base de donn�es").initCause(he);
	    }
	}
	
	/**
	 * Correspond � la requ�te select dans la BD
	 * @param example Objet servant d'example
	 * @return liste des objets correspondant � la requ�te de s�lection
	 * @throws DataBaseException
	 */
	public List get( Object example ) throws DataBaseException {
		Object o = convertStub(example);
		if( o==null ) return new ArrayList();
		Criteria c = session.createCriteria(o.getClass());
	    // On ignore les valeurs z�ro, la recherche est insensible � la case et utilise like pour les comparaison de strings
        c.add( Example.create(o).excludeZeroes().ignoreCase().enableLike());
        try {
            return c.list();
        }catch( HibernateException he ) {
            throw (DataBaseException)new  DataBaseException( "Erreur lors de la r�cup�ration de donn�es � partir de la base de donn�es").initCause(he);
        }
	}
	
	public Criteria createCriteria( Class c ) {
		return session.createCriteria(c);
	}
	
	/**
	 * Renvoi l'object auquel correspond le stub.
	 * @param stub Le stub � convertir
	 * @return L'object correspondant au stub 
	 */
	private Object convertStub( Object stub ) {
		try {
			Class ori = stub.getClass();
			if( !ori.getName().endsWith("_Stub") ) return stub;
			String original = ori.getName().split("_")[0];
			//System.out.println(ori.getName()+" "+original);
			Class dest = Class.forName(original);
			Object destObj = dest.newInstance();
			Method[] ms = dest.getMethods();
						
			for( int i=0; i<ms.length; ++i ) {
				Method m = ms[i];
				String s = m.getName();
				/* On s'interesse unsiquement aux setters */ 
				if( s.startsWith( "set" )) {
					/* on cr�e le getter associ� au setter */
					String r = s.replaceFirst("set", "get");
					try {
						/* on r�cup�re le getter */
						Method mm = ori.getMethod(r, null);
						Object[] args = {mm.invoke(stub,null)};
						m.invoke( destObj, args );
					} catch ( NoSuchMethodException ignored ) {
						/* Exception lev�e si on essaye d'invoquer une methode n'existant pas.
						 * Dans ce cas on l'ignore tout simplement.
						 */
						
					}
				}
			}
			
			Criteria c = session.createCriteria(destObj.getClass());
			c.add( Example.create(destObj).excludeZeroes().ignoreCase().enableLike());
			List l = c.list();
			if( l.size() > 0 ) return l.get(0);
			return destObj;
						
		} catch( Exception e ) {
			e.printStackTrace();
			return null;
		}
	}
}
