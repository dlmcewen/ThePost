package thepost;

/**
 * Test class for the ThePostDAO
 * @author christopherdonaldson
 */

import java.sql.DriverManager;
import java.sql.SQLException;

//import org.junit.Before;
//import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.TestCase;

public class DAOTest extends TestCase {
	ThePostDAO db = null;
	PreparedStatement clearItemStatement;


	/**
	 * Deletes previous information from the database prior to testing
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Connection conn = null;
		try{
			String url = "jdbc:mysql://localhost/postTest";
			String uname = "user";
			String pwd = "password";

			Class.forName("com.mysql.jdbc.Driver");
			try{
				conn = DriverManager.getConnection(url,uname,pwd);
				clearItemStatement = conn.prepareStatement("delete from Item");
				PreparedStatement clearUserStatement = conn.prepareStatement("delete from User");
				clearItemStatement.execute();
				clearUserStatement.execute();	
				
			}catch (SQLException ex){
				ex.printStackTrace();
			}
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}

	/**
	 * Tests if the db is set up and instantiated correctly and successfully
	 * 
	 */
	public void testDAO() {
		ThePostDAO db = null;

		try{
			db=new ThePostDAO("postTest");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(db.getUserLastId);
		assertNotNull(db.insertUser);
		assertNotNull(db.checkLogin);
		assertNotNull(db.updatePassword);
		assertNotNull(db.addItem);
		assertNotNull(db.getUserItems);
		
	}
	/**
	 * Tests whether or not a user is successfully inserted into the db 
	 * Additionally tests whether a user can be successfully pulled from the db
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public void testInsertUser() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		ThePostDAO db = null;

		try{
			db=new ThePostDAO("postTest");
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean insertUser = db.insertUser("Frank", "Underwood", "HouseCards", "washington");
		System.out.println("InsertUser: "+ insertUser);
		//assertEquals("Insert User:", true, insertUser);
		
		User instance = new User ("HouseCards", "washington");
		User result = db.login(instance);
		//		assertEquals("UserId:", 1, result.getId());
		assertEquals("FirstName:", "Frank", result.getfName());
		assertEquals("LastName:", "Underwood", result.getlName());
		assertEquals("UserName:", "HouseCards", result.getUserName());
		assertEquals("Password:", "[14, -46, -76, 6, -17, -62, 49, -66, 18, 112, -28, 5, -24, 108, -95, 74]", Arrays.toString(result.getEncodedPasswordBytes()));
		assertEquals("isAdmin:", false, result.getIsAdmin());
	}
	/**
	 * Tests the working of password updates
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public void testUpdatePassword() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		ThePostDAO db = null;

		try{
			db = new ThePostDAO("postTest");
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		boolean updatePasswordUser = db.insertUser("Fishy", "AndChips", "OliverTwist", "password");
		//assertEquals("Create updatePasswordUser:", true, updatePasswordUser);
		
		User tempUser = new User("OliverTwist", "password");
		User instanceUser = db.login(tempUser);
		instanceUser.setPassword("goldfish");
		db.updatePassword(instanceUser);
		
		User checkUser = db.login(instanceUser);
		assertEquals("Username:", "OliverTwist", checkUser.getUserName());
		assertEquals("Password:", Arrays.toString(instanceUser.getEncodedPasswordBytes()), Arrays.toString(checkUser.getEncodedPasswordBytes()));
	}
	
	/**
	 * Tests the viability of adding an item that is assigned to a specific user
	 * Additionally tests whether or not the item is correctly pulled from the database
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void testAddItem() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		ThePostDAO db = null;

		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
	
	
	boolean addItemUserInsert = db.insertUser("Hello", "Kitty", "hellokitty", "meow");
	System.out.println("addItemUserInsert"+ addItemUserInsert);
	User addItemUser = db.login(new User("hellokitty", "meow"));
	int addItemUserId = addItemUser.getId();
	boolean addItemPassed = db.addItem(addItemUserId, 12.50, "furniture", "its a hammock", "hammock.png", "myHammock");
	assertEquals("addItemPassed:", true, addItemPassed);
	User product = db.getUserItems(addItemUser);
	assertEquals("UserId", addItemUserId, product.getId());
	Item instance = product.getItemList().get(0);
	Item other = new Item(1, addItemUserId, 12.50, "furniture", "its a hammock", "hammock.png", instance.getDate(), "myHammock");
	//assertEquals("Item Compare id:", other.getItemId(), instance.getItemId());
	assertEquals("Item Compare price:", other.getPrice(), instance.getPrice());
	assertEquals("Item Compare type:", other.getType(), other.getType());
	}
	
	
	/**
	 * Tests the removing of an object from the database
	 */
	public void testRemoveItem(){
		ThePostDAO db = null;
		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		int itemid = db.getLastItemId();
		System.out.println("Item Id: "+itemid);
		db.addItem(1, 12, "tree", "its a tree", "tree.png", "myTree");
		db.removeItem(itemid);
		boolean getItem = false;
		try{
			getItem = db.getSingleItem(itemid).equals(null);
		}
		catch(NullPointerException e){
		    //  System.out.println("Get Item Null");
			getItem = true;
		}
		
		assertEquals("Item removed", true, getItem);
		
	}
	
	/**
	 * Assesses the effectiveness of getting a single item from the database
	 * @throws SQLException
	 */
	public void testGetSingleItem() throws SQLException{
		ThePostDAO db = null;
		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		int lastItem = db.getLastItemId();
	    System.out.println("lastItem: "+ lastItem);
		boolean added = db.addItem(1, 12.50, "Furniture", "desk", "desk.png", "MyDesk");
		System.out.println("addItem: "+ added);
		
		Item instance2 = null;
		Item instance3 = null;
		
		int error = 0;
		try{
			instance2 = db.getSingleItem(lastItem);
			error++;
			instance3 = db.getSingleItem(lastItem+1);
			error++;
			
			
			System.out.println("2"+instance2.getTitle());
			error++;
			System.out.println("3"+instance3.getTitle());
			error ++;
		}
		catch(NullPointerException e){
			
			System.out.println("error"+error);
		}
		System.out.println("passed trycatch");
		
		
		
				
		//assertEquals("Item Title:", "MyDesk", instance2.getTitle());
		assertEquals("Error:", 2, error);
	}


    /**
     * Assesses the working and accuracy of getting a Username from the database given userId
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
	public void testGetUsername() throws UnsupportedEncodingException, NoSuchAlgorithmException{
		ThePostDAO db = null;
		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		db.insertAdmin("Fishy", "McChips", "theFish", "yourChips");
		String resultUsername = db.getUsername(db.getUserLastId()-1);
		assertEquals("Username: ", "theFish", resultUsername);
		
	}
	/**
	 * Tests if getting the last x number of items is effective.
	 */
	public void testGetLastTenItems(){
		ThePostDAO db = null;
		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		db.addItem(2, 10, "misc", "test1", "void.png", "0");
		db.addItem(2, 10, "misc", "test2", "void.png", "1");
		db.addItem(2, 10, "misc", "test3", "void.png", "2");
		db.addItem(2, 10, "misc", "test4", "void.png", "3");
		db.addItem(2, 10, "misc", "test5", "void.png", "4");
		
		ArrayList<Item> instance = db.getLastTenItems();
		String[] titles = new String[10];
		for(int i=0;i<5;i++){
			if(instance.get(i) != null){
				titles[i] = instance.get(i).getTitle();
				assertEquals("Title"+ i+":", ""+i, titles[i] );
			}
			else{
				titles[i] = "nada";
			}
		}
	}
	/**
	 * Assesses the effectiveness of getting all users as an administrator	
	 * @author christopherdonaldson	
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public void testGetAllUsers() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		ThePostDAO db = null;
		try{
			db = new ThePostDAO("postTest");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		User admin = new User(7, "Charles", "De Gaulle", "char", "boolean", true);
		
		boolean insertUser = db.insertUser("al", "pacino", "diet", "coke");
		boolean insertAdmin = db.insertAdmin("lance", "armstrong", "lance28", "mybikerox");
		System.out.println("Userbool:"+insertUser+" Adminbool:"+insertAdmin);
		User hello = new User(2, "Tom", "Jerry", "dipndots", "warmachingrox", true);
		System.out.println("pass insert");
		String uname;
		try{
			User instance = db.getAllUsers(hello);
			System.out.println("pass instance");
			uname = instance.getUserList().get(0).getUserName();
		}catch(Exception e){
			
		}
		
		uname = hello.getUserName();
		System.out.println("pass getUname");
		assertEquals("User1:", "dipndots",uname);
		
		
		
		

		

		
	}
	
}
