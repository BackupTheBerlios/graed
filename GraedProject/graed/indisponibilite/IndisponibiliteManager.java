
package graed.indisponibilite;

import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.RessourceInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.Collection;
import java.sql.Date;


/**
 * @author Helder DE SOUSA
 * Interface définissant le fonctionnement d'un gestionnaire d'indisponibilité.
 */
public interface IndisponibiliteManager extends Remote{
    /**
     * Ajoute une indisponibilite.
     * @param r L'indisponibilité à ajouter.
     * @throws RemoteException
     */
    public void addIndisponibilite( IndisponibiliteInterface i ) throws RemoteException;
    /**
     * Supprime une indisponibilite.
     * @param i L'indisponibilite à supprimer.
     * @throws RemoteException
     */
    public void deleteIndisponibilite( IndisponibiliteInterface i ) throws RemoteException;
    /**
     * Met à jour une indisponibilité.
     * @param i L'indisponibilite à mettre à jour.
     * @throws RemoteException
     */
    public void updateIndiponibilite( IndisponibiliteInterface i ) throws RemoteException;
    /**
     * Récupère les indiponibilité en utilisant une indisponibilité comme example.
     * @param i L'indisponibilite servant d'example.
     * @return Une collection contenant les indisponibilités.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( IndisponibiliteInterface i ) throws RemoteException;
    /**
     * Récupère les indisponibilités d'une ressource.
     * @param r La ressource commune aux indisponibilité.
     * @return Une collection contenant les indisponibilités.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( RessourceInterface r ) throws RemoteException;
    /**
     * Récupère les indisponibilité entre deux dates.
     * @param begin La date de début.
     * @param end La date de fin.
     * @return Une collection contenant les indisponibilités.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( Date begin, Date end ) throws RemoteException;
    /**
     * Récupère les indisponibilité entre deux dates pour une ressource donnée.
     * @param r La ressource commune aux indisponibilité.
     * @param begin La date de début.
     * @param end La date de fin.
     * @return Une collection contenant les indisponibilités.
     * @throws RemoteException
     */
	public Collection getIndisponibilites( RessourceInterface r, Date begin, Date end ) throws RemoteException;
	
	/**
	 * Permet aux listener de s'enregister pour être avertis des modifications.
     * @param il Le listener voulant s'enregister.
     * @throws RemoteException
     */
    public void registerForNotification( IndisponibiliteListener il ) throws RemoteException;
    public IndisponibiliteInterface createIndisponibilite(Date debut, Date fin, Time hdebut, int duree, String periodicite,
			String libelle, String type) throws RemoteException;
    public IndisponibiliteInterface createIndisponibilite() throws RemoteException;
}
