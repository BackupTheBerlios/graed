/*
 * Created on 8 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.callback;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface Callback extends Remote {
	public static final int UPDATE = 0;
	public static final int DELETE = 1;
	public static final int ADD = 2;
	
	public void notify( Object o, int cause )  throws RemoteException ;
}
