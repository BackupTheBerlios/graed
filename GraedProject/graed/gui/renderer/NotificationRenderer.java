/*
 * Created on 21 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.renderer;

import java.awt.Color;
import java.awt.Component;

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
			this.setOpaque(true);
			String s = (String)value;
			
				if( s.indexOf("ajout�") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/New16.gif")));
					this.setBackground(new Color(0x9dc97f));
				}
				else if( s.indexOf("supprim�") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/Delete16.gif")));
					this.setBackground(new Color(0xc97f7f));
				}
				else if( s.indexOf("� jour") >=0 ) {
					this.setIcon(new ImageIcon(getClass().getResource("../timetable/icons/general/Refresh16.gif")));
					this.setBackground(new Color(0xf8ff8b));
				}
			
			this.setText(s);
			return this;
		} else {
			return null;
		}
	}

}
