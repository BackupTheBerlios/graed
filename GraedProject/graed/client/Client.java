/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.client;

import graed.callback.Callback;
import graed.callback.CallbackImpl;
import graed.callback.CallbackThread;
import graed.indisponibilite.IndisponibiliteManager;
import graed.ressource.RessourceManager;
import graed.ressource.type.TeacherInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
	
	private static final String host="pccop2b104-11.univ-mlv.fr";
	
	static {
		try {
			//Récupération du registry de la machine dusk sur le port 6666
			Registry rg = LocateRegistry.getRegistry(host,6666);
			//Récupération de la référence du serveur distant
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
		CallbackThread t = new CallbackThread();
		
		t.start();
		
		IndisponibiliteManager im = Client.getIndisponibiliteManager();
		TeacherInterface ti = (TeacherInterface)Client.getRessourceManager().createRessource("Professeur");
		ti.setName("Forax");
		ti.setFirstName("Rémi");
		ti.setEmail("forax@univ-mlv.fr");
		Client.getRessourceManager().addRessource(ti);
		
		Collection c = Client.getRessourceManager().getRessources(ti);
		
		System.out.println(c);
		TeacherInterface tic = (TeacherInterface)c.iterator().next();
		rm.deleteRessource(tic);
		
		t.stopThread();
		System.exit(0);
	}
}
