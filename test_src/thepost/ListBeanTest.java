package thepost;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class ListBeanTest extends TestCase {


	
	
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
				PreparedStatement clearItemStatement = conn.prepareStatement("delete from Item");
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
	 * Verifies that the listbean accesses item lists, and the last ten items for the main page
	 * @author Christopher Donaldson
	 */
	public void testBeanConstructor() {
	
		ListBean instance = new ListBean();
		ThePostDAO db = null;
		
		try{
			db = new ThePostDAO("postTest");
			
		}
		catch (Exception e){
			e.printStackTrace();
		}	
		db.addItem(1, 12, "misc", "its a tree", "tree.png", "myTree");
		db.addItem(1, 12, "misc", "its a tree", "tree.png", "theothertree");
		
		ArrayList<Item> list1 = instance.getItemList();
		ArrayList<Item> list2 = instance.getAllItems();
		
	//	assertEquals("List1.0:", "A duck", list1.get(list1.size()-1).getTitle());
		//assertEquals("List1.1:", "test", list1.get(list1.size()-1).getTitle());
		assertEquals("List2.0:", "book3", list2.get(list1.size()-1).getTitle());
		
		
//		assertEquals("List2.0:", "", list2.get(0).getTitle());
//		assertEquals("List2.1:", "test", list2.get(1).getTitle());
//		
	}

}
