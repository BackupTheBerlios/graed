/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.callback.*;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.*;

/**
 * @author tcontami
 *
 */
public class GraedGraphicCallbackHandler implements CallbackHandler{

	JFrame parentFrame;
	JTextField loginField;
	JPasswordField passField;
	//final String[] loginInfos = {null,null};
	
	public GraedGraphicCallbackHandler(){
		
	}
	
	public GraedGraphicCallbackHandler(JFrame parentFrame){
		this.parentFrame = parentFrame;
		final JDialog loginDialog = new JDialog(parentFrame,"Authentification",true);
    	loginDialog.setSize(400,150);
    	loginDialog.setResizable(false);
    	loginDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JPanel p=new JPanel();
    	GridBagLayout l=new GridBagLayout();
    	p.setLayout(l);
    	
    	loginDialog.setContentPane(p);
    	
    	loginField = new JTextField();
    	passField = new JPasswordField();
    	
    	Dimension dim = new Dimension(100,20);
    	loginField.setPreferredSize(dim);
    	loginField.setMinimumSize(dim);
    	loginField.setMaximumSize(dim);

    	passField.setPreferredSize(dim);
    	passField.setMinimumSize(dim);
    	passField.setMaximumSize(dim);

    	p.add(loginField);
    	p.add(passField);
    	
    	JButton okButton = new JButton("Connecter");
    	okButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent arg0) {
    			loginDialog.dispose();
    		}
    	});

    	JButton cancelButton = new JButton("Annuler");
    	cancelButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent arg0) {
    			//dialog.dispose();
    		}
    	});

    	
    	p.add(okButton);
    	p.add(cancelButton);
    	
    	loginDialog.setVisible(true);
	}
	
    public void handle(Callback[] callbacks)
    throws java.io.IOException, UnsupportedCallbackException {
        ((NameCallback)callbacks[0]).setName(loginField.getText());
        ((PasswordCallback)callbacks[1]).setPassword(passField.getPassword());

    }

}
