/*
 * Created on 8 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.callback;

import graed.client.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CallbackThread extends Thread {
	private Callback ca;
	private boolean run;
	/*private Runnable add;
	private Runnable delete;
	private Runnable update;*/
	
	public CallbackThread( /*Runnable add, Runnable delete, Runnable update*/ ) {
		init();
	}
	
	public void run() {
		while(run) {
			try {
				while( ca.getCause() == -1 )
					if( run == false ) return ;
				System.out.println("Sync : "+ca.getSource()+" "+ca.getCause());
				ca.init();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void init() {
		try {
			run = true;
			ca = new CallbackImpl();
			UnicastRemoteObject.exportObject(ca);
			Client.getRessourceManager().registerForNotification(ca);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
	public void stopThread() {
		try {
			Client.getRessourceManager().unregister(ca);
			run = false;
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
}
