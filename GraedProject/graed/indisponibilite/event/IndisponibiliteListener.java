/*
 * Created on 25 f�vr. 2005
 */
package graed.indisponibilite.event;

import java.util.EventListener;

/**
 * @author Helder DE SOUSA
 */
public interface IndisponibiliteListener extends EventListener {
    public void indiponibiliteDeleted( IndisponibiliteEvent ie );
    public void indisponibiliteUpdated( IndisponibiliteEvent ie );
}
