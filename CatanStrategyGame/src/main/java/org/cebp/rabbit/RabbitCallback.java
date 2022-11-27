package org.cebp.rabbit;


public interface RabbitCallback<T> {
    void onMessage(T message);
}

