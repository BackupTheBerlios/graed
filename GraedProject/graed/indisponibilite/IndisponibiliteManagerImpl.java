package graed.indisponibilite;

import graed.db.DataBaseManager;
import graed.exception.DataBaseException;
import graed.indisponibilite.event.IndisponibiliteEvent;
import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.Ressource;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import com.toedter.calendar.JDateChooser;
import com.toedter.components.JSpinField;


import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.expression.Example;
import net.sf.hibernate.expression.Expression;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteManagerImpl implements IndisponibiliteManager {
	private DataBaseManager dbm;
    private static IndisponibiliteManagerImpl instance;
    private List toBeNotified;
    
    private IndisponibiliteManagerImpl() throws RemoteException {
    	dbm = DataBaseManager.getInstance();
    	toBeNotified = new ArrayList();
    }
    
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
            return dbm.get(i);
        } catch (DataBaseException e) {
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
	
	protected void fireIndisponibiliteDeleted( IndisponibiliteEvent ie ) {
		for( Iterator i=toBeNotified.iterator(); i.hasNext(); ) {
	           ((IndisponibiliteListener)i.next()).indisponibiliteDeleted(ie);
	       }
	}

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
	
	public static void main( String[] args ) throws RemoteException {
		/*Ressource t = new Teacher("Zipstein", "Mark", "", "", "zipstein@univ-mlv.fr");
		Ressource u = new Teacher("Forax", "Remi", "", "", "forax@univ-mlv.fr");
		Ressource r = new Room("2b104","Copernic","Copernic",40);
		java.sql.Date.valueOf("2005-06-10");
		Indisponibilite in = new Indisponibilite( 
				java.sql.Date.valueOf("2005-06-10"),
				java.sql.Date.valueOf("2005-06-10"),
				java.sql.Time.valueOf("15:00:00"),
				2, "Unique", "Réseau", "Cours");
		in.addRessource(t);
		in.addRessource(r);
		
		RessourceManagerImpl.getInstance().addRessource(t);
		RessourceManagerImpl.getInstance().addRessource(r);
		RessourceManagerImpl.getInstance().addRessource(u);
		
		IndisponibiliteManagerImpl.getInstance().addIndisponibilite(in);*/
		
		Ressource zip = (Ressource)RessourceManagerImpl.getInstance().getRessources(new Teacher("Zipstein", null, null, null, null)).iterator().next();
		Ressource forax = (Ressource)RessourceManagerImpl.getInstance().getRessources(new Teacher("Forax", null, null, null, null)).iterator().next();
		
		Collection c = IndisponibiliteManagerImpl.getInstance().getIndisponibilites( zip, java.sql.Date.valueOf("2005-06-08"),java.sql.Date.valueOf("2005-06-15")  );
		for( Iterator i = c.iterator(); i.hasNext(); ) {
			System.out.println(((Indisponibilite)i.next()).getRessources());
		}
		
		SpinnerDateModel dateModel = new SpinnerDateModel(
	        new Date(7*60*60*1000), new Date(7*60*60*1000), new Date(18*60*60*1000), Calendar.HOUR_OF_DAY);
	    
	    JSpinner spinner = new JSpinner(dateModel);
	    
	    
	    spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
	    
		JFrame jf = new JFrame();
		jf.getContentPane().add(spinner);
		jf.pack();
		jf.setVisible(true);
		
	}
}
