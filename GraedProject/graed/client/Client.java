/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.client;

import graed.callback.CallbackRunnable;
import graed.callback.CallbackThread;
import graed.conf.Configuration;
import graed.indisponibilite.IndisponibiliteManager;
import graed.ressource.RessourceInterface;
import graed.ressource.RessourceManager;
import graed.ressource.type.TeacherInterface;
import graed.user.UserManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;

/**
 * @author hdesou01
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Client {
	private static final RessourceManager rm;
	private static final IndisponibiliteManager im;
	private static final String host="pccop2b104-12.univ-mlv.fr";
	private static final UserManager um;
	static {
		try {
			String host = Configuration.getParamValue("rmi","host");
			String port = Configuration.getParamValue("rmi","port");
			//Récupération du registry de la machine dusk sur le port
			Registry rg = LocateRegistry.getRegistry(host,Integer.parseInt(port));
			//Récupération de la référence du serveur distant
			Remote r = rg.lookup("rmi://"+host+":"+port+"/RessourceManager");
			Remote i = rg.lookup("rmi://"+host+":"+port+"/IndisponibiliteManager");
			Remote u = rg.lookup("rmi://"+host+":"+port+"/UserManager");
			rm = (RessourceManager)r;
			im = (IndisponibiliteManager)i;
			um = (UserManager)u;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erreur de connexion RMI : " + e.getMessage(), e);
		}
	}
	
	public static RessourceManager getRessourceManager() {
		return rm;
	}
	
	public static IndisponibiliteManager getIndisponibiliteManager() {
		return im;
	}
	
	public static UserManager getUserManager() {
		return um;
	}
	
	public static void main(String[] args) throws RemoteException {
		Runnable add = new CallbackRunnable(){ 
			public void run() {
			try {
				System.out.println("Added : "+((RessourceInterface)(getSource())).print());
			} catch (RemoteException e) {
				e.printStackTrace();
			}			
		} };
		
		Runnable update = new CallbackRunnable(){ 
			public void run() {
			try {
				System.out.println("Updated : "+((RessourceInterface)(getSource())).print());
			} catch (RemoteException e) {
				e.printStackTrace();
			}			
		} };
		
		Runnable delete = new CallbackRunnable(){ 
			public void run() {
			try {
				System.out.println("Deleted : "+((RessourceInterface)(getSource())).print());
			} catch (RemoteException e) {
				e.printStackTrace();
			}			
		} };
		
		CallbackThread t = new CallbackThread(add,delete,update, Client.getRessourceManager());
		
		t.start();
		
		IndisponibiliteManager im = Client.getIndisponibiliteManager();
		TeacherInterface ti = (TeacherInterface)Client.getRessourceManager().createRessource("Professeur");
		ti.setName("Forax");
		ti.setFirstName("Rémi");
		ti.setEmail("forax@univ-mlv.fr");
		Client.getRessourceManager().addRessource(ti);
		
		Collection c = Client.getRessourceManager().getRessources(ti);
		
		TeacherInterface tic = (TeacherInterface)c.iterator().next();
		rm.deleteRessource(tic);
		
		t.stopThread();
	}
}
