package org.cebp.model;

import org.cebp.rabbit.RabbitClient;
import org.cebp.rabbit.RabbitMessage;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.System.exit;

public class Player{
    private String username;    // used to identify players

    private int houses;

    private int cities;

    private int points;

    private HashMap<Resource, Integer> playerResources;

    private Resource playerResourceToExchange;

    private Resource playerWantedResource;

    private final RabbitClient rabbitClient;

    public Player(String username, RabbitClient rabbitClient) {
        this.username = username;
        this.rabbitClient = rabbitClient;
    }

    public void assignInitialResources(int bricks, int wood, int sheep, int grain, int stones) {
        playerResources = new HashMap<Resource, Integer>();
        playerResources.put(Resource.BRICK, bricks);
        playerResources.put(Resource.WOOD, wood);
        playerResources.put(Resource.SHEEP, sheep);
        playerResources.put(Resource.GRAIN, grain);
        playerResources.put(Resource.STONE, stones);
    }

    public void sendLoginMessage() throws IOException {
        RabbitMessage message = new RabbitMessage("loginAction", this.username);
        this.rabbitClient.publish(message);
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
        System.out.println(this.username + ": you currently have " + this.houses + " houses");
    }

    public void returnNumberOfPlayerCities() {
        System.out.println(this.username + ": you currently have " + this.cities + " cities");
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
                        System.out.println(this.username + ": you don't have any more bricks available.");
                        exit(0);
                    }
                    break;
                case WOOD:
                    if (playerResources.get(Resource.WOOD) > 0) {
                        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
                        playerResourceToExchange = Resource.WOOD;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println(this.username + ": you don't have any more wood available.");
                        exit(0);
                    }
                    break;
                case SHEEP:
                    if (playerResources.get(Resource.SHEEP) > 0) {
                        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
                        playerResourceToExchange = Resource.SHEEP;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println(this.username + ": you don't have any more sheep available.");
                        exit(0);
                    }
                    break;
                case GRAIN:
                    if (playerResources.get(Resource.GRAIN) > 0) {
                        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
                        playerResourceToExchange = Resource.GRAIN;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println(this.username + ": you don't have any more grain available.");
                        exit(0);
                    }
                    break;
                case STONE:
                    if (playerResources.get(Resource.STONE) > 0) {
                        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 1);
                        playerResourceToExchange = Resource.STONE;
                        System.out.println("Exchanged Resource set!");
                    } else {
                        System.out.println(this.username + ": you don't have any more stones available.");
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

    public void tryAction(int step) {
        switch (step) {
            case 1:
                this.createHouse();
                break;
            case 2:
                this.createCity();
                break;
            case 3:
                this.setPlayerOpenToExchange(Resource.BRICK, Resource.WOOD);
                break;
            case 4:
                this.setPlayerOpenToExchange(Resource.WOOD, Resource.BRICK);
                break;
            case 5:
                this.createResource(Resource.WOOD);
                break;
            case 6:
                this.createResource(Resource.GRAIN);
                break;
            case 7:
                this.createResource(Resource.SHEEP);
                break;
            case 8:
                this.createResource(Resource.STONE);
                break;
            case 9:
                this.createResource(Resource.BRICK);
                break;
            //TODO case for exchange confirm
        }
    }

    public void createResource(Resource wantedResource) {
        HashMap<Resource, Integer> playerResources = this.getPlayerResources();
        HashMap<Resource, Integer> commonResources = Game.getCommonResources();
        if(playerResources != null) {
            if (playerResources.get(Resource.SHEEP) < 1
                || playerResources.get(Resource.GRAIN) < 1
                || playerResources.get(Resource.STONE) < 1) {
                System.out.println(this.username + " You don't have enough resources. Please check your resources.");
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForAnotherResource(playerResources, commonResources);

                switch (wantedResource) {
                    case BRICK:
                        if(commonResources.get(Resource.BRICK) > 0) {
                            playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) + 1);
                            commonResources.put(Resource.BRICK, commonResources.get(Resource.BRICK) - 1);
                            System.out.println("Brick resource created successfully for player: " + this.username);
                        } else {
                            System.out.println("There are no more bricks available for player: " + this.username);
                            exit(0);
                        }
                    case WOOD:
                        if(commonResources.get(Resource.WOOD) > 0) {
                            playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) + 1);
                            commonResources.put(Resource.WOOD, commonResources.get(Resource.WOOD) - 1);
                            System.out.println("Wood resource created successfully for player: " + this.username);
                        } else {
                            System.out.println("There is no more wood available for player: " + this.username);
                            exit(0);
                        }
                    case SHEEP:
                        if(commonResources.get(Resource.SHEEP) > 0) {
                            playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) + 1);
                            commonResources.put(Resource.SHEEP, commonResources.get(Resource.SHEEP) - 1);
                            System.out.println("Sheep resource created successfully for player: " + this.username);
                        } else {
                            System.out.println("There are no more sheep available for player: " + this.username);
                            exit(0);
                        }
                    case GRAIN:
                        if(commonResources.get(Resource.GRAIN) > 0) {
                            playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) + 1);
                            commonResources.put(Resource.GRAIN, commonResources.get(Resource.GRAIN) - 1);
                            System.out.println("Grain resource created successfully for player: " + this.username);
                        } else {
                            System.out.println("There is no more grain available.");
                            exit(0);
                        }
                    case STONE:
                        if(commonResources.get(Resource.STONE) > 0) {
                            playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) + 1);
                            commonResources.put(Resource.STONE, commonResources.get(Resource.STONE) - 1);
                            System.out.println("Stone resource created successfully for player: " + this.username);
                        } else {
                            System.out.println("There are no more stones available for player: " + this.username);
                            exit(0);
                        }
                }
            }
        } else {
            exit(0);
        }
    }

    private void removeResourcesInExchangeForAnotherResource(HashMap<Resource, Integer> playerResources,
                                                             HashMap<Resource, Integer> commonResources) {
        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 1);

        commonResources.put(Resource.SHEEP, commonResources.get(Resource.SHEEP) + 1);
        commonResources.put(Resource.GRAIN, commonResources.get(Resource.GRAIN) + 1);
        commonResources.put(Resource.STONE, commonResources.get(Resource.STONE) + 1);
    }

    public void createHouse() {
        HashMap<Resource, Integer> playerResources = this.getPlayerResources();
        HashMap<Resource, Integer> commonResources = Game.getCommonResources();
        if (playerResources != null) {
            if (playerResources.get(Resource.BRICK) < 1
                || playerResources.get(Resource.WOOD) < 1
                || playerResources.get(Resource.GRAIN) < 1
                || playerResources.get(Resource.SHEEP) < 1) {
                System.out.println(this.username + " You don't have enough resources to create a house. Please check your resources.");
                System.out.println();
                exit(0);
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForHouse(playerResources, commonResources);
                this.increaseNoOfHouses();
                this.increasePointsForHouse();
                System.out.println("House created successfully for player " + this.username);
                System.out.println();
            }
        } else {
            exit(0);
        }
    }

    private void removeResourcesInExchangeForHouse(HashMap<Resource, Integer> playerResources,
                                                   HashMap<Resource, Integer> commonResources) {
        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) - 1);
        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);

        synchronized (this) {
            commonResources.put(Resource.BRICK, commonResources.get(Resource.BRICK) + 1);
            commonResources.put(Resource.WOOD, commonResources.get(Resource.WOOD) + 1);
            commonResources.put(Resource.GRAIN, commonResources.get(Resource.GRAIN) + 1);
            commonResources.put(Resource.SHEEP, commonResources.get(Resource.SHEEP) + 1);
            try {
                wait(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
            notifyAll();
        }
    }

    public void createCity() {
        HashMap<Resource, Integer> playerResources = this.getPlayerResources();
        HashMap<Resource, Integer> commonResources = Game.getCommonResources();
        if(playerResources != null) {
            if (playerResources.get(Resource.GRAIN) < 2 || playerResources.get(Resource.STONE) < 3) {
                System.out.println(this.username + " You don't have enough resources to create a city. Please check your resources.");
                System.out.println();
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForCity(playerResources, commonResources);
                this.increaseNoOfCities();
                this.increasePointsForCity();
                System.out.println("City created successfully for player: " + this.username);
                System.out.println();
            }
        } else {
            exit(0);
        }
    }

    private void removeResourcesInExchangeForCity(HashMap<Resource, Integer> playerResources,
                                                  HashMap<Resource, Integer> commonResources) {
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 2);
        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 3);

        commonResources.put(Resource.GRAIN, commonResources.get(Resource.GRAIN) + 2);
        commonResources.put(Resource.STONE, commonResources.get(Resource.STONE) + 3);
    }    public Resource getPlayerResourceToExchange() {
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

}
