/*
 * Created on 25 févr. 2005
 */
package graed.indisponibilite.event;

import java.util.EventListener;

/**
 * @author Helder DE SOUSA
 */
public interface IndisponibiliteListener extends EventListener {
    public void indisponibiliteDeleted( IndisponibiliteEvent ie );
    public void indisponibiliteUpdated( IndisponibiliteEvent ie );
}
