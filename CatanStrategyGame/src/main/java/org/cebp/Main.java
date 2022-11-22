package org.cebp;

import org.cebp.model.Game;
import org.cebp.model.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Player player1 = new Player("ravenclawUser");
        Player player2 = new Player("slytherinUser");
        Player player3 = new Player("hufflepuffUser");
        Set<Player> players = new HashSet<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);

        Game game = new Game(players);
        game.showGameRules();
        game.initializeUsers();
        game.initializeCommonResources();

        ExecutorService service = Executors.newFixedThreadPool(3);
        service.execute(game);
        service.shutdown();


        //        //this can cause thread interference and memory inconsistency
        //        Thread thread1 = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                player3.printCommonResources();
        //            }
        //        });
        //
        //        Thread thread2 = new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                House house = new House();
        //                house.createHouse(player1);
        //            }
        //        });
        //
        //        Semaphore sem = new Semaphore(1);
        //
        //        GameThread mt1 = new GameThread(sem, "A");
        //        GameThread mt2 = new GameThread(sem, "B");
        //
        //        // stating threads A and B
        //        mt1.start();
        //        mt2.start();
        //
        //        // waiting for threads A and B
        //        mt1.join();
        //        mt2.join();
        //
        //        thread1.start();
        //        thread2.start();

        //        player1.setPlayerOpenToExchange(Resource.BRICK, Resource.WOOD);
        //        player3.setPlayerOpenToExchange(Resource.WOOD, Resource.BRICK);
        //        //what to do when the resource is taken the trade is not done
        //        game.confirmAndMadeExchange(player1);
        //        player1.printPlayerResources();
        //        player3.printPlayerResources();
        //        player2.printPlayerResources();
    }
}
