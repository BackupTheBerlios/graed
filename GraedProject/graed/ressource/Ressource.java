
package graed.ressource;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Ressource {
	private String type;
	
	protected Ressource( String type ) {
		this.type = type;
	}
	
	public String getRessourceType() {
		return type;
	}
	
}
