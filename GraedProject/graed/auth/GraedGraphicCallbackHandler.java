/*
 * Created on 7 mars 2005
 *
 */
package graed.auth;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.callback.*;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.*;
import javax.swing.text.JTextComponent;

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
    	loginDialog.setSize(300,100);
    	loginDialog.setResizable(false);
    	loginDialog.setLocationRelativeTo(parentFrame);
    	loginDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	JPanel p=new JPanel();
    	GridBagLayout l=new GridBagLayout();
    	GridBagConstraints c= new GridBagConstraints();
    	p.setLayout(l);
    	c.gridheight = 1;
    	c.weighty=1;
    	c.fill = GridBagConstraints.BOTH;
    	c.insets=new Insets(1,1,1,1);
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

    	c.gridy = 1;
    	c.gridx = 1;
    	p.add(new JLabel("Login : "),c);
    	c.gridx = 2;
    	p.add(loginField,c);

    	c.gridy = 2;
    	c.gridx = 1;
    	p.add(new JLabel("Password :"),c);
    	c.gridx = 2;
    	p.add(passField,c);
    	
    	JButton okButton = new JButton("Connecter");
    	okButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent arg0) {
    			loginDialog.dispose();
    		}
    	});

    	JButton cancelButton = new JButton("Annuler");
    	cancelButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent arg0) {
    			loginDialog.dispose();
    		}
    	});

    	c.gridy = 3;
    	c.gridx = 1;
    	p.add(okButton,c);
    	
    	c.gridx = 2;
    	p.add(cancelButton,c);
    	
    	loginDialog.setVisible(true);
	}
	
    public void handle(Callback[] callbacks)
    throws java.io.IOException, UnsupportedCallbackException {
        ((NameCallback)callbacks[0]).setName(loginField.getText());
        ((PasswordCallback)callbacks[1]).setPassword(passField.getPassword());

    }

}
