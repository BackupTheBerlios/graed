/*
 * Created on 22 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UserInterface extends Remote{
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
	 * @return Returns the fonction.
	 */
	public String getFonction() throws RemoteException;
	/**
	 * @param fonction The fonction to set.
	 */
	public void setFonction(String fonction) throws RemoteException;
	/**
	 * @return Returns the id.
	 */
	public String getId() throws RemoteException;
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) throws RemoteException;
	/**
	 * @return Returns the login.
	 */
	public String getLogin()throws RemoteException;
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login)throws RemoteException;
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
	 * @return Returns the password.
	 */
	public String getPassword() throws RemoteException;
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password)throws RemoteException;
	/**
	 * @return Returns the phone.
	 */
	public String getPhone()throws RemoteException;
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) throws RemoteException;
}
