/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 *
 * @author Purnama
 */
public class MailController {
    
    public MailController(){
        final String username = "project.purnama@gmail.com";
        final String password = "purnama240988";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
                        @Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("purnama.sugianto@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
    }
    
    public MailController(String path){
        final String username = "project.purnama@gmail.com";
        final String password = "purnama240988";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });
        
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.addRecipients(Message.RecipientType.TO, 
                      InternetAddress.parse("purnama.sugianto@gmail.com,wow.purnama@gmail.com"));
            message.setSubject("Testing Subject");
            
            BodyPart messagebodypart2 = new MimeBodyPart();
            messagebodypart2.setText("Test");
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagebodypart2);
            
            BodyPart messagebodypart = new MimeBodyPart();
            DataSource source = new FileDataSource(path);
            messagebodypart.setDataHandler(new DataHandler(source));
            messagebodypart.setFileName(path);
            multipart.addBodyPart(messagebodypart);
            
            message.setContent(multipart);
            
            Transport.send(message);
        }
        catch(MessagingException mex){
            mex.printStackTrace();
        }
    }
    
}
