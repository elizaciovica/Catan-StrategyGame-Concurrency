package org.cebp.model;

import java.util.HashMap;

import static java.lang.System.exit;

public class Player {
    private String username;

    private int houses;

    private int cities;

    private int points;

    private HashMap<Resource, Integer> playerResources;

    private HashMap<Resource, Integer> playerResourceToExchange;

    private HashMap<Resource, Integer> playerWantedResource;

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
            System.out.println(Resource.BRICK + " " + Game.getCommonResources().get(Resource.BRICK).toString());
            System.out.println(Resource.WOOD + " " + Game.getCommonResources().get(Resource.WOOD).toString());
            System.out.println(Resource.SHEEP + " " + Game.getCommonResources().get(Resource.SHEEP).toString());
            System.out.println(Resource.GRAIN + " " + Game.getCommonResources().get(Resource.GRAIN).toString());
            System.out.println(Resource.STONE + " " + Game.getCommonResources().get(Resource.STONE).toString());
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

    public void initializePlayerResourceToExchange() {
        playerResourceToExchange = new HashMap<Resource, Integer>();
        playerResourceToExchange.put(Resource.BRICK, 0);
        playerResourceToExchange.put(Resource.WOOD, 0);
        playerResourceToExchange.put(Resource.SHEEP, 0);
        playerResourceToExchange.put(Resource.GRAIN, 0);
        playerResourceToExchange.put(Resource.STONE, 0);
    }

    public void initializePlayerWantedResource() {
        playerWantedResource = new HashMap<Resource, Integer>();
        playerWantedResource.put(Resource.BRICK, 0);
        playerWantedResource.put(Resource.WOOD, 0);
        playerWantedResource.put(Resource.SHEEP, 0);
        playerWantedResource.put(Resource.GRAIN, 0);
        playerWantedResource.put(Resource.STONE, 0);
    }

    //we want to make the resource for exchange of player visible for a possible match
    public void setPlayerOpenToExchange(Resource toExchangeResource, Resource wantedResource) {

        initializePlayerResourceToExchange();
        initializePlayerWantedResource();

        if (playerResources != null) {
            switch (toExchangeResource) {
                case BRICK:
                    if (playerResources.get(Resource.BRICK) > 0) {
                        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) - 1);
                        playerResourceToExchange.put(Resource.BRICK,
                                playerResourceToExchange.get(Resource.BRICK) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more bricks available.");
                        exit(0);
                    }
                case WOOD:
                    if (playerResources.get(Resource.WOOD) > 0) {
                        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
                        playerResourceToExchange.put(Resource.WOOD,
                                playerResourceToExchange.get(Resource.WOOD) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more wood available.");
                        exit(0);
                    }
                case SHEEP:
                    if (playerResources.get(Resource.SHEEP) > 0) {
                        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
                        playerResourceToExchange.put(Resource.SHEEP,
                                playerResourceToExchange.get(Resource.SHEEP) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more sheep available.");
                        exit(0);
                    }
                case GRAIN:
                    if (playerResources.get(Resource.GRAIN) > 0) {
                        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
                        playerResourceToExchange.put(Resource.GRAIN,
                                playerResourceToExchange.get(Resource.GRAIN) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more grain available.");
                        exit(0);
                    }
                case STONE:
                    if (playerResources.get(Resource.STONE) > 0) {
                        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 1);
                        playerResourceToExchange.put(Resource.STONE,
                                playerResourceToExchange.get(Resource.STONE) + 1);
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more stones available.");
                        exit(0);
                    }
            }

            switch (wantedResource) {
                case BRICK:
                    playerWantedResource.put(Resource.BRICK,
                            playerWantedResource.get(Resource.BRICK) + 1);
                case WOOD:
                    playerWantedResource
                            .put(Resource.WOOD,
                                    playerWantedResource.get(Resource.WOOD) + 1);
                case SHEEP:
                    playerWantedResource
                            .put(Resource.SHEEP,
                                    playerWantedResource.get(Resource.SHEEP) + 1);
                case GRAIN:
                    playerWantedResource
                            .put(Resource.GRAIN,
                                    playerWantedResource.get(Resource.GRAIN) + 1);
                case STONE:
                    playerWantedResource
                            .put(Resource.STONE,
                                    playerWantedResource.get(Resource.STONE) + 1);
            }
        }
    }

    public HashMap<Resource, Integer> getPlayerResourceToExchange() {
        return playerResourceToExchange;
    }

    public HashMap<Resource, Integer> getPlayerWantedResource() {
        return playerWantedResource;
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

}
