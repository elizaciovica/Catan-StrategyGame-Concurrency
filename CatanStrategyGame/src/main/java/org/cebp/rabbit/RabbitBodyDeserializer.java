package org.cebp.rabbit;

import com.fasterxml.jackson.databind.JsonNode;

public interface RabbitBodyDeserializer<T> {
    public T deserialize(JsonNode jsonNode);
}
