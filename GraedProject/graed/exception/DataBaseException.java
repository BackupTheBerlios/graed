/*
 * Created on 23 févr. 2005
 */
package graed.exception;

/**
 * @author Helder DE SOUSA
 */
public class DataBaseException extends Exception {
    private String message;
    
    public DataBaseException( String message ) {
        this.message = message;
    }
    
    public String getMessage() {
        return message; 
    }
}
