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

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

/**
 * @author Helder DE SOUSA
 */
public class CallbackThread extends Thread {
	private Callback ca;
	private boolean run;
	private Hashtable runnables;
	private CallbackSender cs;
	
	public CallbackThread( Runnable add, Runnable delete, Runnable update, CallbackSender cs ) {
		this.cs = cs;
		init();
		runnables = new Hashtable();
		runnables.put( new Integer(Callback.ADD), add );
		runnables.put( new Integer(Callback.UPDATE), update );
		runnables.put( new Integer(Callback.DELETE), delete );
	}
	
	public void run() {
		while(run) {
			try {
				while( ca.getCause() == -1 )
					if( run == false ) return ;
				CallbackRunnable cr = (CallbackRunnable)runnables.get(new Integer(ca.getCause()));
				cr.setSource(ca.getSource());
				new Thread( cr ).start();
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
			cs.registerForNotification(ca);
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
