/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.ressource.type;

import graed.ressource.RessourceInterface;
import graed.user.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface GroupInterface extends Remote, RessourceInterface {
	/**
	 * @return Returns the number.
	 */
	public String getNumber() throws RemoteException;
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) throws RemoteException;
	/**
	 * @return Returns the description.
	 */
	public String getDescription() throws RemoteException;
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) throws RemoteException;
	/**
	 * @return Returns the mail.
	 */
	public String getMail() throws RemoteException;
	/**
	 * @param mail The mail to set.
	 */
	public void setMail(String mail) throws RemoteException;
	/**
	 * @return Returns the name.
	 */
	public String getName() throws RemoteException;
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) throws RemoteException;
	/**
	 * @return Returns the options.
	 */
	public String getOptions() throws RemoteException;
	/**
	 * @param options The options to set.
	 */
	public void setOptions(String options) throws RemoteException;
	/**
	 * @return Returns the prof_responsable.
	 */
	public TeacherInterface getProf_responsable() throws RemoteException;
	/**
	 * @param prof The prof_responsable to set.
	 */
	public void setProf_responsable(TeacherInterface prof) throws RemoteException;
	/**
	 * @return Returns the user.
	 */
	public User getUser() throws RemoteException;
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) throws RemoteException;
}
