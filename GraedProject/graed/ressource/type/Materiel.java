package graed.ressource.type;

import graed.ressource.Ressource;

import java.rmi.RemoteException;

/**
 * @author Propriétaire
 */
public class Materiel extends Ressource implements MaterielInterface{
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
	public String print() {
		String p="";
		p+=name+" ";
		p+="("+typeMateriel+")";
		return p;
	}
	/**
	 * Controle les données
	 */
	public String control() {
		if(name==null || name.equals(""))return "Veuillez renseigner le nom du matériel";
		if(typeMateriel==null || typeMateriel.equals(""))return "Veuillez renseigner le type du matériel";
		return null;
	}

}
