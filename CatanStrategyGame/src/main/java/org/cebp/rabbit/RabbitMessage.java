package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.UUID;

public class RabbitMessage {
    /**
     * acts as a message request, received by the server ***/
    private String uuid;
    private String actionName;
    private String playerName;
    private JsonNode data;

    public RabbitMessage(String actionName, String playerName) {
        this.uuid = UUID.randomUUID().toString();
        this.actionName = actionName;
        this.playerName = playerName;

        // create a json body
        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);
        objectNode.put("action", actionName);
        objectNode.put("player-name", playerName);
        objectNode.put("uuid", uuid);

        this.data = objectNode;
    }

    public RabbitMessage(JsonNode data) {
        this.data = data;
        this.uuid = data.path("action").asText();
        this.actionName = data.path("action-name").asText();
        this.playerName = data.path("player-name").asText();
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public JsonNode getData() {
        return data;
    }


    public String getUuid() {
        return uuid;
    }


    @Override
    public String toString() {
        return "RabbitMessage{" +
                "uuid='" + uuid + '\'' +
                ", actionName='" + actionName + '\'' +
                ", playerName='" + playerName + '\'' +
                ", data=" + data +
                '}';
    }
}
