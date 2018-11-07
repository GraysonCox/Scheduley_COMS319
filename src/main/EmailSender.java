package main;

import java.util.*; 
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 

public class EmailSender {
	
	private Session session;
	private String subject;
	private String body;
	
	public EmailSender(String subject, String body) {
		
		this.subject = subject;
		this.body = body;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		session = Session.getDefaultInstance(props,	new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("319proj@gmail.com","scheduley");
				}
		});
	}
	
	public void send(String email) {

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);
			System.out.println("sent to "+email);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendAll(String[] emails) {
		
		for (int i=0; i<emails.length; i++) {
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("from@no-spam.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emails[i]));
				message.setSubject(subject);
				message.setText(body);

				Transport.send(message);
				System.out.println("sent to "+emails[i]);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getBody() {
		return body;
	}
}
