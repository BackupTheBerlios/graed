package graed.user;

/**
 * @author ngonord
 *
 * Classe gérant les utilisateurs
 */
public class User {
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
	public User(){
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
	/***************Getter/Setter***********************/
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the fonction.
	 */
	public String getFonction() {
		return fonction;
	}
	/**
	 * @param fonction The fonction to set.
	 */
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the login.
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login The login to set.
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the office.
	 */
	public String getOffice() {
		return office;
	}
	/**
	 * @param office The office to set.
	 */
	public void setOffice(String office) {
		this.office = office;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
