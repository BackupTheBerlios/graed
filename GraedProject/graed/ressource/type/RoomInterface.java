/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.ressource.type;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface RoomInterface extends Remote {
	/**
	 * @return Returns the batiment.
	 */
	public String getBatiment() throws RemoteException;
	/**
	 * @param batiment The batiment to set.
	 */
	public void setBatiment(String batiment) throws RemoteException;
	/**
	 * @return Returns the capacite.
	 */
	public int getCapacite() throws RemoteException;
	/**
	 * @param capacite The capacite to set.
	 */
	public void setCapacite(int capacite) throws RemoteException;
	/**
	 * @return Returns the lieu.
	 */
	public String getLieu() throws RemoteException;
	/**
	 * @param lieu The lieu to set.
	 */
	public void setLieu(String lieu) throws RemoteException;
	/**
	 * @return Returns the nom.
	 */
	public String getNom() throws RemoteException;
	/**
	 * @param nom The nom to set.
	 */
	public void setNom(String nom) throws RemoteException;
}
