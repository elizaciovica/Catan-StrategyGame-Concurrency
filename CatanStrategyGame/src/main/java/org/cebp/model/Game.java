package org.cebp.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game {

    public static final ArrayList<String> gameUsers = new ArrayList<>();

    private static ArrayList<Player> currentPlayers = new ArrayList<>();

    private static final HashMap<Resource, Integer> commonResources = new HashMap<Resource, Integer>();
    private Set<Player> players;

    private static final Lock gameLock = new ReentrantLock();

    public static final Lock playerLock = new ReentrantLock();

    public Game(Set<Player> players) {
        this.players = players;
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

    public void initializeUsers() {
        gameUsers.add("gryffindorUser");
        gameUsers.add("hufflepuffUser");
        gameUsers.add("ravenclawUser");
        gameUsers.add("slytherinUser");
    }

    public void initializeCommonResources() {
        commonResources.put(Resource.BRICK, 2);
        commonResources.put(Resource.WOOD, 3);
        commonResources.put(Resource.SHEEP, 1);
        commonResources.put(Resource.GRAIN, 1);
        commonResources.put(Resource.STONE, 2);
    }

    public void loginUser(Player player) throws IOException {
        System.out.println("LOGIN: Enter username");
        System.out.println(player.getUsername());
        if (!gameUsers.contains(player.getUsername()) || player.getUsername() == null || player.getUsername().isEmpty()) {
            System.out.println("This user does not exist! Please login with a different user.");
        } else {
            currentPlayers.add(player);
            player.assignInitialResources(4, 4, 4, 4, 4);
            System.out.println("Login successfully");
            System.out.println();
        }
    }


    public static HashMap<Resource, Integer> getCommonResources() {
        gameLock.lock();
        try {
            return commonResources;
        } finally {
            gameLock.unlock();
        }
    }

    public static ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    static ExecutorService service = Executors.newFixedThreadPool(3);

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
}
