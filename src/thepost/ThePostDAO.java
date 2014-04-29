package thepost;

import java.io.FileInputStream;
import java.io.InputStream;
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
import java.util.HashSet;
import java.util.Set;

import thepost.User;

/**
 * Creates a connection with thepost database and initializes preparedstatements
 * to run methods.
 * @author edkil_000
 *
 */
public class ThePostDAO {
	
	protected Connection conn;
	protected PreparedStatement checkLogin;
	protected PreparedStatement insertUser;
	protected PreparedStatement getUserLastId;
	protected PreparedStatement updatePassword;
	protected PreparedStatement addItem;
	protected PreparedStatement getUserItems;
	protected PreparedStatement updateItem;
	protected PreparedStatement getAllItems;
	protected PreparedStatement getSearchItems;
	protected PreparedStatement getPriceByCat;
	protected PreparedStatement getPriceAll;
	protected PreparedStatement getSingleItem;
	protected PreparedStatement removeItem;
	protected PreparedStatement getLastItemId;
	protected PreparedStatement getUsername;
	protected PreparedStatement getLastTenItems;
	protected PreparedStatement getAllUsers;
	protected PreparedStatement getAllItemsAdmin;
	protected PreparedStatement getSingleUser;
	protected PreparedStatement removeUser;
	
	// select * from Item order by date desc limit 10;
	
	/**
	 * Connects to database and initializes preparedstatements
	 * @author Edward Killmeier, Cameron Day, Chris Donaldson
	 */
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
				addItem = conn.prepareStatement("INSERT INTO Item (userId, price, type, description, imageLocation, title) VALUE (?, ?, ?, ?, ?, ?)");
				getUserItems = conn.prepareStatement("SELECT * FROM Item WHERE userId = ?");
				updateItem = conn.prepareStatement("UPDATE Item SET price = ?, type = ?, description = ?, imageLocation = ?, title = ? WHERE itemId = ?");
				getAllItems = conn.prepareStatement("SELECT * FROM Item");
				getSearchItems = conn.prepareStatement("SELECT * FROM Item where type = ?");
				getPriceByCat = conn.prepareStatement("SELECT * FROM Item WHERE (price between ? and ?) and type IN (?)");
				getPriceAll = conn.prepareStatement("SELECT * FROM Item WHERE (price between ? and ?)");
				getSingleItem = conn.prepareStatement("SELECT * FROM Item WHERE itemId = ?");
				removeItem = conn.prepareStatement("DELETE FROM Item WHERE itemId = ?");
				getLastItemId = conn.prepareStatement("Select Auto_Increment FROM information_schema.tables WHERE table_schema = database() and table_name = 'Item'");
				getUsername = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
				getLastTenItems = conn.prepareStatement("Select * from Item order by date desc limit 10");
				getAllUsers = conn.prepareStatement("SELECT * FROM User");
				getAllItemsAdmin = conn.prepareStatement("SELECT * FROM Item");
				getSingleUser = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
				removeUser = conn.prepareStatement("DELETE FROM User WHERE id = ?");
			}catch (SQLException ex){
				ex.printStackTrace();
			}
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Creates a test connection to postTest database to run all test statements
	 * @author Chris Donaldson
	 * @param test
	 */
	public ThePostDAO(String test){
		conn = null;
		try{
			String url = "jdbc:mysql://localhost/"+test;
			String uname = "user";
			String pwd = "password";

			Class.forName("com.mysql.jdbc.Driver");
			try{
				conn = DriverManager.getConnection(url,uname,pwd);
				getUserLastId = conn.prepareStatement("SELECT Auto_Increment FROM information_schema.tables WHERE table_schema = database() and table_name = 'User'");
				insertUser = conn.prepareStatement("INSERT INTO User (fName, lName, userName, hashed_pw, isAdmin) VALUES (?, ?, ?, ?, ?)");
				checkLogin = conn.prepareStatement("SELECT * FROM User WHERE userName = ?");
				updatePassword = conn.prepareStatement("UPDATE User SET hashed_pw = ? WHERE id = ?");
				addItem = conn.prepareStatement("INSERT INTO Item (userId, price, type, description, imageLocation, title) VALUE (?, ?, ?, ?, ?, ?)");
				getUserItems = conn.prepareStatement("SELECT * FROM Item WHERE userId = ?");
				updateItem = conn.prepareStatement("UPDATE Item SET price = ?, type = ?, description = ?, imageLocation = ?, title = ? WHERE itemId = ?");
				getAllItems = conn.prepareStatement("SELECT * FROM Item");
				getSearchItems = conn.prepareStatement("SELECT * FROM Item where type = ?");
				getPriceByCat = conn.prepareStatement("SELECT * FROM Item WHERE (price between ? and ?) and type IN (?)");
				getPriceAll = conn.prepareStatement("SELECT * FROM Item WHERE (price between ? and ?)");
				getSingleItem = conn.prepareStatement("SELECT * FROM Item WHERE itemId = ?");
				removeItem = conn.prepareStatement("DELETE FROM Item WHERE itemId = ?");
				getLastItemId = conn.prepareStatement("Select Auto_Increment FROM information_schema.tables WHERE table_schema = database() and table_name = 'Item'");
				getUsername = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
				getLastTenItems = conn.prepareStatement("Select * from Item order by date desc limit 10");
			}catch (SQLException ex){
				ex.printStackTrace();
			}
		}catch(ClassNotFoundException e){
			System.out.println(e);
		}
	}
	
	/**
	 * Inserts a User object into the database and returns true if successful
	 * @author Edward Killmeier
	 * @param fName
	 * @param lName
	 * @param userName
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public boolean insertUser(String fName, String lName, String userName, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		boolean result = false;
		int userId = getUserLastId();
		User newUser = new User(userId, fName, lName, userName, password, false);
		ResultSet rs;
		String dbUserName = "";
		boolean check = true;
		try {
			checkLogin.setString(1, userName);
			rs = checkLogin.executeQuery();
			while(rs.next()){
				dbUserName = rs.getString("userName");
				if(dbUserName.equalsIgnoreCase(userName)){
					check = false;
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(check){
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
		else{
			return result;
		}
		
	}
	
	/**
	 * Inserts a User object with admin enabled and returns true if successful
	 * @author Edward Killmeier
	 * @param fName
	 * @param lName
	 * @param userName
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
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
	 * gets the next id from the auto increment in the User table
	 * @author Edward Killmeier
	 * @return
	 */
	public int getUserLastId(){
		int lastId = 0;
		ResultSet rs;
		try {
			rs = getUserLastId.executeQuery();
			while(rs.next()){
				//get the last increment int
				lastId = rs.getInt("Auto_Increment");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastId;
	}
	
	/**
	 * gets the next id from the auto increment in the Item table
	 * @author Edward Killmeier
	 * @return
	 */
	public int getLastItemId(){
		int lastId = 0;
		ResultSet rs;
		try {
			rs = getUserLastId.executeQuery();
			while(rs.next()){
				//get the last increment int
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
	 * @author Edward Killmeier
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
	 * @author Edward Killmeier
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
	 * @author Edward Killmeier
	 * @param userId
	 * @param price
	 * @param type
	 * @param description
	 * @param imageLocation
	 */
	public boolean addItem(int userId, double price, String type, String description, String imageLocation, String title){
		boolean result = false;
		try {
			addItem.setInt(1, userId);
			addItem.setDouble(2, price);
			addItem.setString(3, type);
			addItem.setString(4, description);
			addItem.setString(5, imageLocation);
			addItem.setString(6, title);;
			addItem.executeUpdate();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns the user with all their items added to their itemlist
	 * @author Edward Killmeier
	 * @param user
	 * @return
	 */
	public User getUserItems(User user){
		user.removeAllItems();
		ResultSet rs;
		try {
			getUserItems.setInt(1, user.getId());
			rs = getUserItems.executeQuery();
			while(rs.next()){
				int itemId = rs.getInt("itemId");
				int userId = rs.getInt("userId");
				double price = rs.getDouble("price");
				String type = rs.getString("type");
				String description = rs.getString("description");
				String imageLocation = rs.getString("imageLocation");
				Timestamp date = rs.getTimestamp("date");
				String title = rs.getString("title");
				user.addItem(new Item(itemId, userId, price, type, description, imageLocation, date, title));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Updates an item in the database and returns true if successful
	 * @author Edward Killmeier
	 * @param price
	 * @param type
	 * @param description
	 * @param imageLocation
	 * @param title
	 * @param itemId
	 * @return
	 */
	public boolean updateItem(double price, String type, String description, String imageLocation, String title, int itemId){
		boolean result = false;
		try {
			updateItem.setDouble(1, price);
			updateItem.setString(2, type);
			updateItem.setString(3, description);
			updateItem.setString(4, imageLocation);
			updateItem.setString(5, title);
			updateItem.setInt(6, itemId);
			updateItem.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Gets all the items listed in the database
	 * @author Cameron Day
	 */
	public ArrayList<Item> getAllItems(String sortCat, String sortDir, int jtStartIndex, int jtPageSize){
		ArrayList<Item> allItems = new ArrayList<Item>();
		String startIndex = Integer.toString(jtStartIndex);
		String pageSize = Integer.toString(jtPageSize);
		ResultSet rs1;
		try{
			String sql = "SELECT * FROM Item ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
			rs1 = conn.createStatement().executeQuery(sql);
			while(rs1.next()){
				//get all information to create Item object and add to array
				int itemId = rs1.getInt("itemId");
				int userId = rs1.getInt("userId");
				double price = rs1.getDouble("price");
				String type = rs1.getString("type");
				String desc = rs1.getString("description");
				String image = rs1.getString("imageLocation");
				Timestamp date = rs1.getTimestamp("date");
				String title = rs1.getString("title");
				Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
				allItems.add(newItem);				
			}
		}
		catch (SQLException sqle) {
			System.out.println("Exception in allItems: " + sqle.getMessage());
		}
		
		return allItems;
		
	}
	
	/**
	 * Gets all the items that match the search string
	 * @author Cameron Day
	 */
	public ArrayList<Item> getSearchItems(String category, String searchString, String sortCat, String sortDir, int jtStartIndex, int jtPageSize){
		ArrayList<Item> allItems = new ArrayList<Item>();
		Set<String> setWords = null;
		if (searchString != ""){
			setWords = new HashSet<String>();
			if (searchString.indexOf(' ') == -1){
				setWords.add(searchString);
			}
			else{
				String[] words = searchString.split(" ");
				for (String currentWord : words){
					//add meaningful word to set
					if (words.length < 2 || words.equals("and") || words.equals("the") || words.equals("but")){}//do nothing
					else {
						setWords.add(currentWord);
					}
				}
			}
		}
		
		ResultSet rs1;
		try{
			String sql = "SELECT * FROM Item ORDER BY "+sortCat+" "+sortDir;
			rs1 = conn.createStatement().executeQuery(sql);
			if (searchString.equals("")){
				if (category.equals("All Categories")){
					while(rs1.next()){
					//get all information to create Item object and add to array
						allItems = getAllItems(sortCat, sortDir, jtStartIndex, jtPageSize);
					}
				}
				else {
					while(rs1.next()){
						//get all information to create Item object and add to array
						if (rs1.getString("type").equals(category)){
							String desc = rs1.getString("description");
							String title = rs1.getString("title");
							int itemId = rs1.getInt("itemId");
							int userId = rs1.getInt("userId");
							double price = rs1.getDouble("price");
							String type = rs1.getString("type");
							String image = rs1.getString("imageLocation");
							Timestamp date = rs1.getTimestamp("date");
							Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
							allItems.add(newItem);
						}
					}
				}
			}
			else if (category.equals("All Categories")){
				while(rs1.next()){
				//get all information to create Item object and add to array
					String desc = rs1.getString("description");
					String title = rs1.getString("title");
					for (String word : setWords){
						//add item to list if it contains one of the searched words
						if (title != null && (title.contains((CharSequence)word) || desc.contains((CharSequence)word))){
							int itemId = rs1.getInt("itemId");
							int userId = rs1.getInt("userId");
							double price = rs1.getDouble("price");
							String type = rs1.getString("type");
							String image = rs1.getString("imageLocation");
							Timestamp date = rs1.getTimestamp("date");
							Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
							allItems.add(newItem);
						}
					}
				}
			}
			else{
				while(rs1.next()){
					//get all information to create Item object and add to array
					if (rs1.getString("type").equals(category)){
						String desc = rs1.getString("description");
						String title = rs1.getString("title");
						for (String word : setWords){
							//add item to list if it contains one of the searched words
							if (title != null && (title.contains((CharSequence)word) || desc.contains((CharSequence)word))){
								int itemId = rs1.getInt("itemId");
								int userId = rs1.getInt("userId");
								double price = rs1.getDouble("price");
								String type = rs1.getString("type");
								String image = rs1.getString("imageLocation");
								Timestamp date = rs1.getTimestamp("date");
								Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
								allItems.add(newItem);
					
							}
						}
					}
				}
			}
		}
		catch (SQLException sqle) {
			System.out.println("Exception in searchItems: " + sqle.getMessage());
		}
		
		return allItems;
	}

	/**
	* Returns list of items that are between a particular price range
	* @author Cameron Day
	*/
	public ArrayList<Item> getItemsOfPrice(String category, String searchString, double lowPrice, double highPrice, String sortCat, String sortDir, int jtStartIndex, int jtPageSize){
		ArrayList<Item> priceItems = new ArrayList<Item>();
		String startIndex = Integer.toString(jtStartIndex);
		String pageSize = Integer.toString(jtPageSize);
		ResultSet rs1;
		try{
			if (category.equals("All Categories") && searchString.equals("")){
				String sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				rs1 = conn.createStatement().executeQuery(sql);
				while(rs1.next()){
					//Creates an item object
					int itemId = rs1.getInt("itemId");
					int userId = rs1.getInt("userId");
					double price = rs1.getDouble("price");
					String type = rs1.getString("type");
					String desc = rs1.getString("description");
					String image = rs1.getString("imageLocation");
					Timestamp date = rs1.getTimestamp("date");
					String title = rs1.getString("title");
					Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
					priceItems.add(newItem);
				}
			}
			else{
				ArrayList<Item> allItems = getSearchItems(category, searchString, sortCat, sortDir, jtStartIndex, jtPageSize);
				String sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") and type IN ('"+category+"') ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				rs1 = conn.createStatement().executeQuery(sql);
				while(rs1.next()){
					int id = rs1.getInt("itemId");
					Item addItem = null;
					for (int i=0; i<allItems.size(); i++){
					//searches if item is in the list of items from search, and if so adds it to return list
						if (allItems.get(i).getItemId() == id){
							addItem = allItems.get(i);
							allItems.remove(i);
							break;
						}
					}
					if (addItem != null){
						priceItems.add(addItem);
					}
				}
			}
		}
		catch (SQLException sqle) {
			System.out.println("Exception in priceItems: " + sqle.getMessage());
		}
		return priceItems;
	}


	/**
	 * Returns an Item from the database with a specific ID
	 * @author Edward Killmeier
	 * @param itemId
	 */
	public Item getSingleItem(int itemId){
		Item item = null;
		ResultSet rs;
		try {
			getSingleItem.setInt(1, itemId);
			rs = getSingleItem.executeQuery();
			while(rs.next()){
				//gets the values from the Item in the database
				int userId = rs.getInt("userId");
				double price = rs.getDouble("price");
				String type = rs.getString("type");
				String desc = rs.getString("description");
				String image = rs.getString("imageLocation");
				Timestamp date = rs.getTimestamp("date");
				String title = rs.getString("title");
				item = new Item(itemId, userId, price, type, desc, image, date, title);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
	
	/**
	 * Removes an Item from the Item table by itemId
	 * @author Edward Killmeier
	 * @param itemId
	 */
	public void removeItem(int itemId){
		try {
			removeItem.setInt(1, itemId);
			removeItem.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets the total count of items returned by search for pagination
	 * @author Cameron Day
	 * @param category
	 * @param searchString
	 * @param lowPrice
	 * @param highPrice
	 * @return
	 */
	public int getItemCount(String category, String searchString, double lowPrice, double highPrice, String hasPic){
		ArrayList<Item> countList = new ArrayList<Item>();
		ResultSet rs1;
		int count = 0;
		int jtPageSize = 0;
		String sortCat = "date";
		String sortDir = "DESC";
		int jtStartIndex = 0;
		String sql = "select count(*) as count from Item";
		try {
			rs1 = conn.createStatement().executeQuery(sql);
			while(rs1.next()){
				jtPageSize = rs1.getInt("count");
			}
		} catch (SQLException sqle) {
			System.out.println("Exception in itemCount: " + sqle.getMessage());
		}	
		
		if ((lowPrice != -1.0 && highPrice != -1.0)||hasPic.equals("yes")){
			countList = modifySearch(category, searchString, lowPrice, highPrice, sortCat, sortDir, jtStartIndex, jtPageSize, hasPic);
		}
		else {
			countList = getSearchItems(category, searchString, sortCat, sortDir, jtStartIndex, jtPageSize);
		}

		count = countList.size();
		return count;
	}
	
	
	
	
	
	/**
	* Returns list of items that are between a particular price range
	* @author Cameron Day
	*/
	public ArrayList<Item> modifySearch(String category, String searchString, double lowPrice, double highPrice, String sortCat, String sortDir, int jtStartIndex, int jtPageSize, String hasPic){
		ArrayList<Item> priceItems = new ArrayList<Item>();
		String startIndex = Integer.toString(jtStartIndex);
		String pageSize = Integer.toString(jtPageSize);
		String sql="";
		ResultSet rs1;
		try{
			if (category.equals("All Categories") && searchString.equals("")){
				if(lowPrice==-1 || highPrice==-1){
					if (hasPic.equals("no")){
						sql = "SELECT * FROM Item ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
					else if (hasPic.equals("yes")){
						sql = "SELECT * FROM Item WHERE imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
				}
				else if (hasPic.equals("no")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				else if(hasPic.equals("yes")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") and imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				rs1 = conn.createStatement().executeQuery(sql);
				while(rs1.next()){
					int itemId = rs1.getInt("itemId");
					int userId = rs1.getInt("userId");
					double price = rs1.getDouble("price");
					String type = rs1.getString("type");
					String desc = rs1.getString("description");
					String image = rs1.getString("imageLocation");
					Timestamp date = rs1.getTimestamp("date");
					String title = rs1.getString("title");
					Item newItem = new Item(itemId, userId, price, type, desc, image, date, title);
					priceItems.add(newItem);
				}
			}
			else if (category.equals("All Categories")){
				ArrayList<Item> allItems = getSearchItems(category, searchString, sortCat, sortDir, jtStartIndex, jtPageSize);
				if(lowPrice==-1 || highPrice==-1){
					if (hasPic.equals("no")){
						sql = "SELECT * FROM Item ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
					else if (hasPic.equals("yes")){
						sql = "SELECT * FROM Item WHERE imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
				}
				else if (hasPic.equals("no")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				else if(hasPic.equals("yes")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") and imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				rs1 = conn.createStatement().executeQuery(sql);
				while(rs1.next()){
					int id = rs1.getInt("itemId");
					Item addItem = null;
					for (int i=0; i<allItems.size(); i++){
					//searches if item is in the list of items from search, and if so adds it to return list
						if (allItems.get(i).getItemId() == id){
							addItem = allItems.get(i);
							allItems.remove(i);
							break;
						}
					}
					if (addItem != null){
						priceItems.add(addItem);
					}
				}
			}
			else{
				ArrayList<Item> allItems = getSearchItems(category, searchString, sortCat, sortDir, jtStartIndex, jtPageSize);
				if(lowPrice==-1 || highPrice==-1){
					if (hasPic.equals("no")){
						sql = "SELECT * FROM Item WHERE type IN ('"+category+"') ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
					else if (hasPic.equals("yes")){
						sql = "SELECT * FROM Item WHERE type IN ('"+category+"') and imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
					}
				}
				else if (hasPic.equals("no")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") and type IN ('"+category+"') ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				else if(hasPic.equals("yes")){
					sql = "SELECT * FROM Item WHERE (price between "+lowPrice+" and "+highPrice+") and type IN ('"+category+"') and imageLocation is not null and imageLocation != 'noImage.jpg' ORDER BY "+sortCat+" "+sortDir+" limit "+startIndex+", "+pageSize;
				}
				rs1 = conn.createStatement().executeQuery(sql);
				while(rs1.next()){
					int id = rs1.getInt("itemId");
					Item addItem = null;
					for (int i=0; i<allItems.size(); i++){
					//searches if item is in the list of items from search, and if so adds it to return list
						if (allItems.get(i).getItemId() == id){
							addItem = allItems.get(i);
							allItems.remove(i);
							break;
						}
					}
					if (addItem != null){
						priceItems.add(addItem);
					}
				}
			}
		}
		catch (SQLException sqle) {
			System.out.println("Exception in priceItems: " + sqle.getMessage());
		}
		return priceItems;
	}
	
	/**
	 * Returns the Username of a User's id
	 * @author Chris Donaldson
	 * @param id
	 * @return
	 */
	public String getUsername(int id){
		String username = "";
		ResultSet rs;
		try {
			getUsername.setInt(1, id);
			rs = getUsername.executeQuery();
			while(rs.next()){
				username = rs.getString("username");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}
	
	/**
	 * Returns up to the last 10 items inserted/updated on the database
	 * @author Chris Donaldson
	 * @return
	 */
	public ArrayList<Item> getLastTenItems(){
		ArrayList<Item> returnItems = new ArrayList<Item>();
		ResultSet rs10;
		try{
			rs10 = getLastTenItems.executeQuery();
			while(rs10.next()){
				int itemId = rs10.getInt(1);
				int userId = rs10.getInt("userId");
				double price = rs10.getDouble("price");
				String type = rs10.getString("type");
				String desc = rs10.getString("description");
				String image = rs10.getString("imageLocation");
				Timestamp date = rs10.getTimestamp("date");
				String title = rs10.getString("title");			
				returnItems.add(new Item(itemId, userId, price, type, desc, image, date, title));
			}
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnItems;

	}
	
	/**
	 * Returns a list of all the users in the User table
	 * @author Edward Killmeier
	 * @return
	 */
	public User getAllUsers(User admin){
		//ArrayList<User> users = new ArrayList<User>();
		ResultSet rs;
		try {
			rs = getAllUsers.executeQuery();
			while(rs.next()){
				//Gets a user from the User table
				int id = rs.getInt("id");
				String fName = rs.getString("fName");
				String lName = rs.getString("lName");
				String userName = rs.getString("userName");
				byte [] encodedPasswordArray = rs.getBytes("hashed_pw");
				boolean isAdmin = rs.getBoolean("isAdmin");
				String password = "unknown";
				admin.addUsersAsAdmin(new User(id, fName, lName, userName, password, isAdmin));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	
	/**
	 * Returns a list of all the items in the database and adds them to the allItemsList for the admin
	 * @author Edward Killmeier
	 * @return
	 */
	public ArrayList<Item> getAllItemsAdmin(){
		ArrayList<Item> items = new ArrayList<Item>();
		ResultSet rs;
		try {
			rs = getAllItemsAdmin.executeQuery();
			while(rs.next()){
				//Get an item from the Item table
				int itemId = rs.getInt("itemId");
				int userId = rs.getInt("userId");
				double price = rs.getDouble("price");
				String type = rs.getString("type");
				String description = rs.getString("description");
				String imageLocation = rs.getString("imageLocation");
				Timestamp date = rs.getTimestamp("date");
				String title = rs.getString("title");
				items.add(new Item(itemId, userId, price, type, description, imageLocation, date, title));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	/**
	 * Returns a User from the database with the corresponding id
	 * @author Edward Killmeier
	 * @param userId
	 * @return
	 */
	public User getSingleUser(int userId){
		User user = null;
		ResultSet rs;
		try {
			getSingleUser.setInt(1, userId);
			rs = getSingleUser.executeQuery();
			while(rs.next()){
				//Gets a user from the User table
				int id = rs.getInt("id");
				String fName = rs.getString("fName");
				String lName = rs.getString("lName");
				String userName = rs.getString("userName");
				byte [] encodedPasswordArray = rs.getBytes("hashed_pw");
				boolean isAdmin = rs.getBoolean("isAdmin");
				String password = "unknown";
				user = new User(id, fName, lName, userName, password, isAdmin);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Removes a user from the database along with all his/her items
	 * Returns a true if successful/false if not successful
	 * @author Edward Killmeier
	 * @param userId
	 * @return
	 */
	public boolean removeUser(int userId){
		boolean result = false;
		try {
			removeUser.setInt(1, userId);
			removeUser.executeUpdate();
			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
			