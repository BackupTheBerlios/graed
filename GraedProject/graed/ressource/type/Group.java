/*
 * Created on 11 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package graed.ressource.type;

import graed.ressource.Ressource;
import graed.user.User;

/**
 * @author Propriétaire
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Group extends Ressource {
	private String id;
	private String number;
	private String name;
	private String description;
	private String mail;
	private User user;
	private Teacher prof_responsable;
	private String options;
	public Group(){
		super("Formation");
	}
	public Group(String number,String name, String description,String mail/*Personne directeur,Secretariat s,List options */ ){
		this();
		this.number=number;
		this.name=name;
		this.description=description;
		this.mail=mail;
	}		
	public String toString() {
		String p="";
		p+="ID"+id+" ";
		p+="Number"+number+" ";
		p+="Name: "+name+" ";
		p+="Description: "+description+" ";
		p+="E-Mail: "+mail;		
		return p;
	}
	/* ******************** Getter et Setter *********************** */
	/**
	 * @return Returns the number.
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number The number to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the mail.
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail The mail to set.
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
	 * @return Returns the options.
	 */
	public String getOptions() {
		return options;
	}
	/**
	 * @param options The options to set.
	 */
	public void setOptions(String options) {
		this.options = options;
	}
	/**
	 * @return Returns the prof_responsable.
	 */
	public Teacher getProf_responsable() {
		return prof_responsable;
	}
	/**
	 * @param prof_responsable The prof_responsable to set.
	 */
	public void setProf_responsable(Teacher prof_responsable) {
		this.prof_responsable = prof_responsable;
	}
	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
