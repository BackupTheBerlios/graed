/*
 * Created on 25 févr. 2005
 */
package graed.ressource.event;

import graed.ressource.Ressource;

import java.util.EventObject;

/**
 * @author Helder DE SOUSA
 */
public class RessourceEvent extends EventObject {
    /**
     * @param r The source of this event
     */
    public RessourceEvent(Ressource r) {
        super(r);
    }
}
