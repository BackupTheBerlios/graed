/*
 * Created on 21 mars 2005
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
public interface CallbackSender {
	public void registerForNotification( Callback c ) throws RemoteException;
    public void unregister( Callback c ) throws RemoteException;
}
