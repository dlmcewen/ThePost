package thepost;

import java.sql.Timestamp;

/**
 * Creates an item that a user can post
 * 
 * The type can either be: furniture, electronic, book, vehicle, misc.
 * @author edkil_000
 *
 */
public class Item {
	
	private int itemId;
	private int userId;
	private double price;
	private String type;
	private String description;
	private String imageLocation;
	private Timestamp date;
	
	/**
	 * Creates an Item object with an itemId, userId, price, and description
	 * @param itemId
	 * @param userId
	 * @param price
	 * @param description
	 */
	public Item(int itemId, int userId, double price, String type, String description, String imageLocation, Timestamp date){
		this.itemId = itemId;
		this.userId = userId;
		this.price = price;
		this.type = type;
		this.description = description;
		this.imageLocation = "images/" + imageLocation;
		this.date = date;
	}
	
	/**
	 * returns the item id
	 * @return
	 */
	public int getItemId(){
		return itemId;
	}
	
	/**
	 * returns the user id
	 * @return
	 */
	public int getUserId(){
		return userId;
	}
	
	/**
	 * returns the price of the item
	 * @return
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Return the type of the item, i.e. furniture
	 * @return
	 */
	public String getType(){
		return type;
	}

	/**
	 * Returns the item's description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * retuns the image location of the item
	 * @return
	 */
	public String getImageLocation(){
		return imageLocation;
	}
	
	/**
	 * Returns the date of the item.
	 * @return
	 */
	public Timestamp getDate(){
		return date;
	}

	/**
	 * Sets the price of the item
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Sets the description of the item
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
