/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import java.security.Principal;
import java.io.Serializable;

/**
 * @author tom
 *
 */
public class GraedPrincipal implements Principal,Serializable{

    private String name;

    public GraedPrincipal() {
        name = "";
    }

    public GraedPrincipal(String newName) {
        name = newName;
    }

    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (this == o)
            return true;
 
        if (o instanceof GraedPrincipal) {
            if (((GraedPrincipal) o).getName().equals(name))
                return true;
            else
                return false;
        }
        else 
            return false;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

}
