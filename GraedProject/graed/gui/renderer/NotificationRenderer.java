/*
 * Created on 21 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
 

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotificationRenderer extends JLabel implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if( value instanceof String ) {
			String s = (String)value;
			this.setOpaque(true);
				if( s.indexOf("ajouté") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/New16.gif")));
					this.setBackground(new Color(0xb0f0c1));
				}
				else if( s.indexOf("supprimé") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/Delete16.gif")));
					this.setBackground(new Color(0xf0b0b0));
				}
				else if( s.indexOf("à jour") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/Refresh16.gif")));
					this.setBackground(new Color(0xf0e9b0));
				}
			
			this.setText(s);
			return this;
		} else {
			return null;
		}
	}
	

}
