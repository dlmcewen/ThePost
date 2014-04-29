package thepost;

import java.util.ArrayList;

/**
 * Creates a Bean to store the latest 10 items in the database
 * to show on the homepage
 * @author Edward Killmeier
 *
 */
public class ListBean {
	ArrayList<Item> itemList;
	ArrayList<Item> allItems;
	ThePostDAO dao;
    /**
     * Initialize itemList to be empty
     * @author Edward Killmeier
     */
    public ListBean(){
      itemList = new ArrayList<Item>();
      allItems = new ArrayList<Item>();
      dao = new ThePostDAO();
    }
    /**
     * Returns the list of items
     * @author Edward Killmeier
     */
    public ArrayList<Item> getItemList() {
		itemList = dao.getLastTenItems();
		return itemList;
	}
    
    public ArrayList<Item> getAllItems(){
    	allItems.clear();
    	allItems = dao.getAllItemsAdmin();
    	return allItems;
    }
	
}
