/*
 * Created on 3 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.factory;

import graed.gui.model.SpinnerTimeModel;

import java.util.Date;

import javax.swing.JSpinner;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpinnerFactory {
	public static JSpinner createTimeSpinner( Date begin, Date min, Date max ) {
		JSpinner spinner = new JSpinner(new SpinnerTimeModel(begin,min,max));
	    	    
	    spinner.setEditor(new JSpinner.DateEditor(spinner, "HH:mm"));
	    
	    return spinner;
	}
}
