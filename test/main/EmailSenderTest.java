package main;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.EmailSender;

public class EmailSenderTest {
	
	final String str1 = "We will not have any class today so please don't show up.";
	final String str2 = "Please send me you order for Girl Scout Cookies as soon as you can. It is first come first serve and we only have a limited supply!";
	
	EmailSender email1 = new EmailSender("Class", str1);
	EmailSender email2 = new EmailSender("GirlScoutCookies", str2);
	
	//send() and sendAll() work: tested via my own email
	
	@Test
	public void testGetSubject() {
		assertEquals("Class", email1.getSubject());
		assertEquals("GirlScoutCookies", email2.getSubject());
		
		email1.setSubject("Family");
		assertEquals("Family", email1.getSubject());
	}
	
	@Test
	public void testGetBody() {
		
		assertEquals(str1, email1.getBody());
		assertEquals(str2, email2.getBody());
		
		email1.setBody("The class is no longer cancelled.");
		assertEquals("The class is no longer cancelled.", email1.getBody());
		
	}
}