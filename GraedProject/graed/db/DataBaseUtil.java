/*
 * Created on 21 févr. 2005
 */
package graed.db;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.expression.Example;

/**
 * @author Helder DE SOUSA
 */
public class DataBaseUtil {
	private static final SessionFactory sessionFactory;

    static {
        try {
            // Crée la SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            throw new RuntimeException("Problème de configuration : " + ex.getMessage(), ex);
        }
    }

	public static final ThreadLocal session = new ThreadLocal();
	
	public static Session currentSession() throws HibernateException {
	   	Session s = (Session) session.get();
	    // Ouvre une nouvelle Session, si ce Thread n'en a aucune
	    if (s == null) {
	    	s = sessionFactory.openSession();
	        session.set(s);
	    }
	    return s;
	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null)
			s.close();
    }
	
	/**
	 * Renvoi l'object auquel correspond le stub.
	 * @param stub Le stub à convertir
	 * @return L'object correspondant au stub 
	 */
	public static Object convertStub( Session session, Object stub ) {
		try {
			Class ori = stub.getClass();
			
			if( !ori.getName().endsWith("_Stub") ) return stub;
			
			String original = ori.getName().split("_")[0];
			Class dest = Class.forName(original);
			Object destObj = dest.newInstance();
			Method[] ms = dest.getMethods();
						
			for( int i=0; i<ms.length; ++i ) {
				Method m = ms[i];
				String s = m.getName();
				/* On s'interesse unsiquement aux setters */ 
				if( s.startsWith( "set" )) {
					/* on crée le getter associé au setter */
					String r = s.replaceFirst("set", "get");
					try {
						/* on récupére le getter */
						Method mm = ori.getMethod(r, null);
						Object[] args = {mm.invoke(stub,null)};
						m.invoke( destObj, args );
					} catch ( NoSuchMethodException ignored ) {
						/* Exception levée si on essaye d'invoquer une methode n'existant pas.
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

