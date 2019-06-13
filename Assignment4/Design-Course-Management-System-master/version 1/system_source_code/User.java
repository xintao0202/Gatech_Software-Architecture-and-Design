
/**
 * @author zhihuixie
 * @version 0.0.1
 * @abstract this is a abstract class for student and instruct class
 */
public abstract class User {
	/**
	 * @variables
	 * uuid user's id
	 * name user's name
	 * homeAddress user's address
	 * phoneNumber user's phone number
	 */
	public int uuid;
	public String name;
	public String homeAddress;
	public String phoneNumber;
	/**
	 * @construct for User class
	 * @param uuid
	 * @param name
	 * @param homeAddress
	 * @param phoneNumber
	 */
	public User(int uuid, String name, String homeAddress, String phoneNumber){
		this.uuid = uuid;
		this.name = name;
		this.homeAddress = homeAddress;
		this.phoneNumber = phoneNumber;
	}

}
