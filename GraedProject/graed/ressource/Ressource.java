
package graed.ressource;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Ressource {
	private String id_ressource;
	private String type;
	
	public Ressource() {
		type="none";
	}
	
	public Ressource( String type ) {
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
