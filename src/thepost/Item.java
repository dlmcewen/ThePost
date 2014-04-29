package thepost;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates an item that a user can post
 * 
 * The type can either be: furniture, electronic, book, vehicle, misc.
 * @author Edward Killmeier
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
	private String title;
	private String formatedDate;
	private String formatedPrice;


	/**
	 * Creates an Item object with an itemId, userId, price, and description
	 * @author Edward Killmeier
	 * @param itemId
	 * @param userId
	 * @param price
	 * @param description
	 */
	public Item(int itemId, int userId, double price, String type, String description, String imageLocation, Timestamp date, String title){
		this.itemId = itemId;
		this.userId = userId;
		this.price = price;
		this.type = type;
		this.description = description;
		//this.imageLocation = "/var/lib/tomcat6/webapps/imagesThePost/" + imageLocation;
		this.imageLocation = "images/" + imageLocation;
		this.date = date;
		this.title = title;

		SimpleDateFormat dt = new SimpleDateFormat("MMM dd, yyyy");
		Date dateAdded = new Date(this.date.getTime());
		formatedDate = dt.format(dateAdded);
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		formatedPrice = formatter.format(price);
	}
	
	/**
	 * returns the item id
	 * @author Edward Killmeier
	 * @return
	 */
	public int getItemId(){
		return itemId;
	}
	
	/**
	 * returns the user id
	 * @author Edward Killmeier
	 * @return
	 */
	public int getUserId(){
		return userId;
	}
	
	/**
	 * returns the price of the item
	 * @author Edward Killmeier
	 * @return
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Return the type of the item, i.e. furniture
	 * @author Edward Killmeier
	 * @return
	 */
	public String getType(){
		return type;
	}

	/**
	 * Returns the item's description
	 * @author Edward Killmeier
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * retuns the image location of the item
	 * @author Edward Killmeier
	 * @return
	 */
	public String getImageLocation(){
		return imageLocation;
	}
	
	/**
	 * Returns the date of the item.
	 * @author Edward Killmeier
	 * @return
	 */
	public Timestamp getDate(){
		return date;
	}

	/**
	 * Sets the price of the item
	 * @author Edward Killmeier
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Sets the description of the item
	 * @author Edward Killmeier
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the title of the item
	 * @author Edward Killmeier
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the item
	 * @author Edward Killmeier
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the item's price formatted as $X.XX - String
	 * @author Cameron Day
	 */
	public String getFormatedPrice(){
		return formatedPrice;
	}

	/**
	 * Returns the item's timestamp formated to MM-dd-yyyy - String
	 * @author Cameron Day
	 */
	public String getFormatedDate(){
		return formatedDate;
	}

}
