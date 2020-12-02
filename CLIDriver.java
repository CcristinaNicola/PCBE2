package sist;

import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.util.Scanner;

public class CLIDriver
{
	
	public static void main(String[] args) throws Exception
	{
		
		Receiver receiver = new Receiver("myTopicConnectionFactory", "myTopic");
		Sender publisher = new Sender("myTopicConnectionFactory", "myTopic");
		
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
			
//			if(line.equalsIgnoreCase("exit"))
//			{
//				scanner.close();
//				sender.close();
//				receiver.close();
//				System.exit(0);
//			}
			
            //publisher.setProperties(s, domain, subdomain, source, author);
             
             publisher.publishMessage(s,domain,subdomain,source,author);  
             System.out.println("Message successfully sent.");
			
			
		}
	}
}
