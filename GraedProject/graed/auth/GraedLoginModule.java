/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import graed.client.Client;
import graed.ressource.type.MaterielInterface;
import graed.user.UserInterface;
import graed.user.UserManager;
import graed.util.ldap.ConnectLDAP;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

            if (!success){
            	System.out.println("Pas loggééééé !!!!");
            	throw new LoginException("Pb d'Authentification : login/password ne correspondent pas");
            }
            else{
                tmpPrincipals.add(new GraedPrincipal(userName));
            }

            return true;
            
    	}catch(Exception ex){
    	    success = false;
    	    ex.printStackTrace();
    	    throw new LoginException(ex.getMessage());
    	}
    }
    
    public boolean commit() throws LoginException {
        if (success) {
            System.out.println("COMMIT ---");
            
        	// subject peut-être en lecture seule, ce qui empèche
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

    private boolean validateLogin(String userName, String password) throws LoginException/*throws Exception*/{
    	// on hashe le mot de passe avec MD5
    	try{
    		MessageDigest md = MessageDigest.getInstance("MD5");
    		md.update( password.getBytes() );
    		BigInteger hash = new BigInteger( 1, md.digest() );
    		String hpassword = hash.toString(16);
    		try{
    			UserManager um = Client.getUserManager();
    			UserInterface ui = um.createUser();
    			ui.setLogin(userName);
    			
    			Collection col = (Collection)um.getUsers(ui);    			 	
    			Iterator i=null;
    			if(col != null && col.size()>0){
    			    i = col.iterator();
    			}
    			else{
    			    System.out.println("Aucun utilisateur");
    			    ConnectLDAP ldap=new ConnectLDAP();
    			    if(ldap.identification(userName,password)){
    			    	success=true;
    			    	return true;
    			    }
    			    success = false;
    			    throw new LoginException();
    			}
    			UserInterface userToLog=null;
    			if(i.hasNext()){
    				userToLog = (UserInterface)i.next();
    			}
    			else{
    				success = false;
    			}	
    	
    			if(userToLog.getPassword().equals(hpassword)==false){
    				success = false;
    				throw new LoginException("mdp ne correspond pas");
    			}
    			else{
    			    success = true;
    			}
    	
    		}catch(RemoteException remoteEx){
    			System.out.println("pb avec RMI");
    			remoteEx.printStackTrace();
    			throw new LoginException("Pb avec RMI");
    		}
    	}catch(NoSuchAlgorithmException algoEx){
    	    algoEx.printStackTrace();
    		throw new LoginException("Impossible de hasher le mdp");    		
    	}

		return true;
    }
    
}
