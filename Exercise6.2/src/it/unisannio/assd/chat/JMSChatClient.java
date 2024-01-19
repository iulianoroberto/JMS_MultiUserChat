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
		String url = "tcp://localhost:61616";
		TopicConnectionFactory factory = new ActiveMQTopicConnectionFactory(url);
		TopicConnection connection = factory.createTopicConnection();
		TopicSession sessionPub = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSession sessionSub = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		     
		Topic topic = sessionPub.createTopic("Music");
		TopicPublisher publisher = sessionPub.createPublisher(topic);
		
		String selector = "SubTopic = 'Jazz'";
		
		TopicSubscriber subscriber = sessionSub.createSubscriber(topic, selector, true); //<-- specifica del selettore
		     
		     
		MessageListener listener = new MessageListener() {
			public void onMessage(Message msg) {
		    	System.out.print("Peer: ");
		    	String strmsg = null;
		    	try {
		    		strmsg = ((TextMessage)msg).getText();;
		    		System.out.println(strmsg);
		    	} catch (JMSException e) {
		    		e.printStackTrace();
		    	}
		    }
		   };
		   subscriber.setMessageListener(listener);
		     
		   connection.start();
		     
		   TextMessage msg = sessionPub.createTextMessage();

		   Scanner sc = new Scanner(System.in);
		   String line = null;
		     
		   do {
		    System.out.print(" ");
		    line = sc.nextLine();
		    msg.setStringProperty("SubTopic", "Jazz");
		    msg.setText(line);
		    publisher.publish(msg);
		   } while(!line.endsWith("."));
		     
		   sessionPub.close();
		   sessionSub.close();
		   connection.close();
		   sc.close();
		}
	
}


