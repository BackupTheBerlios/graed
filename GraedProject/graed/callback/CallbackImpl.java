/*
 * Created on 8 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.callback;

import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallbackImpl implements Callback{
	private Object o;
	private int cause;
	
	public CallbackImpl() {
		o=null;
		cause = -1;
	}
	
	public void notify(Object o, int cause) throws RemoteException {
		System.out.println("Notified");
		this.o = o;
		this.cause = cause;
		//notify();
	}

	/* (non-Javadoc)
	 * @see graed.callback.Callback#getSource()
	 */
	public Object getSource() throws RemoteException {
		return o;
	}

	/* (non-Javadoc)
	 * @see graed.callback.Callback#getCause()
	 */
	public int getCause() throws RemoteException {
		return cause;
	}

	/* (non-Javadoc)
	 * @see graed.callback.Callback#init()
	 */
	public void init() throws RemoteException {
		o=null;
		cause = -1;
		
	}
	
}
