
package graed.indisponibilite;

import graed.indisponibilite.event.IndisponibiliteListener;
import graed.ressource.Ressource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;


/**
 * @author Helder DE SOUSA
 * Interface d�finissant le fonctionnement d'un gestionnaire d'indisponibilit�.
 */
public interface IndisponibiliteManager extends Remote{
    /**
     * Ajoute une indisponibilite.
     * @param r L'indisponibilit� � ajouter.
     * @throws RemoteException
     */
    public void addIndisponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * Supprime une indisponibilite.
     * @param i L'indisponibilite � supprimer.
     * @throws RemoteException
     */
    public void deleteIndisponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * Met � jour une indisponibilit�.
     * @param i L'indisponibilite � mettre � jour.
     * @throws RemoteException
     */
    public void updateIndiponibilite( Indisponibilite i ) throws RemoteException;
    /**
     * R�cup�re les indiponibilit� en utilisant une indisponibilit� comme example.
     * @param i L'indisponibilite servant d'example.
     * @return Une collection contenant les indisponibilit�s.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( Indisponibilite i ) throws RemoteException;
    /**
     * R�cup�re les indisponibilit�s d'une ressource.
     * @param r La ressource commune aux indisponibilit�.
     * @return Une collection contenant les indisponibilit�s.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( Ressource r ) throws RemoteException;
    /**
     * R�cup�re les indisponibilit� entre deux dates.
     * @param begin La date de d�but.
     * @param end La date de fin.
     * @return Une collection contenant les indisponibilit�s.
     * @throws RemoteException
     */
    public Collection getIndisponibilites( Date begin, Date end ) throws RemoteException;
    /**
     * R�cup�re les indisponibilit� entre deux dates pour une ressource donn�e.
     * @param r La ressource commune aux indisponibilit�.
     * @param begin La date de d�but.
     * @param end La date de fin.
     * @return Une collection contenant les indisponibilit�s.
     * @throws RemoteException
     */
	public Collection getIndisponibilites( Ressource r, Date begin, Date end ) throws RemoteException;
	/**
	 * Permet aux listener de s'enregister pour �tre avertis des modifications.
     * @param il Le listener voulant s'enregister.
     * @throws RemoteException
     */
    public void registerForNotification( IndisponibiliteListener il ) throws RemoteException;
}
