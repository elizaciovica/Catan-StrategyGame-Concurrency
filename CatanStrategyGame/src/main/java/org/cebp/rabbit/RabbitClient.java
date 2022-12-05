package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;
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
    private Channel serverChannel;

    public RabbitClient() {
        this.objectMapper = new ObjectMapper();
        consumerTags = new ArrayList<>();
    }

    public boolean initializeConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        connection = connectionFactory.newConnection();
        this.serverChannel = connection.createChannel();

        try {
            serverChannel.exchangeDeclare("client_exchange", "direct", true);
        } catch (IOException e) {
            System.out.println("unable to declare client_exchange");
        }

        try {
            serverChannel.exchangeDeclare("server_exchange", "fanout", true);
        } catch (IOException e) {
            System.out.println("unable to declare server_exchange");
        }


        // TODO: make sure the queue is declared passively, e.g. it is created if it does not exist
        serverChannel.queueDeclare("serverQueue", false, false, false, null);
        serverChannel.queueBind("serverQueue", "server_exchange", "");
        serverChannel.queuePurge("serverQueue");

        serverChannel.basicQos(1);
        return true;    // TODO: return true only if the queue creation was successful

    }

    // comunicarea client
    // createPlayerQueue(username) - exchange fanout, queue name poate sa fie username si routing key username
    // consumeFromQueue(queue-name) - serverul consume de pe queue-ul numit server_queue
    //                                iar clientul consuma de pe queue-ul cu username-ul player-ului



    private void publish(String exchangeName, String routingKey, Object message) throws IOException {
        serverChannel.basicPublish(exchangeName, routingKey, null, objectMapper.writeValueAsBytes(message));
    }

    public void createPlayerQueue(String username) throws IOException {
        serverChannel.queueDeclare(username, false, false, false, null);    // signals if the queue creation was successful
        serverChannel.queueBind(username, "client_exchange", username);
        serverChannel.queuePurge(username); // delete all messages from the queue so when restarting the app
    }

    public void publishToClient(String playerName, Object message) throws IOException {
        this.publish("client_exchange", playerName, message);
    }

    public void publishToServer(Object message) throws IOException {
        this.publish("server_exchange", "", message);
    }

    public void startConsume(String queueName, @SuppressWarnings("rawtypes") RabbitCallback callback, @SuppressWarnings("rawtypes") RabbitBodyDeserializer deserializer) {
        try {
            String consumerTag = serverChannel.basicConsume(queueName, true, new DeliverCallback() {
                @Override
                public void handle(String consumerTag, Delivery message) throws IOException {
                    byte[] messageBytes = message.getBody();
                    // deserialize them in a rabbitMessage object
                    JsonNode node = objectMapper.readTree(messageBytes);
                    //noinspection unchecked
                    callback.onMessage(deserializer.deserialize(node));
                }
            }, (consumer -> {   //delivery cancel callback
                System.out.printf("Canceled consumer %s%n", consumer);
            }));
            consumerTags.add(consumerTag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public void shutdown() {
        try {
            for (String consumerTag: consumerTags) {
                serverChannel.basicCancel(consumerTag);
            }
            serverChannel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
