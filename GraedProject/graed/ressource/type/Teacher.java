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
public class Teacher extends Ressource {
	private int id;
	private String name;
	private String firstName;
	private boolean exterior;
	private String office;
	private String phone; 
	private String email;
	public Teacher(String name, String firstName, boolean exterior,
					String office, String phone, String email){
		super();
		this.id=0;
		this.name=name;
		this.firstName=firstName;
		this.exterior=exterior;
		this.office=office;
		this.phone=phone;
		this.email=email;		
	}
	public Teacher(int id,String name, String firstName, boolean exterieur,
						String office, String phone, String email){
			this(name,firstName,exterieur,office,phone,email);
			this.id=id;
		}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String p="";
		p+="Name: "+name+" ";
		p+="First name: "+firstName+" ";
		p+="Exterior: "+exterior+" ";
		p+="Office: "+office+" ";
		p+="Phone: "+phone+" ";
		p+="E-Mail: "+email;		
		return p;
	}
	/* ******************** Getter et Setter *********************** */
	
}
