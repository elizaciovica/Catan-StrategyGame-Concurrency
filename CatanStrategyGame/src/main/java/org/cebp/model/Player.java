package org.cebp.model;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;
import static org.cebp.model.Game.commonResources;

public class Player {
    private String username;
    private HashMap<Resource, Integer> playerResources;

    private int houses;
    private int cities;

    private int points;

    public Player(String username) {
        this.username = username;
    }

    public void assignInitialResources(int bricks, int wood, int sheep, int grain, int stones) {
        playerResources = new HashMap<Resource, Integer>();
        playerResources.put(Resource.BRICK, bricks);
        playerResources.put(Resource.WOOD, wood);
        playerResources.put(Resource.SHEEP, sheep);
        playerResources.put(Resource.GRAIN, grain);
        playerResources.put(Resource.STONE, stones);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashMap<Resource, Integer> getPlayerResources() {
        return playerResources;
    }

    public void setPlayer_resources(HashMap<Resource, Integer> playerResources) {
        this.playerResources = playerResources;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public void increaseNoOfHouses() {
        this.houses += 1;
    }

    public void increaseNoOfCities() {
        this.cities += 1;
    }

    public void increasePointsForHouse() {
        this.points += 1;
    }

    public void increasePointsForCity() {
        this.points += 2;
    }

    public void printPlayerResources() {
        System.out.println("Player " + this.username + ": You currently have: ");
        System.out.println(Resource.BRICK + " " + playerResources.get(Resource.BRICK).toString());
        System.out.println(Resource.WOOD + " " + playerResources.get(Resource.WOOD).toString());
        System.out.println(Resource.SHEEP + " " + playerResources.get(Resource.SHEEP).toString());
        System.out.println(Resource.GRAIN + " " + playerResources.get(Resource.GRAIN).toString());
        System.out.println(Resource.STONE + " " + playerResources.get(Resource.STONE).toString());
        System.out.println();
    }

    public void printCommonResources() {
        synchronized (this) {
            try {
                wait(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
            System.out.println("Currently, there are available the following resources: ");
            System.out.println(Resource.BRICK + " " + commonResources.get(Resource.BRICK).toString());
            System.out.println(Resource.WOOD + " " + commonResources.get(Resource.WOOD).toString());
            System.out.println(Resource.SHEEP + " " + commonResources.get(Resource.SHEEP).toString());
            System.out.println(Resource.GRAIN + " " + commonResources.get(Resource.GRAIN).toString());
            System.out.println(Resource.STONE + " " + commonResources.get(Resource.STONE).toString());
            System.out.println();
            notifyAll();
        }
    }

    public void returnNumberOfPlayerHouses() {
        System.out.println("You currently have " + this.houses + " houses");
    }

    public void returnNumberOfPlayerCities() {
        System.out.println("You currently have " + this.cities + " cities");
    }

    //we want to make the resource for exchange of player visible for a possible match
    public void setPlayerOpenToExchange(HashMap<Resource, Integer> playerExchangedResources,
                                        HashMap<Resource, Integer> playerWantedResource,
                                        Resource wantedResource) {

        ExchangedResource exchangedResource = new ExchangedResource(playerExchangedResources, playerWantedResource);
        exchangedResource.initializePlayerResourceToExchange();
        exchangedResource.initializePlayerWantedResource();

        if (playerResources != null) {
            switch (wantedResource) {
                case BRICK:
                    if (playerResources.get(Resource.BRICK) > 0) {
                        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) - 1);
                        exchangedResource.getPlayerResourceToExchange()
                                .put(Resource.BRICK, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.BRICK) + 1);
                        exchangedResource.getPlayerWantedResource()
                                .put(Resource.BRICK, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.BRICK) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more bricks available.");
                        exit(0);
                    }
                case WOOD:
                    if (playerResources.get(Resource.WOOD) > 0) {
                        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
                        exchangedResource.getPlayerResourceToExchange()
                                .put(Resource.WOOD, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.WOOD) + 1);
                        exchangedResource.getPlayerWantedResource()
                                .put(Resource.WOOD, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.WOOD) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more wood available.");
                        exit(0);
                    }
                case SHEEP:
                    if (playerResources.get(Resource.SHEEP) > 0) {
                        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
                        exchangedResource.getPlayerResourceToExchange()
                                .put(Resource.SHEEP, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.SHEEP) + 1);
                        exchangedResource.getPlayerWantedResource()
                                .put(Resource.SHEEP, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.SHEEP) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more sheep available.");
                        exit(0);
                    }
                case GRAIN:
                    if (playerResources.get(Resource.GRAIN) > 0) {
                        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
                        exchangedResource.getPlayerResourceToExchange()
                                .put(Resource.GRAIN, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.GRAIN) + 1);
                        exchangedResource.getPlayerWantedResource()
                                .put(Resource.GRAIN, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.GRAIN) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more grain available.");
                        exit(0);
                    }
                case STONE:
                    if (playerResources.get(Resource.STONE) > 0) {
                        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 1);
                        exchangedResource.getPlayerResourceToExchange()
                                .put(Resource.STONE, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.STONE) + 1);
                        exchangedResource.getPlayerWantedResource()
                                .put(Resource.STONE, exchangedResource.getPlayerResourceToExchange()
                                        .get(Resource.STONE) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more stones available.");
                        exit(0);
                    }
            }
        }
    }

    public void confirmExchange(ExchangedResource exchangedResource, Player currentPlayer) {
        ArrayList currentPlayers = Game.getCurrentPlayers();
        for(int i = 0; i < currentPlayers.size(); i++) {
            //todo
        }
    }
}
