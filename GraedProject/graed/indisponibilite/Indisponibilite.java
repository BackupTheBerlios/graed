/*
 * Created on 24 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.indisponibilite;

import graed.ressource.Ressource;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;


/**
 * Classe repr�sentant une indisponibilite.
 * @author Helder DE SOUSA
 */
public class Indisponibilite {
	/**
	 * L'identifiant de l'indisponibilit�.
	 */
	private String id;
	/**
	 * L'ensemble des ressources li�es � cette indisponibilit�.
	 */
	private Set ressources;
	/**
	 * Date de d�but de l'indisponibilit�.
	 */
	private Date debut;
	/**
	 * Date de fin de l'indisponibilit�.
	 */
	private Date fin;
	/**
	 * Dur�e de l'indisponibilit�.
	 */
	private int duree;
	/**
	 * Fr�quence de l'indisponibilit�.
	 */
	private String periodicite;
	/**
	 * Libelle de l'indisponibilit�.
	 */
	private String libelle;
	private String type;
	private Time hdebut;
	
	public Indisponibilite(Date debut, Date fin, Time hdebut, int duree, String periodicite,
			String libelle, String type) {
		this();
		this.debut = debut;
		this.fin = fin;
		this.duree = duree;
		this.periodicite = periodicite;
		this.libelle = libelle;
		this.type = type;
		this.hdebut = hdebut;
	}
	
	public Indisponibilite() {
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
	
	public void addRessource( Ressource r ) {
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
}
