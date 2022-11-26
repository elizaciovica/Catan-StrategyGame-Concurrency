package org.cebp.rabbit;


public interface RabbitCallback {
    void onMessage(RabbitMessage message);
}

