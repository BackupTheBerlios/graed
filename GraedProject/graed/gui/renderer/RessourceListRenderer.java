/*
 * Created on 7 mars 2005
 */
package graed.gui.renderer;

import java.awt.Component;
import java.rmi.RemoteException;

import graed.ressource.RessourceInterface;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Helder DE SOUSA
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
	         try {
	         	if( value!=null && value instanceof RessourceInterface)
	         	setText(((RessourceInterface)value).print());
	         	else setText("");
			} catch (RemoteException e) {
				e.printStackTrace();
				setText("Erreur !!");
			}
	         setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
	         setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
	         return this;
	     }
 }

