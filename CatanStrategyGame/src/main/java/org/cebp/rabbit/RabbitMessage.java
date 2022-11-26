package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;

public class RabbitMessage {
    private String actionName;
    private String playerName;
    private JsonNode messageData;

    public RabbitMessage(String actionName, String playerName, JsonNode messageData) {
        this.actionName = actionName;
        this.messageData = messageData;
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

    public JsonNode getMessageData() {
        return messageData;
    }

    public void setMessageData(JsonNode messageData) {
        this.messageData = messageData;
    }
}
