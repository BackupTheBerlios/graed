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
	 * Correspond � la requ�te insert dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void add(Object dbo){
	}
	/**
	 * Correspond � la requ�te delete dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void delete(Object dbo){
	}
	/**
	 * Correspond � la requ�te update dans la BD
	 * @param dbo Objet contenant la liste des param�tres et le nom de la table
	 */
	public void update( Object dbo ) {
	}
	/**
	 * Correspond � la requ�te select dans la BD
	 * @param dbo
	 * @param where
	 * @return liste des objets correspondant � la requ�te de s�lection
	 */
	public Object[] get( Object dbo, List where ) {
		return null;
	}
}
