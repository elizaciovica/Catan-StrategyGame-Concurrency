package org.cebp.model;

import java.io.IOException;
import java.util.ArrayList;

public class Game {

    public static final ArrayList<String> gameUsers = new ArrayList<>();

    public Game() {

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
    }

    public void initializeUsers() {
        gameUsers.add("gryffindorUser");
        gameUsers.add("hufflepuffUser");
        gameUsers.add("ravenclawUser");
        gameUsers.add("slytherinUser");
    }

    public void loginUser(Player player) throws IOException {
        ArrayList<Player> currentPlayers = new ArrayList<>();
        System.out.println("LOGIN: Enter username");
        System.out.println(player.getUsername());
        if (!gameUsers.contains(player.getUsername()) || player.getUsername() == null || player.getUsername().isEmpty()) {
            System.out.println("This user does not exist! Please login with a different user.");
        } else {
            currentPlayers.add(player);
            player.assignInitialResources(2, 2, 2, 2, 2);
            System.out.println("Login successfully");
            player.printPlayerResources();
        }

    }
}
