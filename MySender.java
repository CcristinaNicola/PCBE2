package sistemStiri;

import java.io.BufferedReader;  
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.naming.*;  
import javax.jms.*;  

public class MySender 
{  
    public static void main(String[] args) 
    {  
    	
        try  
        {   //Create and start connection  
            InitialContext ctx=new InitialContext();  
            TopicConnectionFactory f=(TopicConnectionFactory)ctx.lookup("myTopicConnectionFactory");  
            TopicConnection con=f.createTopicConnection();  
            con.start();  
            //2) create queue session  
            TopicSession ses=con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
            //3) get the Topic object  
            Topic t=(Topic)ctx.lookup("myTopic");  
            //4)create TopicPublisher object          
            TopicPublisher publisher=ses.createPublisher(t);  
            //5) create TextMessage object  
            TextMessage msg=ses.createTextMessage();  
            //6) write message  
            BufferedReader b=new BufferedReader(new InputStreamReader(System.in));  
            while(true)  
            {  
            	
                System.out.println("Enter Msg, end to terminate:");  
                String s=b.readLine();  
                if (s.equals("end"))  
                    break;
                System.out.println("Enter domain:");  
                String domain=b.readLine(); 
                System.out.println("Enter subdomain:");  
                String subdomain=b.readLine(); 
                System.out.println("Enter source:");  
                String source=b.readLine();
                System.out.println("Enter author:");  
                String author=b.readLine();
                msg.setText(s);  
                msg.setStringProperty("domeniu", domain);
                msg.setStringProperty("subdomeniu", subdomain);
                msg.setStringProperty("sursa", source);
                msg.setStringProperty("autor", author);
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                msg.setStringProperty("data", String.valueOf(formatter.format(date)));
                
                //msg.setJMSType("NewsType = 'Sports' OR NewsType = 'Opinion'");
                 
                //7) send message  
                publisher.publish(msg);  
                System.out.println("Message successfully sent.");  
                
            
                
                //int subscribersCounter = (Integer) GlassFish.getAttribute(org.hornetq:module=JMS, new String("SubscriptionCount"));
                
            }  
            //8) connection close  
            con.close();  
        }catch(Exception e){System.out.println(e);}  
    }  
}  
