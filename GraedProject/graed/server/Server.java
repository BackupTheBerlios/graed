/*
 * Created on 4 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.server;

import graed.indisponibilite.IndisponibiliteManager;
import graed.indisponibilite.IndisponibiliteManagerImpl;
import graed.ressource.RessourceManager;
import graed.ressource.RessourceManagerImpl;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Helder DE SOUSA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Server {
	private final static int portNumber = 6666;
	
	public static void main(String[] args) {
		try {
			//Execution dynamique du registre de nom rmi
			//On aurait pu le lancer manuellement (rmiregistry)
			Registry r = LocateRegistry.createRegistry(portNumber);

			//Facultatif, mais plus sûr dans le cas de chargement dynamique de classes
			//System.out.println("Mise en place du Security Manager ...");
			//System.setSecurityManager(new java.rmi.RMISecurityManager());
			
			//Creation des objets serveurs
			RessourceManager rm = RessourceManagerImpl.getInstance();
			IndisponibiliteManager im = IndisponibiliteManagerImpl.getInstance();
			
			//Enregistrement du serveur dans le domaine de nom rmi
			r.rebind("rmi://"+InetAddress.getLocalHost().getHostName()+":6666/RessourceManager", rm);
			r.rebind("rmi://"+InetAddress.getLocalHost().getHostName()+":6666/IndisponibiliteManager", im);
			
		    System.out.println("Serveur lancé : rmi://"+InetAddress.getLocalHost().getHostName()+":6666/RessourceManager");
		    System.out.println("Serveur lancé : rmi://"+InetAddress.getLocalHost().getHostName()+":6666/IndisponibiliteManager");
		    
		} catch (Exception e) {
			System.out.println("Exception capturée: ");
			e.printStackTrace();
		}
		
	}
}
