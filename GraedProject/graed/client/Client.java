/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.client;

import graed.callback.CallbackRunnable;
import graed.callback.CallbackThread;
import graed.indisponibilite.IndisponibiliteManager;
import graed.ressource.RessourceInterface;
import graed.ressource.RessourceManager;
import graed.ressource.type.TeacherInterface;

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
	private static final String host="pccop2b104-15.univ-mlv.fr";
	
	static {
		try {
			//R�cup�ration du registry de la machine dusk sur le port 6666
			Registry rg = LocateRegistry.getRegistry(host,6666);
			//R�cup�ration de la r�f�rence du serveur distant
			Remote r = rg.lookup("rmi://"+host+":6666/RessourceManager");
			Remote i = rg.lookup("rmi://"+host+":6666/IndisponibiliteManager");
			rm = (RessourceManager)r;
			im = (IndisponibiliteManager)i;
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
		ti.setFirstName("R�mi");
		ti.setEmail("forax@univ-mlv.fr");
		Client.getRessourceManager().addRessource(ti);
		
		Collection c = Client.getRessourceManager().getRessources(ti);
		
		TeacherInterface tic = (TeacherInterface)c.iterator().next();
		rm.deleteRessource(tic);
		
		t.stopThread();
	}
}
