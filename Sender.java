package sist;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Sender
{
	TopicConnection connection;
	TopicPublisher publisher;
	TopicSession session;
	//String username;
	
	public Sender(String topicFactory, String topicName) throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		
		TopicConnectionFactory connFactory = (TopicConnectionFactory) ctx.lookup(topicFactory);
		connection = connFactory.createTopicConnection();
		
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic chatTopic = (Topic) ctx.lookup(topicName);
		
		publisher = session.createPublisher(chatTopic);
		
		
		
	}
	
	public void close() throws JMSException
	{
		connection.close();
	}
	
	
	
	public void setProperties(TextMessage msg, String domain, String subdomain, String source, String author) throws JMSException
	{
		
		
		msg.setStringProperty("domeniu", domain);
        msg.setStringProperty("subdomeniu", subdomain);
        msg.setStringProperty("sursa", source);
        msg.setStringProperty("autor", author);
        
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        msg.setStringProperty("data", String.valueOf(formatter.format(date)));
	}
	
	public void publishMessage(String text, String domain, String subdomain, String source, String author) throws JMSException
	{
		TextMessage message = session.createTextMessage( text);
		setProperties(message,domain,subdomain,source,author);
		publisher.publish(message);
		
		Destination replyToDestination=session.createTemporaryTopic();
		message.setJMSReplyTo(replyToDestination);
		
	}
}
