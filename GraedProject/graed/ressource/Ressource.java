
package graed.ressource;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Helder DE SOUSA
 */
public class Ressource extends UnicastRemoteObject{
	private String id_ressource;
	private String type;
	
	
	public Ressource() {
	}
	
	public Ressource( String type ) {
		this();
		this.type = type;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * @return Returns the id_ressource.
	 */
	public String getId_ressource() {
		return id_ressource;
	}
	/**
	 * @param id_ressource The id_ressource to set.
	 */
	public void setId_ressource(String id_ressource) {
		this.id_ressource = id_ressource;
	}

}
