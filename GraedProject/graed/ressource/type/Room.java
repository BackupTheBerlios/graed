/*
 * Created on 11 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package graed.ressource.type;

import graed.ressource.Ressource;

import java.rmi.RemoteException;

/**
 * @author Propri�taire
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Room extends Ressource implements RoomInterface{
	private String nom;
	private String batiment;
	private String lieu;
	private int capacite;
	
	public Room() throws RemoteException{
		super("Salle");
	}
	
	public Room(String nom, String lieu,String batiment, int capacite) throws RemoteException{
		this();
		this.nom=nom;
		this.batiment=batiment;
		this.lieu=lieu;
		this.capacite=capacite;
	}
	
	public String print() {
		String p="";
		p+=nom+" ";
		p+="("+batiment+")";
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
	/**
	 * Controle les donn�es
	 */
	public String control() throws RemoteException{
		if(nom==null || nom.equals(""))return "Veuillez renseigner le nom de la salle";
		if(lieu==null || lieu.equals(""))return "Veuillez renseigner le lieu de la salle";
		if(batiment==null || batiment.equals(""))return "Veuillez renseigner le batiment de la salle";
		return null;
	}
}
