package org.cebp.model;

import java.util.List;
import java.util.concurrent.Semaphore;

public class GameThread extends Thread {
    Semaphore sem;
    String threadName;
    Player player1;
    Player player2;

    public GameThread(Semaphore sem, String threadName) {
        super(threadName);
        this.sem = sem;
        this.threadName = threadName;
        List<Player> currentPlayers = Game.getCurrentPlayers();
        this.player1 = currentPlayers.get(0);
        this.player2 = currentPlayers.get(1);
    }

    @Override
    public void run() {

        if (this.getName().equals("A")) {
            System.out.println("Starting " + threadName);
            try {
                // First, get a permit.
                System.out.println(threadName + " is waiting for a permit.");
                sem.acquire();

                System.out.println(threadName + " gets a permit.");

                player1.printCommonResources();
                Thread.sleep(500);

            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            System.out.println(threadName + " releases the permit.");
            sem.release();
        } else {
            System.out.println("Starting " + threadName);
            try {
                // First, get a permit.
                System.out.println(threadName + " is waiting for a permit.");
                sem.acquire();

                System.out.println(threadName + " gets a permit.");

                City city = new City();
                city.createCity(player2);
                Thread.sleep(500);

            } catch (InterruptedException exc) {
                System.out.println(exc);
            }
            System.out.println(threadName + " releases the permit.");
            sem.release();

        }
    }
}
