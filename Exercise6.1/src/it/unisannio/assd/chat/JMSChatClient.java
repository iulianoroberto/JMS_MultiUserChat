package it.unisannio.assd.chat;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.artemis.jms.client.ActiveMQTopicConnectionFactory;

public class JMSChatClient {

	public static void main(String[] args) throws JMSException {
		String uri = "tcp://localhost:61616";	// URI per connettersi al gestore di eventi (event service - Artemis)
		
			// this code could be substituted by a lookup operation in a naming service  
		     TopicConnectionFactory connFactory = new ActiveMQTopicConnectionFactory(uri);
		  
		     TopicConnection connection = connFactory.createTopicConnection();
		   
		     // Sessione come pubblicatore
		     TopicSession sessionPub = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		     // Sessione come sottoscrittore
		     TopicSession sessionSub = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		     
		     Topic myTopic = sessionPub.createTopic("Music");
		     
		     // Publisher e subscriber nello stesso processo
		     TopicPublisher publisher = sessionPub.createPublisher(myTopic);
		     TopicSubscriber subscriber = sessionSub.createSubscriber(myTopic);
		     
		     // Impianto message listener nel publisher
		     MessageListener listener = new MessageListener(){
		    	 public void onMessage(Message msg) {
		    		 System.out.print("Peer: ");
		    		 String strmsg = null;
		    		 try {
		    			 strmsg = ((TextMessage)msg).getText();
		    			 System.out.println(strmsg);
		    		 } catch (JMSException e) {
		    			 e.printStackTrace();
		    		 }
		    	 }
		     };
		     subscriber.setMessageListener(listener);
		     
		     connection.start();
		     
		     // Recupero il messaggio dallo standard input
		     TextMessage msg = sessionPub.createTextMessage();
		     Scanner sc = new Scanner(System.in);
		     String line = null;
		     
		     do {
		    	 System.out.println(" ");
		    	 line = sc.nextLine();
		    	 msg.setText(line);
		    	 publisher.publish(msg);	// Pubblico il messaggio
		   } while(!line.endsWith("."));
		     
		   sessionPub.close();
		   sessionSub.close();
		   connection.close();
		   sc.close();
		}
	
}


