/*
 * Created on 21 févr. 2005
 */
package graed.db;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

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
}

