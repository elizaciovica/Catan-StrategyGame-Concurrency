package org.cebp;


import org.cebp.model.Game;
import org.cebp.model.Player;
import org.cebp.rabbit.RabbitClient;
import org.cebp.rabbit.RabbitMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws IOException, TimeoutException, ClassNotFoundException {
        RabbitClient rabbitClient = new RabbitClient();
        rabbitClient.initializeConnection();


        ExecutorService service = Executors.newFixedThreadPool(3);
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("JohnDoe", rabbitClient);   // generate random player method?
        Player player2 = new Player("Batman", rabbitClient);
        players.add(player1);
        players.add(player2);

        player1.sendLoginMessage();     // post login message to server queue
        player2.sendLoginMessage();

        RabbitMessage loginUser1 = (RabbitMessage) rabbitClient.consumeSync();  // read message from queue
        RabbitMessage loginUser2 = (RabbitMessage) rabbitClient.consumeSync();

        String player_name_1 = loginUser1.getPlayerName();  // get player name from message
        String player_name_2 = loginUser2.getPlayerName();



        // server
        Game game = new Game(players);

        game.showGameRules();
        game.initializeCommonResources();

        // 2 players
//        Client client1 = new Client("JohnDoe");
//        Client client2 = new Client("Batman");
//        client1.createClient();
//        client2.createClient();

        service.execute(game);

        service.shutdown();
    }
}