package graed.ressource.type;

import java.rmi.RemoteException;

import graed.ressource.Ressource;

/**
 * @author Propriétaire
 */
public class Materiel extends Ressource {
	 private String name;
     private String typeMateriel;

     public Materiel() throws RemoteException{
             super("Materiel");
     }
     public Materiel(String name,String typeMateriel) throws RemoteException{
             this();
             this.name=name;
             this.typeMateriel=typeMateriel;
     }
     /* ******************** Getter et Setter *********************** */
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
		p+=name+" ";
		p+="("+typeMateriel+")";
		return p;
	}


}
