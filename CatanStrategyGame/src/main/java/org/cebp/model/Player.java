package org.cebp.model;

import java.util.HashMap;

import static java.lang.System.exit;

public class Player implements Runnable {
    private String username;

    private int houses;

    private int cities;

    private int points;

    private HashMap<Resource, Integer> playerResources;

    private Resource playerResourceToExchange;

    private Resource playerWantedResource;

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
        System.out.println(Resource.BRICK + " " + this.playerResources.get(Resource.BRICK).toString());
        System.out.println(Resource.WOOD + " " + this.playerResources.get(Resource.WOOD).toString());
        System.out.println(Resource.SHEEP + " " + this.playerResources.get(Resource.SHEEP).toString());
        System.out.println(Resource.GRAIN + " " + this.playerResources.get(Resource.GRAIN).toString());
        System.out.println(Resource.STONE + " " + this.playerResources.get(Resource.STONE).toString());
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


    //we want to make the resource for exchange of player visible for a possible match
    public void setPlayerOpenToExchange(Resource toExchangeResource, Resource wantedResource) {

        if (playerResources != null) {
            switch (toExchangeResource) {
                case BRICK:
                    if (playerResources.get(Resource.BRICK) > 0) {
                        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) - 1);
                        playerResourceToExchange = Resource.BRICK;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more bricks available.");
                        exit(0);
                    }
                    break;
                case WOOD:
                    if (playerResources.get(Resource.WOOD) > 0) {
                        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
                        playerResourceToExchange = Resource.WOOD;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more wood available.");
                        exit(0);
                    }
                    break;
                case SHEEP:
                    if (playerResources.get(Resource.SHEEP) > 0) {
                        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
                        playerResourceToExchange = Resource.SHEEP;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more sheep available.");
                        exit(0);
                    }
                    break;
                case GRAIN:
                    if (playerResources.get(Resource.GRAIN) > 0) {
                        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
                        playerResourceToExchange = Resource.GRAIN;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more grain available.");
                        exit(0);
                    }
                    break;
                case STONE:
                    if (playerResources.get(Resource.STONE) > 0) {
                        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 1);
                        playerResourceToExchange = Resource.STONE;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println("You don't have any more stones available.");
                        exit(0);
                    }
                    break;
            }

            switch (wantedResource) {
                case BRICK:
                    playerWantedResource = Resource.BRICK;
                    break;
                case WOOD:
                    playerWantedResource = Resource.WOOD;
                    break;
                case SHEEP:
                    playerWantedResource = Resource.SHEEP;
                    break;
                case GRAIN:
                    playerWantedResource = Resource.GRAIN;
                    break;
                case STONE:
                    playerWantedResource = Resource.STONE;
                    break;
            }
        }
    }

    public Resource getPlayerResourceToExchange() {
        return playerResourceToExchange;
    }

    public Resource getPlayerWantedResource() {
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

    @Override public void run() {

    }
}
