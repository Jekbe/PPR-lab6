package app;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Network {
    private static final Logger logger = LogManager.getLogger(Network.class);
    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

    public static void sendMessage(Message msg) {
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            Queue queue = session.createQueue("node" + msg.destination);
            MessageProducer producer = session.createProducer(queue);
            TextMessage jmsMessage = session.createTextMessage(msg.content);
            jmsMessage.setIntProperty("source", msg.source); // Ensure the property is set
            producer.send(jmsMessage);
            logger.info("Message sent from " + msg.source + " to " + msg.destination);
        } catch (JMSException e) {
            logger.error("Failed to send message", e);
        }
    }

    public static void registerReceiver(Node node) {
        new Thread(() -> {
            try (Connection connection = connectionFactory.createConnection();
                 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
                connection.start();
                Queue queue = session.createQueue("node" + node.getId());
                MessageConsumer consumer = session.createConsumer(queue);
                while (true) {
                    TextMessage jmsMessage = (TextMessage) consumer.receive();
                    if (jmsMessage != null) {
                        Integer source = jmsMessage.getIntProperty("source");
                        if (source == null) {
                            logger.error("Received message with null source property");
                        } else {
                            node.receiveMessage(new Message(source, node.getId(), jmsMessage.getText()));
                            logger.info("Message received by " + node.getId() + " from " + source);
                        }
                    }
                }
            } catch (JMSException e) {
                logger.error("Failed to receive message", e);
            }
        }).start();
    }

    public static void broadcastMessage(int source, String content) {
        for (Node node : Node.getAllNodes()) {
            if (node.getId() != source) {
                sendMessage(new Message(source, node.getId(), content));
            }
        }
    }
}

