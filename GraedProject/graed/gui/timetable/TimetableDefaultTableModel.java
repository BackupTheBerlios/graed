/*
 * Stocke la liste des cours � afficher dans les tables
 */
package graed.gui.timetable;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.table.DefaultTableModel;


/**
 * @author Nad�ge GONORD
 *
 * 
 */
public class TimetableDefaultTableModel extends DefaultTableModel {
	private int nbcells;
	private int nbhours;
	private String tableName;
	private Hashtable value;
	private final ArrayList listenerTab;

	/**
	 * Constructeur
	 * @param nbhours taille de la table nbre d'heure = 4 colonnes
	 * @param name jour associ�
	 */
	public TimetableDefaultTableModel(int nbhours,String tableName){
		nbcells=4*nbhours;
		this.nbhours=nbhours;
		this.tableName=tableName;
		value = new Hashtable();
		listenerTab=new ArrayList();
		
	}
	/**
	 * Vide l'emploi du temps
	 *
	 */
	public void clear(){
		nbcells=4*nbhours;
		value.clear();
	}
	/**
	 * R�cup�re le jour associ� � la table
	 * @return
	 */
	public String getName() {
		return tableName;
	}
	/**
	 * Une seule ligne
	 */
	public int getRowCount() {
		return 1;
	}

	/**
	 * Renvoie le nombre total de colonnes de la table
	 */
	public int getColumnCount() {
		return nbcells;
	}

	/**
	 * Non utilis�
	 */
	public String getColumnName(int arg0) {
		return tableName;
	}

	/**
	 * Renvoie le type des colonnes = Component
	 */
	public Class getColumnClass(int arg0) {
		return Component.class;
	}

	/**
	 * Les cellules contenant des objets sont �ditables
	 */
	public boolean isCellEditable(int row, int col) {
		if(value.get(new Integer(col))!=null)
				return true;
		return false;
	}

	/**
	 * Renvoie l'�l�ment � la colonne donn�es
	 */
	public Object getValueAt(int row, int col) {
		Object o =value.get(new Integer(col));
		
		if (o!=null)return ((MyCell)o).getValue();
		return null;
	}
	/**
	 * Renvoie l'�l�ment � la colonne donn�es
	 */
	public void modifyCellSize(int col,int size) {
		Object o =value.get(new Integer(col));	
		if (o!=null){
			int old=((MyCell)o).getSize();
			nbcells-=(size-old);
			((MyCell)o).setSize(size);
		}
	}
	/**
	 * R�cup�re la taille en nombre de colonnes de la cellule
	 * @param row
	 * @param col
	 * @return
	 */
	public int getCellSize(int row, int col) {
		return ((MyCell)value.get(new Integer(col))).getSize();
	}
	
	/**
	 * Supprime la valeur contenue dans la cellule
	 * @param col
	 * @param nbcols taille en nombre de colonne de l'objet
	 * @return
	 */
	public Object removeValueAt(int col, int nbcells) {
		this.nbcells+=(nbcells-1);
		return value.remove(new Integer(col));
	}

	/**
	 * Ins�re une nouvelle valeur dans la table 
	 * @param o JLabel a ins�rer
	 * @param col colonne ou l'on doit ins�rer l'objet
	 * @param nbcols taille de l'objet en nombre de colonnes
	 */
	public void setValueAt(Object o, int col, int nbcells) {
		value.put(new Integer(col),new MyCell(o,nbcells));
		this.nbcells-=(nbcells-1);
	}

	/**
	 * 
	 * @author Nad�ge GONORD
	 *
	 * Objet temporaire permettant de stocker � la fois la valeur et sa taille
	 */
	class MyCell{
		private Object value;
		private int size;
		/**
		 * Constructeur
		 * @param value
		 * @param size
		 */
		public MyCell(Object value,int size){
			this.value=value;
			this.size=size;
		}
		
		/**
		 * Renvoie la valeur
		 * @return
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Renvoie la taille
		 * @return
		 */
		public int getSize() {
			return size;
		}

		/**
		 * Red�finie la valeur
		 * @param object
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Red�fini la taille
		 * @param i
		 */
		public void setSize(int i) {
			size = i;
		}

	}
	
}
