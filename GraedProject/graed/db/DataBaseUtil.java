/*
 * Created on 21 f�vr. 2005
 */
package graed.db;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

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
            // Cr�e la SessionFactory
        	Properties p = new Properties();
        	
        	String url = "jdbc:postgresql://"
        					+graed.conf.Configuration.getParamValue("database","host")
							+":"
							+graed.conf.Configuration.getParamValue("database","port")
							+"/"
							+graed.conf.Configuration.getParamValue("database","name");
        	
        	p.put( "hibernate.connection.url", url );
        	p.put( "hibernate.connection.username", 
        			graed.conf.Configuration.getParamValue("database","user"));
        	p.put( "hibernate.connection.password", 
        			graed.conf.Configuration.getParamValue("database","password"));
        	
            sessionFactory = new Configuration().addProperties(p).configure().buildSessionFactory();
        } catch (HibernateException ex) {
            throw new RuntimeException("Probl�me de configuration : " + ex.getMessage(), ex);
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
	 * @param stub Le stub � convertir
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
				if( s.startsWith( "set" )) {
					String r = s.replaceFirst("set", "get");
					try {
						Method mm = ori.getMethod(r, null);
						Object[] args = {mm.invoke(stub,null)};
						m.invoke( destObj, args );
					} catch ( NoSuchMethodException ignored ) {
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

