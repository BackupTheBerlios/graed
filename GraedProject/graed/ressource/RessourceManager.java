
package graed.ressource;

import java.util.List;

public class RessourceManager {
	/**
	 * Ajoute une ressource sur le syst�me de donn�es
	 * @param r
	 */
	 public void addRessource( Ressource r ){
	 }
	/**
	 * Supprime une ressource sur le syst�me de donn�es
	 * @param r
	 */
	 public void deleteRessource( Ressource r ){
	 }
	/**
	 * Modifie une ressource sur le syst�me de donn�es
	 * @param r
	 */
	 public void updateRessource( Ressource r ){
	 }
	/**
	 * Retourne un ensemble de ressources
	 * @param r
	 * @param constraint
	 * @return
	 */ 
	 public Ressource[] getRessources( String type, List constraints ) {
	 	return null;
	 }
	 /**
	  * Retourne un ensemble de ressources
	  * @param type
	  * @return
	  */
	 public Ressource[] getRessourcesByType( String type ){
	 	return null;
	 }
	/**
	 * Retourne la liste des diff�rents types de ressources par introspection
	 * @return
	 */
	 public String[] getRessourcesTypes() {
	 	return null;
	 }
	 
	 public void registerForNotification( Object o ) {
	 	
	 }
	 
	 protected void fireRessourceUpdated() {
	 }
	 
	 protected void fireRessourceDeleted() {
	 }
}
