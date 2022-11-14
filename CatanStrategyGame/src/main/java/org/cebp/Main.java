package org.cebp;

import org.cebp.model.Game;
import org.cebp.model.GameThread;
import org.cebp.model.House;
import org.cebp.model.Player;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        Player player1 = new Player("ravenclawUser");
        Player player2 = new Player("slytherinUser");
        Player player3 = new Player("hufflepuffUser");
        game.showGameRules();
        game.initializeUsers();
        game.initializeCommonResources();
        try {
            game.loginUser(player1);
            game.loginUser(player2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //this can cause thread interference and memory inconsistency
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                player3.printCommonResources();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                House house = new House();
                house.createHouse(player1);
            }
        });

        Semaphore sem = new Semaphore(1);

        GameThread mt1 = new GameThread(sem, "A");
        GameThread mt2 = new GameThread(sem, "B");

        // stating threads A and B
        mt1.start();
        mt2.start();

        // waiting for threads A and B
        mt1.join();
        mt2.join();

        thread1.start();
        thread2.start();
    }
}