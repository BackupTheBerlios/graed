/*
 * Mod�le des colonnes permettant de modifier le nombre de colonnes
 */
package graed.gui.timetable;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * @author GONORD Nad�ge
 *	test�:
 *	S�lection des colonnes
 *	Nombre de colonnes modifiable
 * 
 */
public class TimetableColumnModel extends DefaultTableColumnModel {
	private TimetableDefaultTableModel tm;
	/**
	 * Constructeur
	 * @param tm mod�le de donn�es
	 */
	public TimetableColumnModel(TimetableDefaultTableModel tm){
		super();
		this.tm=tm;		
		this.setColumnSelectionAllowed(true);
	}
	/**
	 * Renvoie le nombre de colonnes
	 */
	public int getColumnCount() {
		return tm.getColumnCount();
	}
	
	/**
	 * Renvoie la colonne
	 * (M�thode n�c�ssaire)
	 */
	public TableColumn getColumn(int arg0) {
		if (arg0<tm.getColumnCount())
			return super.getColumn(arg0);
		return null;
	}

	/**
	 * Ajout d'une colonne
	 * (M�thode n�c�ssaire)
	 */
	public void addColumn(TableColumn aColumn) {
		super.addColumn(aColumn);
	}
}
