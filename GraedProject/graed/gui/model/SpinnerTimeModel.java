/*
 * Created on 3 mars 2005
 */
package graed.gui.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SpinnerDateModel;

/**
 * @author Helder DE SOUSA
 */
public class SpinnerTimeModel extends SpinnerDateModel {
	private Date min;
	private Date max;
	private long hourIncrementValue;
	private long minuteIncrementValue;
	
	public SpinnerTimeModel(Date begin, Date min, Date max ) {
		super(begin,new Date(min.getTime()-1000*60),new Date(max.getTime()+1000*60),Calendar.HOUR_OF_DAY );
		this.max = new Date(max.getTime()+1000*60);
		this.min = new Date(min.getTime()-1000*60);
		hourIncrementValue=60*60*1000;
		minuteIncrementValue=15*60*1000;
	}
	
	public SpinnerTimeModel(Date begin, Date min, Date max, long hourIncrement, long minuteIncrement ) {
		super(begin,new Date(min.getTime()-1000*60),new Date(max.getTime()+1000*60),Calendar.HOUR_OF_DAY );
		this.max = new Date(max.getTime()+1000*60);
		this.min = new Date(min.getTime()-1000*60);
		hourIncrementValue=hourIncrement;
		minuteIncrementValue=minuteIncrement;
	}
	
	public Comparable getStart() {
		return min;
	}
	
	public Comparable getEnd() {
		return max;
	}
	
	public Object getPreviousValue() {
		Date value = (Date)getValue();
		if( getCalendarField() == Calendar.HOUR_OF_DAY ) {
			Date test = new Date(value.getTime()-hourIncrementValue);
			if( test.after((Date)this.getStart()) ) value.setTime(value.getTime()-hourIncrementValue);
			return value;
		}
		if(getCalendarField() == Calendar.MINUTE ) {
			Date test = new Date(value.getTime()-minuteIncrementValue);
			if( test.after((Date)this.getStart()) ) value.setTime(value.getTime()-minuteIncrementValue);
			return value;
		}
		return value;
	}
	
	public Object getNextValue() {
		Date value = (Date)getValue();
		if( getCalendarField() == Calendar.HOUR_OF_DAY ) {
			Date test = new Date(value.getTime()+hourIncrementValue);
			if( test.before((Date)this.getEnd()) ) value.setTime(value.getTime()+hourIncrementValue);
			return value;
		}
		if(getCalendarField() == Calendar.MINUTE ) {
			Date test = new Date(value.getTime()+minuteIncrementValue);
			if( test.before((Date)this.getEnd()) ) value.setTime(value.getTime()+minuteIncrementValue);
			return value;
		}
		return value;
	}
	
	
	
	/**
	 * @return Returns the hourIncrementValue.
	 */
	public long getHourIncrementValue() {
		return hourIncrementValue;
	}
	/**
	 * @param hourIncrementValue The hourIncrementValue to set.
	 */
	public void setHourIncrementValue(long hourIncrementValue) {
		this.hourIncrementValue = hourIncrementValue;
	}
	/**
	 * @return Returns the minuteIncrementValue.
	 */
	public long getMinuteIncrementValue() {
		return minuteIncrementValue;
	}
	/**
	 * @param minuteIncrementValue The minuteIncrementValue to set.
	 */
	public void setMinuteIncrementValue(long minuteIncrementValue) {
		this.minuteIncrementValue = minuteIncrementValue;
	}
	public Time getSQLTime() {
		Calendar c = new GregorianCalendar();
		c.setTime((Date)getValue());
		return Time.valueOf(c.get(Calendar.HOUR_OF_DAY)+":"
				+c.get(Calendar.MINUTE)+":00");
	}
}
