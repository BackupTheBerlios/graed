/*
 * Created on 11 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package graed.ressource.type;

import java.rmi.Remote;
import java.rmi.RemoteException;

import graed.ressource.Ressource;

/**
 * @author Propriétaire
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Teacher extends Ressource implements TeacherInterface{
	private String name;
	private String firstName;
	private String office;
	private String phone; 
	private String email;
	public Teacher() throws RemoteException{
		super("Professeur");
	}
	public Teacher(String name, String firstName, 
					String office, String phone, String email) throws RemoteException{
		this();
		this.name=name;
		this.firstName=firstName;
		this.office=office;
		this.phone=phone;
		this.email=email;		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String p="";
		p+=name+" ";
		p+=firstName;	
		return p;
	}
	/* ******************** Getter et Setter *********************** */
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the office.
	 */
	public String getOffice() {
		return office;
	}
	/**
	 * @param office The office to set.
	 */
	public void setOffice(String office) {
		this.office = office;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
