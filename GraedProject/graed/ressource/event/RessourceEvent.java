/*
 * Created on 25 f�vr. 2005
 */
package graed.ressource.event;

import graed.ressource.RessourceInterface;

import java.util.EventObject;

/**
 * @author Helder DE SOUSA
 */
public class RessourceEvent extends EventObject {
    /**
     * @param r The source of this event
     */
    public RessourceEvent(RessourceInterface r) {
        super(r);
    }
}
