/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import java.security.MessageDigest;
import java.util.*;

import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.LoginModule;



/**
 * @author tom
 *
 */
public class GraedLoginModule implements LoginModule {
    
    // objets r�cup�r�s initialement
    CallbackHandler callbackHandler;
    Subject subject;
    Map sharedState;
    Map options; // options pass�es via le fichier de conf
    
    // objets temporaires
    TreeSet tmpCredentials;
    TreeSet tmpPrincipals;
    
    // drapeau pour connaitre le status de l'authentification
    boolean success;

    /**
     * Cr�ation d'un module de login sp�cifique � l'application Graed
     * Il obtient les informations login/passwd via le SGBD de l'applicatio
     *
     */    
    public GraedLoginModule(){
        tmpCredentials = new TreeSet();
        tmpPrincipals = new TreeSet();
        success = false;

    }
    
    /**
     * Initialisation du LoginModule
     * 
     * @param subject le sujet � authentifier
     * @param callbackHandler le CallBackHandler permettant de dialoguer avec l'utilisateur
     * @param sharedState
     * @param options les options sp�cifi�es dans le fichier de conf 
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map sharedState, Map options){
        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.sharedState = sharedState;
        this.options = options;
    }
    
    /**
     * M�thode d'authetification de l'utilisateur
     * 
     * @return true toujours
     * @exception FailedLoginException si l'authentification �choue
     * @exception LoginException s'il est impossible de r�aliser l'authentification
     */
    public boolean login() throws LoginException {
        if(callbackHandler == null){
            throw new LoginException("CallbackHandler inexistant, impossible d'authentifier l'utilisateur");
        }
        
        try{
            // on cr�e les 2 Callback n�cessaire au CallbackHandler
            Callback[] callbacks = new Callback[] {
                    new NameCallback("Login"),
                    new PasswordCallback("Password",false)
            };
        
            callbackHandler.handle(callbacks);
            String userName = ((NameCallback)callbacks[0]).getName();
            String password = new String(((PasswordCallback)callbacks[1]).getPassword());
            
            ((PasswordCallback)callbacks[1]).clearPassword();
            
            success = validateLogin(userName, password);

            callbacks[0] = null;
            callbacks[1] = null;

            if (!success)
                throw new LoginException("Pb d'Authentification : login/password ne correspondent pas");

            return true;
            
    	}catch(Exception ex){
    	    success = false;
    	    throw new LoginException(ex.getMessage());
    	}
    }
    
    public boolean commit() throws LoginException {
        if (success) {

        	// subject peut-�tre en lecture seule, ce qui emp�che
        	// de valider l'authentification
            if (subject.isReadOnly()) {
                throw new LoginException ("Subject est en lecture seule");
            }

            try {
                Iterator it = tmpPrincipals.iterator();

                subject.getPrincipals().addAll(tmpPrincipals);
                subject.getPublicCredentials().addAll(tmpCredentials);

                tmpPrincipals.clear();
                tmpCredentials.clear();

                return(true);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
                throw new LoginException(ex.getMessage());
            }
        } else {
            tmpPrincipals.clear();
            tmpCredentials.clear();
            return(true);
        }
    }
    
    public boolean abort() throws LoginException {
        tmpPrincipals.clear();
        tmpCredentials.clear();

        logout();
    	
        return true;
    }

    public boolean logout() throws LoginException {
        tmpPrincipals.clear();
        tmpCredentials.clear();
        Iterator it = subject.getPrincipals(GraedPrincipal.class).iterator();
        while (it.hasNext()) {
            GraedPrincipal p = (GraedPrincipal)it.next();
            subject.getPrincipals().remove(p);
        }

        it = subject.getPublicCredentials(GraedCredential.class).iterator();
        while (it.hasNext()) {
            GraedCredential c = (GraedCredential)it.next();
            subject.getPrincipals().remove(c);
        }

    	return true;
    }

    private boolean validateLogin(String userName, String password)throws Exception{
    	System.out.println("Je valide : "+userName+"/"+password);
    	// on hashe le mot de passe avec MD5
    	/*MessageDigest md = MessageDigest.getInstance("MD5");
    	byte[] encodedPassword = md.digest(password.getBytes());
*/
		return true;
    }
    
}
