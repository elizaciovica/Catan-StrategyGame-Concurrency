package org.cebp.model;

import java.util.HashMap;

import static org.cebp.model.Game.commonResources;

public class Player {
    private String username;
    private HashMap<Resource, Integer> playerResources;

    private int houses;
    private int cities;

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
    public void setPlayerOpenToExchange(HashMap<Resource, Integer> playerResources) {
        // ???
    }
}
