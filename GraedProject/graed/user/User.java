package graed.user;

/**
 * @author ngonord
 *
 * Classe gérant les utilisateurs
 */
public class User {
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
	 * @param name nom de l'utilisateur
	 * @param firstName prénom de l'utilisateur
	 * @param login identifiant de l'utilisateur
	 * @param password mot de passe de l'utilisateur
	 * @param fonction role de l'utilisateur
	 * @param office bureau de l'utilisateur
	 * @param phone telephone de l'utilisateur
	 * @param email courriel de l'utilisateur
	 */
	public User(String name, String firstName, String login,
			String password, String fonction, String office,
			String phone, String email){
		this.name=name;
		this.firstName=firstName;
		this.login=login;
		this.password=password;
		this.fonction=fonction;
		this.office=office;
		this.phone=phone; 
		this.email=email;
		
	}
	
}
