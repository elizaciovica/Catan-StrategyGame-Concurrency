package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;

public class RabbitMessageResponseDeserializer implements RabbitBodyDeserializer<RabbitMessageResponse>{
    @Override
    public RabbitMessageResponse deserialize(JsonNode jsonNode) {
        RabbitMessageResponse rabbitMessageResponse = new RabbitMessageResponse();
        rabbitMessageResponse.setUuid(jsonNode.get("uuid").asText());
        rabbitMessageResponse.setActionName(jsonNode.get("actionName").asText());
        rabbitMessageResponse.setMessage(jsonNode.get("message").asText());
        rabbitMessageResponse.setSuccess(jsonNode.get("success").asBoolean());
        return rabbitMessageResponse;
    }
}
