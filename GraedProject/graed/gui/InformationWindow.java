/*
 * Created on 21 f�vr. 2005
 *
 */
package graed.gui;

import graed.exception.InvalidStateException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author Gonord Nad�ge
 *
 * The window for alter teachers
 */
public abstract class InformationWindow {

/**
 * The different state to open the window
 */
protected static int CREATE=1;
protected static int MODIFY=2;
protected static int SEE=3;
protected static int SEARCH=4;
/**
 * The current state
 */
private int state;
private Object o;


/**
 * Constructor which open the information window
 * @param state the state of the window
 * @throws InvalidStateException
 */
public InformationWindow(int state,Object o) throws InvalidStateException{
	if((state!=CREATE && state!=MODIFY
			&& state!=SEE && state!=SEARCH)
			|| (o==null && 
			state!=CREATE && state!=SEARCH))
		throw new InvalidStateException();
	this.state=state;
	this.o=o;	
	OpenWindow();
}
/**
 * Open and fill the window
 */
protected abstract void OpenWindow();
/**
 * Cr�ation du bouton annuler
 * @return bouton
 */
protected JButton stop(){
	JButton b=new JButton("Annuler");
	b.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}		
	});
	return b;
	
}
/**
 * Cr�ation du bouton modifier
 * @return bouton
 */
protected abstract JButton modify();

/**
 * Cr�ation du bouton creer
 * @return bouton
 */
protected abstract JButton create();
/**
 * Cr�ation du bouton de recherche
 * @return bouton
 */
protected abstract JButton search();
/**
 * Retourne l'�tat d'appel de la fen�tre
 * @return l'�tat d'appel de la fen�tre
 */
public int getState(){
	return state;
}
/**
 * Retourne l'information affich�e par la fen�tre
 * @return l'information affich�e par la fen�tre
 */
public Object getInformation(){
	return o;
}
/**
 * Modifie l'information contenue dans la fen�tre
 * @param o la nouvelle information
 */
public void setInformation(Object o){
	this.o=o;
}
/**
 * On test si la fen�tre est en mode consultation
 * @return vrai si la fen�tre est en mode consultation faux sinon
 */
public boolean isSee(){
	return state==SEE;
}
/**
 * On test si la fen�tre est en mode modification
 * @return vrai si la fen�tre est en mode modification faux sinon
 */
public boolean isModify(){
	return state==MODIFY;
}
/**
 * On test si la fen�tre est en mode recherche
 * @return vrai si la fen�tre est en mode recherche faux sinon
 */
public boolean isSearch(){
	return state==SEARCH;
}
/**
 * On test si la fen�tre est en mode cr�ation
 * @return vrai si la fen�tre est en mode cr�ation faux sinon
 */
public boolean isCreate(){
	return state==CREATE;
}
}
