/*
 * Redéfini l'affichage des cellules et la sélection
 */
package graed.gui.timetable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
	 * Redéfinit la couleur de sélection et l'affichage d'une cellule
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c=(Component) table.getValueAt(row, column);
		
		if (c!=null && (isSelected || hasFocus)){			
			c.setBackground(table.getSelectionBackground());
		}
		else if (c!=null){
			c.setBackground(Color.WHITE);
		}
		if(c==null){			
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
		JTextArea jta = (JTextArea) table.getValueAt(row, column);
		jta.setBorder(BorderFactory.createEtchedBorder(new Color(0xb0f0c1).darker(), new Color(0xb0f0c1).darker().darker()));
		return c;
	}

}
