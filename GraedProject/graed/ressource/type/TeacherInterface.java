/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.ressource.type;

import graed.ressource.RessourceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface TeacherInterface extends Remote, RessourceInterface {
	/**
	 * @return Returns the email.
	 */
	public String getEmail() throws RemoteException;
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) throws RemoteException;
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() throws RemoteException;
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) throws RemoteException;
	/**
	 * @return Returns the name.
	 */
	public String getName() throws RemoteException;
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) throws RemoteException;
	/**
	 * @return Returns the office.
	 */
	public String getOffice() throws RemoteException;
	/**
	 * @param office The office to set.
	 */
	public void setOffice(String office) throws RemoteException;
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() throws RemoteException;
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) throws RemoteException;
	
	/**
	 * Controle les données
	 */
	public String control() throws RemoteException;
}
