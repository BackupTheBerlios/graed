/*
 * Created on 24 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.indisponibilite;

import graed.ressource.RessourceInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * Classe représentant une indisponibilite.
 * @author Helder DE SOUSA
 */
public class Indisponibilite extends UnicastRemoteObject implements IndisponibiliteInterface{
	/**
	 * L'identifiant de l'indisponibilité.
	 */
	private String id;
	/**
	 * L'ensemble des ressources liées à cette indisponibilité.
	 */
	private Set ressources;
	/**
	 * Date de début de l'indisponibilité.
	 */
	private Date debut;
	/**
	 * Date de fin de l'indisponibilité.
	 */
	private Date fin;
	/**
	 * Durée de l'indisponibilité.
	 */
	private int duree;
	/**
	 * Fréquence de l'indisponibilité.
	 */
	private String periodicite;
	/**
	 * Libelle de l'indisponibilité.
	 */
	private String libelle;
	private String type;
	private Time hdebut;
	
	public Indisponibilite(Date debut, Date fin, Time hdebut, int duree, String periodicite,
			String libelle, String type) throws RemoteException {
		this();
		this.debut = debut;
		this.fin = fin;
		this.duree = duree;
		this.periodicite = periodicite;
		this.libelle = libelle;
		this.type = type;
		this.hdebut = hdebut;
	}
	
	public Indisponibilite() throws RemoteException{
		ressources = new HashSet();
	}
	
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the ressources.
	 */
	public Set getRessources() {
		return ressources;
	}
	/**
	 * @param ressources The ressources to set.
	 */
	public void setRessources(Set ressources) {
		this.ressources = ressources;
	}
	
	public void addRessource( RessourceInterface r ) {
		ressources.add(r);
	}
	
	
	/**
	 * @return Returns the debut.
	 */
	public Date getDebut() {
		return debut;
	}
	/**
	 * @param debut The debut to set.
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	/**
	 * @return Returns the duree.
	 */
	public int getDuree() {
		return duree;
	}
	/**
	 * @param duree The duree to set.
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}
	/**
	 * @return Returns the fin.
	 */
	public Date getFin() {
		return fin;
	}
	/**
	 * @param fin The fin to set.
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}
	/**
	 * @return Returns the libelle.
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle The libelle to set.
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	/**
	 * @return Returns the periodicite.
	 */
	public String getPeriodicite() {
		return periodicite;
	}
	/**
	 * @param periodicite The periodicite to set.
	 */
	public void setPeriodicite(String periodicite) {
		this.periodicite = periodicite;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * @return Returns the hdebut.
	 */
	public Time getHdebut() {
		return hdebut;
	}
	/**
	 * @param hdebut The hdebut to set.
	 */
	public void setHdebut(Time hdebut) {
		this.hdebut = hdebut;
	}
	/**
	 * Affiche l'objet
	 */
	public String print() throws RemoteException{
		String libelle=getLibelle();
		if(getPeriodicite().equals("ponctuel")){
			libelle+=" le "+getDebut();
		}
		else if(getPeriodicite().equals("hebdomadaire")){
			libelle+=" du "+getDebut()+" au "+getFin();
		}
		else if(getPeriodicite().equals("bihebdomadaire")){
			libelle+=" tous les 15 jours à partir du "+getDebut();
		}
		for(Iterator i=ressources.iterator();i.hasNext();)
			libelle+=" \n"+((RessourceInterface)i.next()).print();
		libelle+=" \nde "+getHdebut()+" à "+new Time(getHdebut().getTime()+1000*60*getDuree());
		return libelle;
	}	
}
