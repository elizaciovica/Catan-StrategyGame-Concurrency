package org.cebp.model;

import org.cebp.messages.ActionFactory;
import org.cebp.messages.ActionResult;
import org.cebp.messages.IAction;
import org.cebp.rabbit.RabbitCallback;
import org.cebp.rabbit.RabbitMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game {

    //public final RabbitClient rabbitClient = new RabbitClient();

    private static ArrayList<Player> currentPlayers = new ArrayList<>();

    private static final HashMap<Resource, Integer> commonResources = new HashMap<Resource, Integer>();

    private static final Lock gameLock = new ReentrantLock();

    public static final Lock playerLock = new ReentrantLock();

    static ExecutorService service = Executors.newFixedThreadPool(3);

    public Game(ArrayList<Player> players) {
        //this.rabbitClient.initializeConnection();
        currentPlayers = players;
    }

    public void showGameRules() {
        System.out.println("Welcome to Catan!");
        System.out.println("The goal of this game is to reach 10 points. The player who reaches first 10 points wins!");
        System.out.println("You can use resources to build houses, cities and create new resources.");
        System.out.println("The resources are: brick, wood, sheep, grain and stone.");
        System.out.println("1 house = 1 brick + 1 wood + 1 grain + 1 sheep.");
        System.out.println("1 city = 2 grains + 3 stones.");
        System.out.println("1 resource of your choice = 1 sheep + 1 grain + 1 stone.");
        System.out.println("You can also exchange resources between you.");
        System.out.println("\n\n");
    }

    public void initializeCommonResources() {
        commonResources.put(Resource.BRICK, 2);
        commonResources.put(Resource.WOOD, 3);
        commonResources.put(Resource.SHEEP, 1);
        commonResources.put(Resource.GRAIN, 1);
        commonResources.put(Resource.STONE, 2);
    }

    public void loginUser(Player player) throws IOException {
        System.out.println("LOGIN:");
        System.out.println(player.getUsername());
        player.assignInitialResources(4, 4, 4, 4, 4);
        System.out.println("Login successfully" + "\n");
        player.printPlayerResources();
        System.out.println();
    }

    public static HashMap<Resource, Integer> getCommonResources() {
        gameLock.lock();
        try {
            return commonResources;
        } finally {
            gameLock.unlock();
        }
    }

    public void simulate() {
        for(Player player: currentPlayers) {
            service.execute(player);
        }
    }

    public static void stopExecutor() {
        service.shutdownNow();
    }

    public static Lock getPlayerLock() {
        return playerLock;
    }

    public static ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

}
