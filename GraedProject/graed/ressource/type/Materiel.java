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
public class Materiel extends Ressource {
	public Materiel(){
		super("Materiel");
	}
	public Materiel(String nom,String type){
		this();
	}
	public Materiel(int id,String nom,String type){
		this(nom,type);
	}
}
