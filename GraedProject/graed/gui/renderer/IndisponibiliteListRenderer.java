/*
 * Created on 7 mars 2005
 */
package graed.gui.renderer;


import graed.indisponibilite.IndisponibiliteInterface;

import java.awt.Component;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteListRenderer extends JLabel implements ListCellRenderer {
	public IndisponibiliteListRenderer() {
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
				setText(((IndisponibiliteInterface)value).print());
			} catch (RemoteException e) {
				e.printStackTrace();
				setText("Erreur !!");
			}
	         setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
	         setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
	         return this;
	     }
 }