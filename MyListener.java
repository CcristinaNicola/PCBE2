package sistemStiri;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;  

public class MyListener implements MessageListener {  
  
    public void onMessage(Message m) {  
        try{  
        TextMessage msg=(TextMessage)m;  
        String domain = msg.getStringProperty("domeniu");
        String subdomain = msg.getStringProperty("subdomeniu");
        String source = msg.getStringProperty("sursa");
        String author = msg.getStringProperty("autor");
        String date = msg.getStringProperty("data");
        System.out.println("following message is received by: "+msg.getText());  
        System.out.println("with : "+domain+", "+subdomain+", "+source+", "+author+", "+date);
        }catch(JMSException e){System.out.println(e);}  
    }  
}  
