/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

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
    
    // objets récupérés initialement
    CallbackHandler callbackHandler;
    Subject subject;
    Map sharedState;
    Map options; // options passées via le fichier de conf
    
    // objets temporaires
    TreeSet tmpCredentials;
    TreeSet tmpPrincipals;
    
    // drapeau pour connaitre le status de l'authentification
    boolean success;

    /**
     * Création d'un module de login spécifique à l'application Graed
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
     * @param subject le sujet à authentifier
     * @param callbackHandler le CallBackHandler permettant de dialoguer avec l'utilisateur
     * @param sharedState
     * @param options les options spécifiées dans le fichier de conf 
     */
    public void initialize(Subject subject, CallbackHandler callbackHandler,
            Map sharedState, Map options){
        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.sharedState = sharedState;
        this.options = options;
    }
    
    /**
     * Méthode d'authetification de l'utilisateur
     * 
     * @return true toujours
     * @exception FailedLoginException si l'authentification échoue
     * @exception LoginException s'il est impossible de réaliser l'authentification
     */
    public boolean login() throws LoginException {
        if(callbackHandler == null){
            throw new LoginException("CallbackHandler inexistant, impossible d'authentifier l'utilisateur");
        }
        
        try{
            // on crée les 2 Callback nécessaire au CallbackHandler
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
        return true;
    }
    
    public boolean abort() throws LoginException {
        return true;
    }

    public boolean logout() throws LoginException {
        return true;
    }

    private boolean validateLogin(String userName, String password){
        return true;
    }
    
}
