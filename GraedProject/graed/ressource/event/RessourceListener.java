/*
 * Created on 25 févr. 2005
 */
package graed.ressource.event;

import java.util.EventListener;

/**
 * @author Helder DE SOUSA
 */
public interface RessourceListener extends EventListener {
    public void ressourceDeleted( RessourceEvent re );
    public void ressourceUpdated( RessourceEvent re );
}
