package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class RabbitMessage {
    /**
     * acts as a message request, received by the server ***/
    private String uuid;
    private String actionName;
    private String playerName;
    private JsonNode data;

    public RabbitMessage(String actionName, String playerName, JsonNode data) {
        this.uuid = UUID.randomUUID().toString();
        this.actionName = actionName;
        this.data = data;
        this.playerName = playerName;
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

    public void setData(JsonNode data) {
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
