/*
 * Created on 3 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.gui.model;

import java.util.Calendar;
import java.util.Date;

import javax.swing.SpinnerDateModel;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpinnerTimeModel extends SpinnerDateModel {
	private Date min;
	private Date max;
		
	public SpinnerTimeModel(Date begin, Date min, Date max ) {
		super(begin,min,new Date(max.getTime()+1000*60),Calendar.HOUR_OF_DAY );
		this.max = max;
		this.min = min;
	}
	
	public Comparable getStart() {
		System.out.println(super.getStart());
		return min;
	}
	
	public Comparable getEnd() {
		System.out.println(super.getEnd());
		return max;
	}
	
	public Object getPreviousValue() {
		Date value = (Date)getValue();
		if( getCalendarField() == Calendar.HOUR_OF_DAY ) {
			Date test = new Date(value.getTime()-1000*60*60);
			if( test.after((Date)this.getEnd()) ) value.setTime(value.getTime()-1000*60*60);
			return value;
		}
		if(getCalendarField() == Calendar.MINUTE ) {
			Date test = new Date(value.getTime()-1000*60*15);
			if( test.after((Date)this.getEnd()) ) value.setTime(value.getTime()-1000*60*15);
			return value;
		}
		return value;
	}
	
	public Object getNextValue() {
		Date value = (Date)getValue();
		if( getCalendarField() == Calendar.HOUR_OF_DAY ) {
			Date test = new Date(value.getTime()+1000*60*60);
			if( test.before((Date)this.getEnd()) ) value.setTime(value.getTime()+1000*60*60);
			return value;
		}
		if(getCalendarField() == Calendar.MINUTE ) {
			Date test = new Date(value.getTime()+1000*60*15);
			if( test.before((Date)this.getEnd()) ) value.setTime(value.getTime()+1000*60*15);
			return value;
		}
		return value;
	}
}
