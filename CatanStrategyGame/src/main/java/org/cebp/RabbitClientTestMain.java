package org.cebp;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.cebp.rabbit.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitClientTestMain {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitClient rabbitClient = new RabbitClient();
        rabbitClient.initializeConnection();

        rabbitClient.createPlayerQueue("john");

        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("test", "my-data");

        RabbitMessage message = new RabbitMessage("loginAction", "john", objectNode);
        rabbitClient.publishToServer(message);

        rabbitClient.startConsume("serverQueue", new RabbitCallback<RabbitMessage>() {
            @Override
            public void onMessage(RabbitMessage message) {
                System.out.println("message having: " + message.getUuid() + " " + message.getPlayerName() + " " + message.getActionName() + " " + message.getData().get("test").asText());
            }
        }, RabbitMessageDeserializer.Create());

        rabbitClient.publishToClient("john", RabbitMessageResponse.fromRabbitMessage(message, true, "ok"));

        rabbitClient.startConsume("john", new RabbitCallback<RabbitMessageResponse>() {
            @Override
            public void onMessage(RabbitMessageResponse message) {
                System.out.println(message.toString());
            }
        }, new RabbitMessageResponseDeserializer());

        System.out.println("hello");
        // rabbitClient.shutdown();
        System.out.println("done");
    }

}
