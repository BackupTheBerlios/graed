/*
 * Created on 26 janv. 2005
 */
package graed.indisponibilite;

import java.util.List;

/**
 * @author Dusk93
 */
public class IndisponibiliteManager {
	/**
		 * Ajoute une ressource sur le système de données
		 * @param r
		 */
		 public void addIndisponibilite( Indisponibilite r ){
		 }
		/**
		 * Supprime une ressource sur le système de données
		 * @param r
		 */
		 public void deleteIndisponibilite( Indisponibilite r ){
		 }
		/**
		 * Modifie une ressource sur le système de données
		 * @param r
		 */
		 public void updateIndisponibilite( Indisponibilite r ){
		 }
		/**
		 * Retourne un ensemble de ressources
		 * @param r
		 * @param constraint
		 * @return
		 */ 
		 public List getIndisponibilites( String type, List constraints ) {
			return null;
		 }
		
	 
		 public void registerForNotification( Object o ) {
	 	
		 }
	 
		 protected void fireIndisponibiliteUpdated() {
		 }
	 
		 protected void fireIndisponibiliteDeleted() {
		 }
		 protected void fireIndisponibiliteCreated(){
		 }
}
