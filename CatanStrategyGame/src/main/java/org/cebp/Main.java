package org.cebp;

import org.cebp.model.Game;
import org.cebp.model.House;
import org.cebp.model.Player;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("ravenclawUser");
        Player player2 = new Player("slytherinUser");
        game.showGameRules();
        game.initializeUsers();
        game.initializeCommonResources();
        try {
            game.loginUser(player1);
            game.loginUser(player2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //player1.printCommonResources();
        //house.createHouse(player1);
        //player1.printPlayerResources();
        //player1.printCommonResources();

        //this can cause thread interference and memory inconsistency
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                player2.printCommonResources();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                House house = new House();
                house.createHouse(player1);
            }
        });

        thread1.start();
        thread2.start();
    }
}
