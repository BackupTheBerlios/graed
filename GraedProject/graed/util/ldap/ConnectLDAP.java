package graed.util.ldap;


import graed.exception.DataBaseException;
import graed.ressource.RessourceManagerImpl;
import graed.ressource.type.Group;
import graed.ressource.type.Room;
import graed.ressource.type.Teacher;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.*;
import javax.naming.directory.*;


/**
 * @author ngonord
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnectLDAP {
	private String urlLDAP = "ldapetud.univ-mlv.fr";
	private String portLDAP = "389";
	private String baseLDAP = "dc=univ-mlv,dc=fr";
	private DirContext contexteLDAP;
	private String path;
	private String filter;
	
	/* ************************* Environnement ********************** */
	public ConnectLDAP(){
		contexteLDAP=null;
	}	
	/**
	 * Création d'un envirronnement pour l'arbre LDAP basique
	 * @return l'environnement de l'annuaire
	 */
	private Hashtable preparerEnvirronnement (){
		Hashtable environnementLDAP = new Hashtable();
		environnementLDAP.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		environnementLDAP.put(Context.PROVIDER_URL,"ldap://"+urlLDAP+":"+portLDAP);
		environnementLDAP.put("java.naming.ldap.version","3");
		return environnementLDAP;
	}
	
	/**
	 * On test la connexion à l'annuaire LDAP
	 * @param environnementLDAP
	 * @return vrai si on est connecté faux sinon
	 */
	private boolean initialiserConnexionLDAP (Hashtable environnementLDAP){
		contexteLDAP = null;
		try {
			contexteLDAP = new InitialDirContext (environnementLDAP);
		}
		catch (Exception erreur)
		{
			return false;
		}
		return true;		
	}
	/* ******************** Identification ******************** */
	/**
	 * Création de l'envirronnement pour l'arbre LDAP pour l'identification
	 * @param loginConnexion login
	 * @param motpasseConnexion mot de passe
	 * @return l'environnement de l'annuaire
	 */
	public boolean identification (String loginConnexion, String motpasseConnexion){
		Hashtable environnementLDAP = preparerEnvirronnement ();
		environnementLDAP.put(Context.SECURITY_AUTHENTICATION,"simple");
		environnementLDAP.put(Context.SECURITY_PRINCIPAL,"uid="+loginConnexion+", ou=Users, ou=Etudiant, dc=univ-mlv,dc=fr");
		environnementLDAP.put(Context.SECURITY_CREDENTIALS,motpasseConnexion);
		return initialiserConnexionLDAP(environnementLDAP);
	}
	/* ************************ Recherche d'une ressource ************************ */
	/**
	 * On effectue la recherche dans l'arbre LDAP
	 * @return résultat de la recherche
	 */
	private NamingEnumeration search() {
		NamingEnumeration bds=null;
		int i = 0;
			try {				
				bds = contexteLDAP.search(path,filter,null);				
			} catch (NamingException e) {
				return null;
			}
		       
		return bds;
		
	}
	
	/* ************************ Recherche d'un professeur ************************ */
	/**
	 * Recherche de la liste des professeurs
	 * @return liste des professeurs
	 */
	public List searchTeachers(){
		initialiserConnexionLDAP(preparerEnvirronnement ());
		this.filter="gidNumber=801";
		this.path="ou=Users,ou=Etudiant,dc=univ-mlv,dc=fr";
		return createTeacherList(search());
	}
	/**
	 * Renvoie une liste de professeurs
	 * @param ne resultat de la recherche avec LDAP
	 * @return liste des professeurs
	 */
	private List createTeacherList(NamingEnumeration ne){
		List l=new LinkedList();
		try {
			while (ne.hasMore()) {
				 NameClassPair item = (NameClassPair)ne.next();
				 Attributes attr=((SearchResult)item).getAttributes();	
				 l.add(new Teacher(((String)attr.get("sn").get()),
				 		((String)attr.get("givenName").get()),"","", 
						((String)attr.get("mail").get())));
			}
		} catch (NamingException e) {
			return null;
		}
		return l;
	}
	/* ************************ Recherche d'une salle ************************ */
	//TODO
	/**
	 * Recherche de la liste des salles
	 * @return liste des salles
	 */
	public List searchRoom(){
		initialiserConnexionLDAP(preparerEnvirronnement ());
		this.filter="(objectClass=*)";
		this.path="ou=UFR,dc=univ-mlv,dc=fr";
		return createRoomList(search());
	}
	/**
	 * Renvoie une liste de salles
	 * @param ne resultat de la recherche avec LDAP
	 * @return liste des salles
	 */
	private List createRoomList(NamingEnumeration ne){
		List l=new LinkedList();
		try {
			while (ne.hasMore()) {
				 NameClassPair item = (NameClassPair)ne.next();
				 Attributes attr=((SearchResult)item).getAttributes();	
				 if(!((String)attr.get("ou").get()).equals("AMPHIS")){
				 	this.path="ou=salles,ou="+((String)attr.get("ou").get())+",ou=UFR,dc=univ-mlv,dc=fr";
				 	NamingEnumeration salle=search();
				 	while (salle !=null && salle.hasMore()) {
						 NameClassPair item2 = (NameClassPair)salle.next();
						 Attributes attr2=((SearchResult)item2).getAttributes();					
						 l.add(new Room(((String)attr2.get("uid").get()), 
						 		((String)attr.get("description").get()),
								((String)attr.get("ou").get()),0));
				 	}	 
				 }
				 else{
				 	this.path="ou="+((String)attr.get("ou").get())+",ou=UFR,dc=univ-mlv,dc=fr";
				 	NamingEnumeration amphi=search();
				 	while (amphi !=null && amphi.hasMore()) {
						 NameClassPair item2 = (NameClassPair)amphi.next();
						 Attributes attr2=((SearchResult)item2).getAttributes();
						 this.path="ou="+((String)attr2.get("ou").get())+",ou="+((String)attr.get("ou").get())+",ou=UFR,dc=univ-mlv,dc=fr";
						 NamingEnumeration salle=search();
						 while (salle !=null && salle.hasMore()) {
								 NameClassPair item3 = (NameClassPair)salle.next();
								 Attributes attr3=((SearchResult)item3).getAttributes();								
								 l.add(new Room(((String)attr3.get("uid").get()), 
								 		((String)attr2.get("description").get()),
										((String)attr2.get("ou").get()),0));
						 }					 
						
				 	}	 
				 }
				
			}			
		} catch (NamingException e) {
			return null;
		}
		return l;
	}
	/* *********************** Recherche des formations ************************ */
	/**
	 * Recherche de la liste des formations
	 * @return liste des formations
	 */
	public List searchGroup(){
		initialiserConnexionLDAP(preparerEnvirronnement ());
		this.filter="(objectClass=*)";
		this.path="ou=Groups,ou=Etudiant,dc=univ-mlv,dc=fr";
		return createGroupList(search());
	}
	/**
	 * Renvoie une liste des formations des etudiants
	 * @param ne resultat de la recherche avec LDAP
	 * @return liste des formations des etudiants
	 */
	private List createGroupList(NamingEnumeration ne){
		List l=new LinkedList();
		try {
			while (ne.hasMore()) {
				 NameClassPair item = (NameClassPair)ne.next();
				 Attributes attr=((SearchResult)item).getAttributes();
				 Attribute description=attr.get("description");
				 Attribute mailLocalAddress=attr.get("mailLocalAddress");
				 if(description!=null && mailLocalAddress!=null)
				 	l.add(new Group(((String)attr.get("gidNumber").get()),
				 		((String)attr.get("cn").get()),
				 		((String)description.get()),
				 		((String)mailLocalAddress.get())));
			}
		} catch (NamingException e) {
			return null;
		}
		return l;
	}
	/* ********** Recherche des mails des etudiants d'une formation *********** */
	/**
	 * Recherche de la liste des mails des etudiants d'une formation
	 * @return liste des professeurs
	 */
	public List searchStudents(Group g){
		initialiserConnexionLDAP(preparerEnvirronnement ());
		this.filter="gidNumber="+g.getNumber();
		this.path="ou=Users,ou=Etudiant,dc=univ-mlv,dc=fr";
		return createStudentList(search());
	}
	/**
	 * Renvoie une liste des mails des etudiants
	 * @param ne resultat de la recherche avec LDAP
	 * @return liste des mails des etudiants
	 */
	private List createStudentList(NamingEnumeration ne){
		List l=new LinkedList();
		try {
			while (ne.hasMore()) {
				 NameClassPair item = (NameClassPair)ne.next();
				 Attributes attr=((SearchResult)item).getAttributes();	
				 l.add(((String)attr.get("mail").get()));
			}
		} catch (NamingException e) {
			return null;
		}
		return l;
	}
	
	/* ******************* Test ************************ */
	public static void main(String[] args) throws DataBaseException {
		ConnectLDAP ldap=new ConnectLDAP();
		Hashtable environnementLDAP = ldap.preparerEnvirronnement ();
		ldap.initialiserConnexionLDAP(environnementLDAP);
		/*List p= ldap.searchTeachers();
		System.out.println(p.size()+" teacher find:");
		for (Iterator i=p.iterator();i.hasNext();){
			Teacher t=(Teacher)i.next();
			System.out.println(t);
			try {
				RessourceManagerImpl.getInstance().addRessource(t);
			} catch (RemoteException e) {				
				e.printStackTrace();
			}
		}*//*
		List p= ldap.searchGroup();
		System.out.println(p.size()+" groups find:");
		for (Iterator i=p.iterator();i.hasNext();){
			Group g=(Group) i.next();
			System.out.println(g);
			List e= ldap.searchStudents(g);
			System.out.println(e.size()+" students find:");
			for (Iterator j=e.iterator();j.hasNext();)
				System.out.println("***"+j.next());
			}*/
		List p= ldap.searchRoom();
		System.out.println(p.size()+" rooms find:");
		for (Iterator i=p.iterator();i.hasNext();){
				Room r=(Room)i.next();
				System.out.println(r);
				try {
					RessourceManagerImpl.getInstance().addRessource(r);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
