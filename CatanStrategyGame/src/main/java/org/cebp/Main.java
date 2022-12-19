package org.cebp;


import org.cebp.model.Game;
import org.cebp.rabbit.RabbitClient;
import org.cebp.rabbit.RabbitMessage;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException, ClassNotFoundException {
        RabbitClient rabbitClient = new RabbitClient();
        rabbitClient.initializeConnection();

        RabbitMessage loginRequest1 = new RabbitMessage("loginAction", Game.getRandomName());
        RabbitMessage loginRequest2 = new RabbitMessage("loginAction", Game.getRandomName());
        rabbitClient.publish(loginRequest1);
        rabbitClient.publish(loginRequest2);

        Game game = new Game(2, rabbitClient);

        game.showGameRules();
        game.initializeCommonResources();
        game.simulate();

    }
}