package graed.ressource.type;

import graed.ressource.Ressource;

/**
 * @author Propriétaire
 */
public class Materiel extends Ressource {
	 private String id;
     private String name;
     private String typeMateriel;

     public Materiel(){
             super("Materiel");
     }
     public Materiel(String name,String typeMateriel){
             this();
             this.name=name;
             this.typeMateriel=typeMateriel;
     }
     /* ******************** Getter et Setter *********************** */
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
      * @return Returns the name.
      */
     public String getName() {
             return name;
     }
     /**
      * @param name The name to set.
      */
     public void setName(String name) {
             this.name = name;
     }
     /**
      * @return Returns the type.
      */
     public String getTypeMateriel() {
             return typeMateriel;
     }
     /**
      * @param type The type to set.
      */
     public void setTypeMateriel(String typeMateriel) {
             this.typeMateriel = typeMateriel;
     }
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String p="";
		p+="Name: "+name+" ";
		p+="Type: "+typeMateriel;
		return p;
	}


}
