/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JFrame;

/**
 * @author tcontami
 *
 */
public class AuthTest {
	
	public static void main(String[] args){
		
		Subject subj = null;
		JFrame mainFrame = new JFrame();
		try{
			GraedGraphicCallbackHandler cbh = new GraedGraphicCallbackHandler(mainFrame);
			
			LoginContext lc = new LoginContext("GraedAuth",cbh);
			lc.login();
			
			subj = lc.getSubject();
			
		}catch(LoginException e){
			e.printStackTrace();
		}
	}
}
