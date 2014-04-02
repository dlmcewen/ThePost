package thepost;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import thepost.User;

public class ThePostDAO {
	
	protected Connection conn;
	protected PreparedStatement checkLogin;
	protected PreparedStatement insertUser;
	protected PreparedStatement getUserLastId;
	protected PreparedStatement updatePassword;
	protected PreparedStatement addItem;
	
	public ThePostDAO(){
		conn = null;
		try{
			String url = "jdbc:mysql://localhost/thepost";
			String uname = "user";
			String pwd = "password";

			Class.forName("com.mysql.jdbc.Driver");
			try{
				conn = DriverManager.getConnection(url,uname,pwd);
				getUserLastId = conn.prepareStatement("SELECT Auto_Increment FROM information_schema.tables WHERE table_schema = database() and table_name = 'User'");
				insertUser = conn.prepareStatement("INSERT INTO User (fName, lName, userName, hashed_pw, isAdmin) VALUES (?, ?, ?, ?, ?)");
				checkLogin = conn.prepareStatement("SELECT * FROM User WHERE userName = ?");
				updatePassword = conn.prepareStatement("UPDATE User SET hashed_pw = ? WHERE id = ?");
				//addItem = conn.prepareStatement("INSERT INTO Item (userId, price, type, description, imageLocation) VALUES (?, ?, ?, ?, ?)");
				addItem = conn.prepareStatement("INSERT INTO Item (userId, price, type, description, imageLocation) VALUE (?, ?, ?, ?, ?)");
			}catch (SQLException ex){
				ex.printStackTrace();
			}
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
	
	public boolean insertUser(String fName, String lName, String userName, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		boolean result = false;
		int userId = getUserLastId();
		User newUser = new User(userId, fName, lName, userName, password, false);
		try {
			insertUser.setString(1, newUser.getfName());
			insertUser.setString(2, newUser.getlName());
			insertUser.setString(3, newUser.getUserName());
			insertUser.setBytes(4, newUser.getEncodedPasswordBytes());
			insertUser.setBoolean(5, newUser.getIsAdmin());
			insertUser.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean insertAdmin(String fName, String lName, String userName, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		boolean result = false;
		int userId = getUserLastId();
		User newUser = new User(userId, fName, lName, userName, password, true);
		try {
			insertUser.setString(1, newUser.getfName());
			insertUser.setString(2, newUser.getlName());
			insertUser.setString(3, newUser.getUserName());
			insertUser.setBytes(4, newUser.getEncodedPasswordBytes());
			insertUser.setBoolean(5, newUser.getIsAdmin());
			insertUser.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * gets the next id from the auto increment in the database
	 * @return
	 */
	public int getUserLastId(){
		int lastId = 0;
		ResultSet rs;
		try {
			rs = getUserLastId.executeQuery();
			while(rs.next()){
				lastId = rs.getInt("Auto_Increment");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastId;
	}
	
	/**
	 * Checks if a user is in the database and if so returns the User object
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public User login(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		User newUser = user;
		ResultSet rs;
		try {
			checkLogin.setString(1, user.getUserName());
			rs = checkLogin.executeQuery();
			while(rs.next()){
				//builds a user object from the resultset
				int id = rs.getInt("id");
				String fName = rs.getString("fName");
				String lName = rs.getString("lName");
				String userName = rs.getString("userName");
				byte [] encodedPasswordArray = rs.getBytes("hashed_pw");
				boolean isAdmin = rs.getBoolean("isAdmin");
				if(Arrays.toString(user.getEncodedPasswordBytes()).equalsIgnoreCase(Arrays.toString(encodedPasswordArray))){
					newUser = new User(id, fName, lName, userName, user.getPassword(), isAdmin);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newUser;
	}

	/**
	 * updates the hashed_pw in the User table
	 * @param worker
	 */
	public void updatePassword(User user){
		int id = user.getId();
		byte [] newPassword = user.getEncodedPasswordBytes();
		try {
			updatePassword.setBytes(1, newPassword);
			updatePassword.setInt(2, id);
			updatePassword.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds an item to the database
	 * @param userId
	 * @param price
	 * @param type
	 * @param description
	 * @param imageLocation
	 */
	public boolean addItem(int userId, double price, String type, String description, String imageLocation){
		boolean result = false;
		try {
			addItem.setInt(1, userId);
			addItem.setDouble(2, price);
			addItem.setString(3, type);
			addItem.setString(4, description);
			addItem.setString(5, imageLocation);
			addItem.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
