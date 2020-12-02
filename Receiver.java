package sist;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

public class Receiver implements MessageListener
{
	TopicConnection connection;
	
	public Receiver(String topicFactory, String topicName) throws Exception
	{
		InitialContext ctx = new InitialContext();
		
		TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(topicFactory);
		connection = connFactory.createTopicConnection();
		
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic t = (Topic) ctx.lookup(topicName);
		
		//TopicSubscriber subscriber = session.createSubscriber(chatTopic, null, true);
		
		//subscriber.setMessageListener(this);
		
		 TopicSubscriber receiver1=session.createSubscriber(t, "(domeniu='opinion') AND (sursa='google') AND (autor='ana')",true);  
         //TopicSubscriber receiver2=session.createSubscriber(t, "(domeniu='Sports') AND (sursa='youtube') AND (autor='george')",true);  
		
         receiver1.setMessageListener(this);
         //receiver2.setMessageListener(this);
         
         Message msg1 = receiver1.receive();
	        
	        Destination replyToDestination = msg1.getJMSReplyTo();
	        MessageProducer replyToMessageProducer = session.createProducer(replyToDestination);
	        Message replyMessage = session.createTextMessage("OK");
	        replyToMessageProducer.send(replyMessage);
         
         connection.start();
	}

	@Override
	public void onMessage(Message message)
	{
//		try
//		{
//			TextMessage textMessage = (TextMessage) message;
//			System.out.println("Received: " + textMessage.getText());
//		}
//		catch (JMSException exception)
//		{
//			exception.printStackTrace();
//		}
		
		try{  
	        TextMessage msg=(TextMessage)message;  
	        String domain = msg.getStringProperty("domeniu");
	        String subdomain = msg.getStringProperty("subdomeniu");
	        String source = msg.getStringProperty("sursa");
	        String author = msg.getStringProperty("autor");
	        String date = msg.getStringProperty("data");
	        System.out.println("following message is received: "+msg.getText());  
	        System.out.println("with : "+domain+", "+subdomain+", "+source+", "+author+", "+date);
	        
	       
	        
	        }catch(JMSException e){System.out.println(e);}  
		
		
	}
	
	public void close() throws JMSException
	{
		connection.close();
	}

}
