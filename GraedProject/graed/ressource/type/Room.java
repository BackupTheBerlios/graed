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
	private String nom;
	private String batiment;
	private String lieu;
	private int capacite;
	
	public Room(){
		super("Salle");
	}
	
	public Room(String nom, String lieu,String batiment, int capacite){
		this();
		this.nom=nom;
		this.batiment=batiment;
		this.lieu=lieu;
		this.capacite=capacite;
	}
	
	public String toString() {
		String p="";
		p+="Name: "+nom+" ";
		p+="House: "+batiment+" ";
		p+="Where: "+lieu+" ";
		p+="How: "+capacite+" ";
		return p;
	}
	/* ******************** Getter et Setter *********************** */
	
	/**
	 * @return Returns the batiment.
	 */
	public String getBatiment() {
		return batiment;
	}
	/**
	 * @param batiment The batiment to set.
	 */
	public void setBatiment(String batiment) {
		this.batiment = batiment;
	}
	/**
	 * @return Returns the capacite.
	 */
	public int getCapacite() {
		return capacite;
	}
	/**
	 * @param capacite The capacite to set.
	 */
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	/**
	 * @return Returns the lieu.
	 */
	public String getLieu() {
		return lieu;
	}
	/**
	 * @param lieu The lieu to set.
	 */
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	/**
	 * @return Returns the nom.
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom The nom to set.
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
}
