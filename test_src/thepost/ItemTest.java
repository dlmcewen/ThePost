package thepost;

import java.sql.Timestamp;

//import org.junit.Before;
//import org.junit.Test;

import junit.framework.TestCase;

public class ItemTest extends TestCase {

	

	//@Test
	public void test() {
		Timestamp time = new Timestamp(400);
		
		Item instance = new Item(1, 88, 12.50, "Car", "Large Car","1truck.jpeg", time, "vehicle");
		assertEquals("Item Id:", 1, instance.getItemId());
		assertEquals("User Id:", 88, instance.getUserId());
		assertEquals("Price:", 12.50, instance.getPrice());
		assertEquals("Type:", "Car", instance.getType());
		assertEquals("Description:", "Large Car", instance.getDescription());
		assertEquals("Image Location:", "images/1truck.jpeg", instance.getImageLocation());
		assertEquals("Title:", "vehicle", instance.getTitle());
		assertEquals("Time:", 400, instance.getDate().getTime());
		
	}

}
