/*
 * Created on 7 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.renderer;

import java.awt.Color;
import java.awt.Component;

import graed.ressource.RessourceInterface;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RessourceListRenderer extends JLabel implements ListCellRenderer {
	public RessourceListRenderer() {
	         setOpaque(true);
	}
	
	public Component getListCellRendererComponent(
		JList list,
		Object value,
		int index,
		boolean isSelected,
	    boolean cellHasFocus)
	   	{
	         setText(((RessourceInterface)value).print());
	         setBackground(isSelected ? Color.red : Color.white);
	         setForeground(isSelected ? Color.white : Color.black);
	         return this;
	     }
 }

