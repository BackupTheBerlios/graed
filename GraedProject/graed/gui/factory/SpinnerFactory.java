/*
 * Created on 3 mars 2005
 */
package graed.gui.factory;

import graed.gui.model.SpinnerTimeModel;

import java.util.Date;

import javax.swing.JSpinner;

/**
 * @author Helder DE SOUSA
 */
public class SpinnerFactory {
	public static JSpinner createTimeSpinner( Date begin, Date min, Date max ) {
		JSpinner spinner = new JSpinner(new SpinnerTimeModel(begin,min,max));
	    	    
	    spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
	    
	    return spinner;
	}
}
