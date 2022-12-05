package org.cebp;

import org.cebp.client.Client;
import org.cebp.model.Game;
import org.cebp.model.Player;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("JohnDoe");
        Player player2 = new Player("Batman");
        players.add(player1);
        players.add(player2);

        // server
        Game game = new Game(players);

        game.showGameRules();
        game.initializeCommonResources();

        // 2 players
        Client client1 = new Client("JohnDoe");
        Client client2 = new Client("Batman");
        client1.createClient();
        client2.createClient();

        service.execute(game);

        service.shutdown();
    }
}