/*
 * Redéfinission du modèle de colonnes
 */
package graed.gui.timetable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;

/**
 * @author Nadège GONORD
 * Stocke les tables dont on redéfinit la sélection
 */
public class TimetableDefaultListSelectionModel extends DefaultListSelectionModel {
	private List l;
	public TimetableDefaultListSelectionModel(){
		super();
		l= new LinkedList();		
	}
	/**
	 * Ajout d'une table dont on redéfinit la sélection
	 * @param a
	 */
	public void addTable(TimetableColJTable a){
		l.add(a);
		System.out.println(a.getName());
	}
	/**
	 * Nettoyage des sélections antérieures
	 *
	 */
	public void clearAllTables(){
		for (Iterator i=l.iterator();i.hasNext();) {
			TimetableColJTable tmp=(TimetableColJTable)i.next();
				tmp.clearSelection();
		}
	}
	
}
