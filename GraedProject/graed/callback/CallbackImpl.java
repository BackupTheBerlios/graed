/*
 * Created on 8 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.callback;

import java.rmi.RemoteException;

/**
 * @author Helder DE SOUSA
 */
public class CallbackImpl implements Callback{
	private Object o;
	private int cause;
	
	public CallbackImpl() {
		o=null;
		cause = -1;
	}
	
	public CallbackImpl( Callback ca ) {
		try {
			o = ca.getSource();
			cause = ca.getCause();
		} catch (RemoteException re ) {
			o=null;
			cause = -1;
		}
	}
	
	public void notify(Object o, int cause) throws RemoteException {
		this.o = o;
		this.cause = cause;
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
