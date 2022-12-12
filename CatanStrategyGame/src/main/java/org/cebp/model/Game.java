package org.cebp.model;

import org.cebp.messages.ActionFactory;
import org.cebp.messages.ActionResult;
import org.cebp.messages.IAction;
import org.cebp.rabbit.RabbitCallback;
import org.cebp.rabbit.RabbitMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Game implements Runnable {

    //public final RabbitClient rabbitClient = new RabbitClient();

    private static ArrayList<Player> currentPlayers = new ArrayList<>();

    private static final HashMap<Resource, Integer> commonResources = new HashMap<Resource, Integer>();

    public Game(ArrayList<Player> players) {
        //this.rabbitClient.initializeConnection();
        this.currentPlayers = players;
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
        player.assignInitialResources(2, 2, 2, 2, 2);
        System.out.println("Login successfully" + "\n");
        player.printPlayerResources();
        System.out.println();
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

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void run() {

    }

//    @Override public void run() {
//        Game gameInstance = this;
//        rabbitClient.startConsume("serverQueue", new RabbitCallback<RabbitMessage>() {
//            @Override
//            public void onMessage(RabbitMessage message) {
//                System.out.println("message having: " + message.getUuid() + " " + message.getPlayerName()
//                                   + " " + message.getActionName() + "\n");
//                IAction action = ActionFactory.construct(message, gameInstance);
//                if (action != null) {
//                    try {
//                        ActionResult actionResult = action.executeAction();
//
//                        System.out.println(actionResult);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//            }
//        }, RabbitMessageDeserializer.Create());
//
//        /* getting the players to do random actions
//        for (Player player : players) {
//            try {
//                this.loginUser(player);
//                player.tryAction(getRandomNumber(1, 5));
//            } catch (IOException e) {
//                throw new RuntimeException(e
//                );
//         */
//
//    }
//
//    public RabbitClient getRabbitClient() {
//        return rabbitClient;
//    }
}
