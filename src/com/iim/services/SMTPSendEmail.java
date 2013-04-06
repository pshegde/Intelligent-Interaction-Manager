package com.iim.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SMTPSendEmail extends Authenticator{

	private static String mUserName;
	private static String mPassword;
	private static String mHostName;

	/**
	 * sends the contact details, images as attachment and account details of the gmail accounts configured
	 * @param user
	 * @param password
	 * @param hostname
	 * @param recipient
	 * @param body
	 * @param imageList
	 * @param listOfAccounts
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmailTo(String user, String password, String hostname, String recipient,Map<String, String> missedCalls)  throws AddressException, MessagingException{

		mUserName=user;
		mPassword=password;
		mHostName=hostname;

		Properties props = getProperties();

		// this object will handle the authentication
		Session session=Session.getInstance(props,this);
		MimeMessage emailMessage=new MimeMessage(session);
		BodyPart msgBody=new MimeBodyPart();
		MimeMultipart bodyMultipart=new MimeMultipart();


		emailMessage.setFrom(new InternetAddress(mUserName));
		emailMessage.setRecipient(RecipientType.TO, new InternetAddress(recipient));  

		emailMessage.setSubject("You've got a missed call");   
		
		String message = "";
		for(String incomingName:missedCalls.keySet()){
			if(incomingName==null){
				message = message + "\nYou've got a missed call from " + missedCalls.get(incomingName);
			}
			else{
				message = message + "\nYou've got a missed call from " + incomingName + "(" + missedCalls.get(incomingName) +")" ;
			}
		}
		
		emailMessage.setText(message);
		
		bodyMultipart.addBodyPart(msgBody);

		emailMessage.setContent(bodyMultipart);
		Transport transport = session.getTransport("smtp");

		transport.connect(user, password);
		transport.sendMessage(emailMessage, emailMessage.getAllRecipients());

		transport.close();
		System.out.println("sent the message!!");
	}

	private static Properties getProperties(){
		Properties props = new Properties(); 
		props.put("mail.smtp.host", mHostName);   
		props.put("mail.smtp.auth", "true");           
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		return props;
	}

	@Override 
	public PasswordAuthentication getPasswordAuthentication() { 
		return new PasswordAuthentication(mUserName, mPassword); 
	} 
}
