package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.Test;

public class EmailSenderTest {
	
	final String str1 = "We will not have any class today so please don't show up.";
	final String str2 = "Please send me you order for Girl Scout Cookies as soon as you can. It is first come first serve and we only have a limited supply!";
	
	EmailSender email1 = new EmailSender("Class", str1);
	EmailSender email2 = new EmailSender("GirlScoutCookies", str2);
	
	
	//Looking into java.mail testing 
	@Test
	public void testSend() throws MessagingException, IOException {
		//TODO fix
		email1.send("smkazun@iastate.edu");
		
		Session session = Session.getDefaultInstance(new Properties());
		Store store = session.getStore("pop3");
		store.connect("test.com", "test.dest", "pass");
		
		Folder folder = store.getFolder("inbox");
		
		folder.open(Folder.READ_ONLY);
		Message[] msg = folder.getMessages();
		
		assertTrue(msg.length == 1);
		assertEquals("Class", msg[0].getSubject());
		assertEquals(str1, msg[0].getContent());
		folder.close(true);
		store.close();
		
	}
	
	@Test
	public void testSendAll() {
		
	}
	
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
