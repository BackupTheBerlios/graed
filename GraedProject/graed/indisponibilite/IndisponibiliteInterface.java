/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.indisponibilite;

import graed.ressource.RessourceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IndisponibiliteInterface extends Remote {
	/**
	 * @return Returns the id.
	 */
	public String getId() throws RemoteException;
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) throws RemoteException;
	/**
	 * @return Returns the ressources.
	 */
	public Set getRessources() throws RemoteException;
	/**
	 * @param ressources The ressources to set.
	 */
	public void setRessources(Set ressources) throws RemoteException;
	
	public void addRessource( RessourceInterface interface1 ) throws RemoteException;
		
	/**
	 * @return Returns the debut.
	 */
	public Date getDebut() throws RemoteException;
	/**
	 * @param debut The debut to set.
	 */
	public void setDebut(Date debut) throws RemoteException;
	/**
	 * @return Returns the duree.
	 */
	public int getDuree() throws RemoteException;
	/**
	 * @param duree The duree to set.
	 */
	public void setDuree(int duree) throws RemoteException;
	/**
	 * @return Returns the fin.
	 */
	public Date getFin() throws RemoteException;
	/**
	 * @param fin The fin to set.
	 */
	public void setFin(Date fin) throws RemoteException;
	/**
	 * @return Returns the libelle.
	 */
	public String getLibelle() throws RemoteException;
	/**
	 * @param libelle The libelle to set.
	 */
	public void setLibelle(String libelle) throws RemoteException;
	/**
	 * @return Returns the periodicite.
	 */
	public String getPeriodicite() throws RemoteException;
	/**
	 * @param periodicite The periodicite to set.
	 */
	public void setPeriodicite(String periodicite) throws RemoteException;
	/**
	 * @return Returns the type.
	 */
	public String getType() throws RemoteException;
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) throws RemoteException;
	
	
	/**
	 * @return Returns the hdebut.
	 */
	public Time getHdebut() throws RemoteException;
	/**
	 * @param hdebut The hdebut to set.
	 */
	public void setHdebut(Time hdebut) throws RemoteException;
	/**
	 * @return Returns the print.
	 */
	public String print() throws RemoteException;
	/**
	 * Copies the given indisponibilite into the current indsponibilite
	 * @param in The indisponibilite to copy
	 * @throws RemoteException
	 */
	public void copy( IndisponibiliteInterface in ) throws RemoteException; 
	
}
