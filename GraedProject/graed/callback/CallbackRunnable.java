/*
 * Created on 19 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.callback;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class CallbackRunnable implements Runnable {
	Object s = null;
	public abstract void run();
	public Object getSource() { return s; }
	public void setSource( Object s ) { this.s = s; }
}
