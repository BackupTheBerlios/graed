/*
 * Created on 11 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package graed.ressource.type;

import graed.ressource.Ressource;

/**
 * @author Propriétaire
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Teacher extends Ressource {
	private int id;
	private String name;
	private String firstName;
	private String office;
	private String phone; 
	private String email;
	public Teacher(){
		super("Professeur");
	}
	public Teacher(String name, String firstName, 
					String office, String phone, String email){
		this();
		this.id=0;
		this.name=name;
		this.firstName=firstName;
		this.office=office;
		this.phone=phone;
		this.email=email;		
	}
	public Teacher(int id,String name, String firstName,
						String office, String phone, String email){
			this(name,firstName,office,phone,email);
			this.id=id;
		}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String p="";
		p+="Name: "+name+" ";
		p+="First name: "+firstName+" ";
		p+="Office: "+office+" ";
		p+="Phone: "+phone+" ";
		p+="E-Mail: "+email;		
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
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
