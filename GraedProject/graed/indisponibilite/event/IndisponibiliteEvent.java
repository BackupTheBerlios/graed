/*
 * Created on 1 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package graed.indisponibilite.event;

import java.util.EventObject;

import graed.indisponibilite.Indisponibilite;

/**
 * @author Helder DE SOUSA
 */
public class IndisponibiliteEvent extends EventObject{
	public IndisponibiliteEvent( Indisponibilite i ) {
		super(i);
	}
}
