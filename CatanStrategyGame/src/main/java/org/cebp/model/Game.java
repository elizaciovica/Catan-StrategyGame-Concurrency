package org.cebp.model;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Game implements Runnable {

    public static final ArrayList<String> gameUsers = new ArrayList<>();

    private static ArrayList<Player> currentPlayers = new ArrayList<>();

    private static final HashMap<Resource, Integer> commonResources = new HashMap<Resource, Integer>();
    private Set<Player> players;
    //
    //    public void simulate() {
    //        players.add(new Player("ravenclawUser"));
    //        players.add(new Player("slytherinUser"));
    //        players.add(new Player("hufflepuffUser"));
    //
    //        List<Player> list = new ArrayList<>(players);
    //        int randIdx = new Random().nextInt(players.size());
    //
    //        Player randomPlayer = list.get(randIdx);
    //        try {
    //            this.loginUser(randomPlayer);
    //        } catch (IOException e) {
    //            throw new RuntimeException(e);
    //        }
    //    }

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
            player.assignInitialResources(2, 2, 2, 2, 2);
            System.out.println("Login successfully");
            System.out.println();
        }
    }

    public static void confirmAndMadeExchange(Player currentPlayer) {
        Resource resourceToExchange = currentPlayer.getPlayerResourceToExchange();
        Resource wantedResource = currentPlayer.getPlayerWantedResource();
        currentPlayers.forEach(player -> {
            if (!currentPlayer.equals(player)) {
                //if the resource the player wants to give is equal to the resource the other player wants to get and
                //if the resource the player wants to get is equal to the resource the other player wants to give
                //then the exchange is made
                if (wantedResource == player.getPlayerResourceToExchange()
                    && resourceToExchange == player.getPlayerWantedResource()) {
                    currentPlayer.getPlayerResources().put(wantedResource,
                                                           currentPlayer.getPlayerResources().get(wantedResource) + 1);
                    player.getPlayerResources().put(resourceToExchange, player.getPlayerResources().get(resourceToExchange) + 1);
                }
            }
        });
    }

    public static HashMap<Resource, Integer> getCommonResources() {
        return commonResources;
    }

    public static ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    @Override public void run() {
        // while true,
        // citesc mesaje de pe rabbit,
        // folosesc factory sa construiesc iaction
        // aplez execute action pt fiecare mesaj
        // trimit raspuns la client

//        Server server = new Server();
//        server.serverStartProcessingMessages(message -> {
//            IAction action = actonFactory.construct(message);
//            action.executeAction();
//        });
        // while tru is running
        for (Player player : players) {
            try {
                this.loginUser(player);
            } catch (IOException e) {
                throw new RuntimeException(e
                );
            }
            player.printPlayerResources();
        }
    }
}
