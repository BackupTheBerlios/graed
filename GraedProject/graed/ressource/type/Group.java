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
public class Group extends Ressource {
	private int id;
	private String number;
	private String name;
	private String description;
	private String mail;
	private int id_utilisateur;
	private int id_prof_responsable;
	private String options;
	public Group(){
		super("Formation");
	}
	public Group(String number,String name, String description,String mail/*Personne directeur,Secretariat s,List options */ ){
		this();
		id=0;
		this.number=number;
		this.name=name;
		this.description=description;
		this.mail=mail;
	}
	public Group (int id, String number, String name, String description, String mail){
		this(number,name, description,mail);
		this.id=id;
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
}
