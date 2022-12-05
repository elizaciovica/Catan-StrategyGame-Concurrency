package org.cebp.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.cebp.rabbit.RabbitClient;
import org.cebp.rabbit.RabbitMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Client {
    public final RabbitClient rabbitClient = new RabbitClient();

    private String playerName;

    public Client(String playerName) {
        this.playerName = playerName;
        try {
            rabbitClient.initializeConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public void createClient() {
        try {
            rabbitClient.createPlayerQueue(this.playerName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // simulate game
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("playerName", this.playerName);

        RabbitMessage message = new RabbitMessage("LoginUserAction", this.playerName, objectNode);
        List<RabbitMessage> rabbitMessages = List.of(message);
        for (RabbitMessage m : rabbitMessages) {
            try {
                rabbitClient.publishToServer(m);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }
}
