package thepost;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

//import org.junit.Before;
//import org.junit.Test;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	
	

	//@Test
	public void test() {
		User instance = null;
		User test = null;
		try{
			instance = new User(1, "James", "Bond", "jBond007", "moneypenny", true);
			test = new User("test", "washington");

			}
			catch(NoSuchAlgorithmException ex){
			}
			catch(UnsupportedEncodingException exc){
					
			}
		
		assertEquals("User Id:", 1, instance.getId());
		assertEquals("FirstName:", "James", instance.getfName());
		assertEquals("LastName:","Bond", instance.getlName());
		assertEquals("UserName:", "jBond007", instance.getUserName());
		assertEquals("Password:", "moneypenny", instance.getPassword());
		assertEquals("Is Admin:", true, instance.getIsAdmin());
		assertEquals("Encrypted Password:", "[-45, -51, 102, -6, 93, -92, 77, -20, 19, -90, 64, -128, 28, -22, 56, -38]", Arrays.toString(instance.getEncodedPasswordBytes()));
		
		
				
		//		System.out.println(Arrays.toString(test.getEncodedPasswordBytes()));
		
		User instance2 = null;
		try{
			instance2 = new User("SantaClaus", "udathohoho");
			}
			catch(NoSuchAlgorithmException ex){
			}
			catch(UnsupportedEncodingException exc){
					
			}
		assertEquals("UserName:", "SantaClaus", instance2.getUserName());
		assertEquals("Password:", "udathohoho", instance2.getPassword());
		
	}
	
	

}
