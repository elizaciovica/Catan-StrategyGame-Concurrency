package org.cebp.messages;

import com.fasterxml.jackson.databind.JsonNode;
import org.cebp.model.Game;
import org.cebp.rabbit.RabbitMessage;

public class ActionFactory {
    public static IAction construct(RabbitMessage message, Game game) {
        switch (message.getActionName()) {
            case "LoginUserAction": {
                LoginUserAction action = new LoginUserAction();
                action.game = game;
                JsonNode messageData = message.getData();
                action.playerName = messageData.get("playerName").asText(); // jackson json processing
                return action;
            }
//            case "BuildHouseAction": {
//                ...
//                return action;
//            }
            default:
                return null;
        }
    }
}
