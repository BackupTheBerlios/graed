/*
 * Created on 24 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.indisponibilite;

import java.util.Set;


/**
 * @author ngonord
 */
public class Indisponibilite {
	private String id;
	private Set ressources;
	
	public Indisponibilite() {
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
}
