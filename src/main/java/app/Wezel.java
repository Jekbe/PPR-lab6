package app;

import jakarta.jms.*;
import lombok.Getter;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Wezel implements MessageListener {
    @Getter
    private String id;
    private boolean zainicjowany = false;
    private final Connection connection;
    private final Session session;
    private final MessageProducer messageProducer;
    private MessageConsumer messageConsumer;

    public Wezel(String id) throws JMSException {
        this.id = id;
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(session.createQueue(id));
        messageConsumer.setMessageListener(this);
        connection.start();
    }

    @Override
    public void onMessage(Message message) {
        try {
            String wiadomosc = ((TextMessage) message).getText();

            if(wiadomosc.equals("START")){

            }

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void startEcho() throws JMSException {
        if (!zainicjowany) {
            zainicjowany = true;
            wyslijEcho("START", null);
        }
        }


    public void wyslijEcho(String wiadomosc, String odpowiedzDo) throws JMSException {
        TextMessage textMessage = session.createTextMessage(wiadomosc);
        if (odpowiedzDo != null) textMessage.setStringProperty("odpowiedzDo", odpowiedzDo);
        messageProducer.send(session.createQueue("koordynator"), textMessage);
    }

    public void koniec() throws JMSException {
        session.close();
        connection.close();
    }
}
