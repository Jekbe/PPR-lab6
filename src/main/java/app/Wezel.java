package app;

import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Wezel implements MessageListener {
    private String id;
    private boolean zainicjowany = false;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;
    private MessageConsumer messageConsumer;

    public Wezel(String id) throws JMSException {
        this.id = id;
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(session.createQueue(id));
        messageConsumer.setMessageListener(this);
        connection.start();
    }

    public String getId(){
        return id;
    }

    @Override
    public void onMessage(Message message) {

    }

    public void startEcho() throws JMSException {
        if (!zainicjowany){
            zainicjowany = true;
            wyslijEhcho("START", null);
        }
    }

    public void wyslijEhcho(String wiadomosc, String odpowiedzDo) throws JMSException {
        TextMessage textMessage = session.createTextMessage(wiadomosc);
        if (odpowiedzDo != null) textMessage.setStringProperty("odpowiedzDo", odpowiedzDo);
        messageProducer.send(session.createQueue("koordynator"), textMessage);
    }

    public void koniec() throws JMSException {
        session.close();
        connection.close();
    }
}
