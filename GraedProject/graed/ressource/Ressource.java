/*
 * Created on 7 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package graed.ressource;

/**
 * @author Propriétaire
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class Ressource {
	private String type;
	public Ressource(String type) {
		this.type=type;
	}
	public String getRessourceType(){
		return type;
	}
}
