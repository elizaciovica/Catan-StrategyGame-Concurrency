package org.cebp;

import org.cebp.client.Client;
import org.cebp.model.Game;
import org.cebp.model.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);

        // server
        Game game = new Game();
        game.showGameRules();
        game.initializeCommonResources();

        service.execute(game);

        // 2 players
        service.execute(new Client("JohnDoe"));
        service.execute(new Client("Batman"));
        service.shutdown();
    }
}