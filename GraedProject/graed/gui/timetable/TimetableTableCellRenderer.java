/*
 * Red�fini l'affichage des cellules et la s�lection
 */
package graed.gui.timetable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TimetableTableCellRenderer extends DefaultTableCellRenderer {
	/**
	 * Constructeur vide
	 */
	public TimetableTableCellRenderer(){
		
	}
	/**
	 * Red�finit la couleur de s�lection et l'affichage d'une cellule
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c=(Component) table.getValueAt(row, column);
		if (c!=null && isSelected){			
			c.setBackground(table.getSelectionBackground());
		}
		else if (c!=null){
			c.setBackground(Color.WHITE);
		}
		if(c==null)
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		return c;
	}

}
