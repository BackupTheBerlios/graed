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
public class Room extends Ressource {
	private int id;
	private String nom;
	private String batiment;
	private String lieu;
	private int capacite;
	public Room(String nom, String lieu,String batiment, int capacite){
		super("Salle");
		this.id=0;
		this.nom=nom;
		this.batiment=batiment;
		this.lieu=lieu;
		this.capacite=capacite;
	}
	public Room (int id,String nom, String lieu,String batiment,int capacite){
		this(nom, batiment, lieu, capacite);
		this.id=id;
	}
	public String toString() {
		String p="";
		p+="ID: "+id+" ";
		p+="Name: "+nom+" ";
		p+="House: "+batiment+" ";
		p+="Where: "+lieu+" ";
		p+="How: "+capacite+" ";
		return p;
	}
	/* ******************** Getter et Setter *********************** */
}
