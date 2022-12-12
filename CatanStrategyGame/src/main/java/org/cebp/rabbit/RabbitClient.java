package org.cebp.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitClient {
    private final ObjectMapper objectMapper;
    private final List<String> consumerTags;
    private Connection connection;
    private Channel channel;

    public RabbitClient() {
        this.objectMapper = new ObjectMapper();
        consumerTags = new ArrayList<>();
    }

    public void initializeConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        connection = connectionFactory.newConnection();
        this.channel = connection.createChannel();

        try {
            channel.exchangeDeclare("queue_exchange", "fanout", true);
        } catch (IOException e) {
            System.out.println("unable to declare client_exchange");
        }

        channel.queueDeclare("queue", false, false, false, null);
        channel.queueBind("queue", "queue_exchange", "");
        channel.queuePurge("queue");

        channel.basicQos(1);

    }
    public void publish(RabbitMessage message) throws IOException {
        channel.basicPublish("queue_exchange", "", null, objectMapper.writeValueAsBytes(message.getData()));
    }


    public Object consumeSync() throws IOException, ClassNotFoundException {
        boolean autoAck = false;
        GetResponse response = channel.basicGet("queue", autoAck);
        if (response == null) {
            // No message retrieved.
        } else {
            // AMQP.BasicProperties props = response.getProps();
            // long deliveryTag = response.getEnvelope().getDeliveryTag();
            byte[] body = response.getBody();
            // deserialize and return a message object
            return new RabbitMessage(objectMapper.readTree(body));
        }
        return null;
    }

    public void shutdown() {
        try {
            for (String consumerTag: consumerTags) {
                channel.basicCancel(consumerTag);
            }
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
