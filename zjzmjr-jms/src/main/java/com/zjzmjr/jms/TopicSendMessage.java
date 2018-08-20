package com.zjzmjr.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Hello world!
 * 
 */
public class TopicSendMessage {
	private ApplicationContext ac = new ClassPathXmlApplicationContext(
			"classpath:META-INF/spring/zjzmjr-jms-config.xml");
	private JmsTemplate jmsTemplate = (JmsTemplate) ac.getBean("jmsTemplate");

	public void send() {

		jmsTemplate.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setText("发送数据++++++++++++发送数据");
				System.out.println("发送数据++++++++++++发送数据");
				return msg;
			}
		});
	}

	public void receive() {
		Message msg = jmsTemplate.receive();
		TextMessage tm = (TextMessage) msg;
		System.out.println("非监听------------------非监听");
		System.out.println(msg);
	}

	public static void main(String[] args) {
		TopicSendMessage topic = new TopicSendMessage();
		topic.send();
		new TopicSendMessage().receive();
	}
}
