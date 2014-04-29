package thepost;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Creates a user object, which depending on the 'role' will distinguies between
 * a user and an admin;
 * @author Edward Killmeier
 *
 */

public class User {
	
	private int id;
	private String fName;
	private String lName;
	//private String email;
	private String userName;
	private String password;
	private boolean isAdmin;
	byte [] encodedPasswordBytes;
	private ArrayList<Item> itemList;
	private ArrayList<User> userList;
	
	/**
	 * Creates a user object with an id, first name, last name, username, password,
	 * email, and states whether or not that user is an admin.  Also initializes an 
	 * itemList.
	 * @author Edward Killmeier
	 * @param id
	 * @param fName
	 * @param lName
	 * @param email
	 * @param userName
	 * @param password
	 * @param isAdmin
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public User(int id, String fName, String lName, String userName, String password, boolean isAdmin) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		//this.email = email;
		this.userName = userName;
		this.password = password;
		this.isAdmin = isAdmin;
		byte [] bytesOfPassword = password.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		encodedPasswordBytes = md.digest(bytesOfPassword);
		itemList = new ArrayList<Item>();
		userList = new ArrayList<User>();
	}
	
	/**
	 * Create a User object with only a username and password
	 * @author Edward Killmeier
	 * @param userName
	 * @param password
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public User(String userName, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		this.id = -1;
		this.userName = userName;
		this.password = password;
		byte [] bytesOfPassword = password.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		encodedPasswordBytes = md.digest(bytesOfPassword);
	}
	
	/**
	 * returns the id of the user
	 * @author Edward Killmeier
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * returns the first name of the user
	 * @author Edward Killmeier
	 * @return
	 */
	public String getfName() {
		return fName;
	}
	
	/**
	 * returns the last name of the user
	 * @author Edward Killmeier
	 * @return
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * returns the username of the user
	 * @author Edward Killmeier
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * returns the password of the user
	 * @author Edward Killmeier
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * returns true of user is an admin
	 * @author Edward Killmeier
	 * @return
	 */
	public boolean getIsAdmin(){
		return isAdmin;
	}
	
	/**
	 * returns the byte array of the encoded password
	 * @author Edward Killmeier
	 * @return
	 */
	public byte[] getEncodedPasswordBytes(){
		return encodedPasswordBytes;
	}
	
	/**
	 * adds an Item to the list of Items
	 * @author Edward Killmeier
	 * @param item
	 */
	public void addItem(Item item){
		itemList.add(item);
	}
	
	/**
	 * returns the list of items for sale
	 * @author Edward Killmeier
	 * @return
	 */
	public ArrayList<Item> getItemList(){
		return itemList;
	}
	
	/**
	 * removes an item from the itemList
	 * @author Edward Killmeier
	 * @param itemId
	 */
	public void removeItem(int itemId){
		for(int i = 0; i < itemList.size(); i++){
			if(itemList.get(i).getItemId() == itemId){
				itemList.remove(i);
			}
		}
	}
	
	/**
	 * sets the password of the user
	 * @author Edward Killmeier
	 * @param newPassword
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void setPassword(String newPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		this.password = newPassword;
		byte [] bytesOfPassword = newPassword.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		encodedPasswordBytes = md.digest(bytesOfPassword);
	}
	
	/**
	 * Removes all the Items on the user's list
	 * @author Edward Killmeier
	 */
	public void removeAllItems(){
		itemList.clear();
	}
	
	/**
	 * Adds a user to the userlist for the admin
	 * @author Edward Killmeier
	 * @param user
	 */
	public void addUsersAsAdmin(User user){
		boolean check = false;
		for(int i = 0; i < userList.size(); i++){
			if(userList.get(i).getId() == user.getId()){
				check = true;
			}
		}
		if(!check){
			userList.add(user);
		}
	}
	
	/**
	 * Clears the list of users
	 * @author Edward Killmeiers
	 */
	public void clearUsers(){
		userList.clear();
	}
	
	/**
	 * Returns a list of users
	 * @author Edward Killmeier
	 * @return
	 */
	public ArrayList<User> getUserList(){
		return userList;
	}

}
