package graed.user;


import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author ngonord
 *
 * Classe g�rant les utilisateurs
 */
public class User extends UnicastRemoteObject implements UserInterface{
	private String id;
	private String name;
	private String firstName;
	private String login;
	private String password;
	private String fonction;
	private String office;
	private String phone; 
	private String email;
	/**
	 * Constructeur de la classe utilisateur
	 */
	public User() throws RemoteException{
		this.name=null;
		this.firstName=null;
		this.login=null;
		this.password=null;
		this.fonction=null;
		this.office=null;
		this.phone=null; 
		this.email=null;
		
	}
	/**
	 * Constructeur de la classe utilisateur
	 * @param name nom de l'utilisateur
	 * @param firstName pr�nom de l'utilisateur
	 * @param login identifiant de l'utilisateur
	 * @param password mot de passe de l'utilisateur
	 * @param fonction role de l'utilisateur
	 * @param office bureau de l'utilisateur
	 * @param phone telephone de l'utilisateur
	 * @param email courriel de l'utilisateur
	 */
	public User(String name, String firstName, String login,
			String password, String fonction, String office,
			String phone, String email) throws RemoteException{
		this.name=name;
		this.firstName=firstName;
		this.login=login;
		this.password=password;
		this.fonction=fonction;
		this.office=office;
		this.phone=phone; 
		this.email=email;
		
	}
	/***************Getter/Setter***********************/
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail()  throws RemoteException{
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email)  throws RemoteException{
		this.email = email;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName()  throws RemoteException{
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName)  throws RemoteException{
		this.firstName = firstName;
	}
	/**
	 * @return Returns the fonction.
	 */
	public String getFonction()  throws RemoteException{
		return fonction;
	}
	/**
	 * @param fonction The fonction to set.
	 */
	public void setFonction(String fonction)  throws RemoteException{
		this.fonction = fonction;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId()  throws RemoteException{
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id)  throws RemoteException{
		this.id = id;
	}
	/**
	 * @return Returns the login.
	 */
	public String getLogin()  throws RemoteException{
		return login;
	}
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login)  throws RemoteException{
		this.login = login;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName()  throws RemoteException{
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name)  throws RemoteException{
		this.name = name;
	}
	/**
	 * @return Returns the office.
	 */
	public String getOffice()  throws RemoteException{
		return office;
	}
	/**
	 * @param office The office to set.
	 */
	public void setOffice(String office)  throws RemoteException{
		this.office = office;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword()  throws RemoteException{
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password)  throws RemoteException{
		this.password = password;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone()  throws RemoteException{
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone)  throws RemoteException{
		this.phone = phone;
	}
    /* (non-Javadoc)
     * @see graed.ressource.RessourceInterface#setType(java.lang.String)
     */
    public void setType(String type) throws RemoteException {
    }
    /* (non-Javadoc)
     * @see graed.ressource.RessourceInterface#getType()
     */
    public String getType() throws RemoteException {
        return "User";
    }
    
    /* (non-Javadoc)
     * @see graed.ressource.RessourceInterface#getId_ressource()
     */
    public String getId_ressource() throws RemoteException {
        return getId();
    }
    /* (non-Javadoc)
     * @see graed.ressource.RessourceInterface#setId_ressource(java.lang.String)
     */
    public void setId_ressource(String id_ressource) throws RemoteException {
    }
    /* (non-Javadoc)
     * @see graed.ressource.RessourceInterface#print()
     */
    public String print() throws RemoteException {
    	return name+firstName;
    }
    /* (non-Javadoc)
     * @see graed.user.UserInterface#control()
     */
    public String control() throws RemoteException{
    	if(name==null || name.equals(""))return "Veuillez renseigner le nom de l'utilisateur";
		if(firstName==null || firstName.equals(""))return "Veuillez renseigner le pr�nom de l'utilisateur";
		if(login==null || login.equals(""))return "Veuillez renseigner le login de l'utilisateur";
		if(password==null || password.equals(""))return "Veuillez renseigner le mot de passe de l'utilisateur";
		if(fonction==null || fonction.equals(""))return "Veuillez renseigner le r�le de l'utilisateur";
		if(email==null || email.equals(""))return "Veuillez renseigner le courriel de l'utilisateur";
		return null;
    }
    
    public static String encodePassword(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update( password.getBytes() );
		BigInteger hash = new BigInteger( 1, md.digest() );
		String hpassword = hash.toString(16);
		return hpassword;
    }
}
