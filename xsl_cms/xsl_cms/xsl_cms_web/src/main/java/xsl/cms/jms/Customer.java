package xsl.cms.jms;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
/**
 * 消费者
 *
 * 负责监听activemq中的消息
 */
@Component
public class Customer implements MessageListener {
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("消息接收 " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

