package graed.db;

import java.util.List;


/**
 * Classe s'occupant de la gestion des objets dans la BD
 */
public class DataBaseManager{
	protected String databaseName;
	
	public DataBaseManager( String databaseName ) {
		this.databaseName = databaseName;
	}
	/**
	 * Correspond à la requête insert dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 */
	public void add(Object dbo){
	}
	/**
	 * Correspond à la requête delete dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 */
	public void delete(Object dbo){
	}
	/**
	 * Correspond à la requête update dans la BD
	 * @param dbo Objet contenant la liste des paramètres et le nom de la table
	 */
	public void update( Object dbo ) {
	}
	/**
	 * Correspond à la requête select dans la BD
	 * @param dbo
	 * @param where
	 * @return liste des objets correspondant à la requête de sélection
	 */
	public Object[] get( Object dbo, List where ) {
		return null;
	}
}
