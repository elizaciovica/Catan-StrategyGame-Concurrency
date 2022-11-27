package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;

public class RabbitMessageDeserializer implements RabbitBodyDeserializer<RabbitMessage>{
    @Override
    public RabbitMessage deserialize(JsonNode jsonNode) {
        return new RabbitMessage(jsonNode.get("actionName").asText(), jsonNode.get("playerName").asText(), jsonNode.get("data"));
    }

    public static RabbitMessageDeserializer Create() {
        return new RabbitMessageDeserializer();
    }
}
