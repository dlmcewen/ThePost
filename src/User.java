package thepost;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Creates a user object, which depending on the 'role' will distinguies between
 * a user and an admin;
 * @author edkil_000
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
	
	/**
	 * Creates a user object with an id, first name, last name, username, password,
	 * email, and states whether or not that user is an admin.  Also initializes an 
	 * itemList.
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
	}
	
	/**
	 * Create a User object with only a username and password
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
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * returns the first name of the user
	 * @return
	 */
	public String getfName() {
		return fName;
	}
	
	/**
	 * returns the last name of the user
	 * @return
	 */
	public String getlName() {
		return lName;
	}

//	/**
//	 * returns the email of the user
//	 * @return
//	 */
//	public String getEmail() {
//		return email;
//	}

	/**
	 * returns the username of the user
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * returns the password of the user
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * returns true of user is an admin
	 * @return
	 */
	public boolean getIsAdmin(){
		return isAdmin;
	}
	
	/**
	 * returns the byte array of the encoded password
	 * @return
	 */
	public byte[] getEncodedPasswordBytes(){
		return encodedPasswordBytes;
	}
	
	/**
	 * adds an Item to the list of Items
	 * @param item
	 */
	public void addItem(Item item){
		itemList.add(item);
	}
	
	/**
	 * returns the list of items for sale
	 * @return
	 */
	public ArrayList<Item> getItemList(){
		return itemList;
	}
	
	/**
	 * removes an item from the itemList
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

}
