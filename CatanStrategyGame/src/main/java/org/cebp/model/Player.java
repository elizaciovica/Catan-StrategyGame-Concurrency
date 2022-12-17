package org.cebp.model;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.exit;
import static org.cebp.model.Game.playerLock;

public class Player implements Runnable {
    private String username;    // used to identify players

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
        Game.getPlayerLock().lock();
        try {
        System.out.println("Player " + this.username + ": You currently have: ");
        System.out.println(Resource.BRICK + " " + this.playerResources.get(Resource.BRICK).toString());
        System.out.println(Resource.WOOD + " " + this.playerResources.get(Resource.WOOD).toString());
        System.out.println(Resource.SHEEP + " " + this.playerResources.get(Resource.SHEEP).toString());
        System.out.println(Resource.GRAIN + " " + this.playerResources.get(Resource.GRAIN).toString());
        System.out.println(Resource.STONE + " " + this.playerResources.get(Resource.STONE).toString());
        System.out.println();
        } finally {
            playerLock.unlock();
        }
    }

    public void printCommonResources() {
        Game.getPlayerLock().lock();
        try {
            System.out.println("Currently, there are available the following resources: ");
            System.out.println(Resource.BRICK + " " + Game.getCommonResources().get(Resource.BRICK).toString());
            System.out.println(Resource.WOOD + " " + Game.getCommonResources().get(Resource.WOOD).toString());
            System.out.println(Resource.SHEEP + " " + Game.getCommonResources().get(Resource.SHEEP).toString());
            System.out.println(Resource.GRAIN + " " + Game.getCommonResources().get(Resource.GRAIN).toString());
            System.out.println(Resource.STONE + " " + Game.getCommonResources().get(Resource.STONE).toString());
            System.out.println();
        } finally {
            playerLock.unlock();
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

        if (this.playerResources != null) {
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

        AtomicReference<Boolean> exchangeMade = new AtomicReference<>(false);
        AtomicReference<String> exchangePartner = new AtomicReference<>(null);
        //accepting the exchange to a possible match
        Game.getCurrentPlayers().forEach(player -> {
            if (!this.equals(player)) {
                //if the resource the player wants to give is equal to the resource the other player wants to get and
                //if the resource the player wants to get is equal to the resource the other player wants to give
                //then the exchange is made
                if (wantedResource == player.getPlayerResourceToExchange()
                    && toExchangeResource == player.getPlayerWantedResource()) {
                    this.getPlayerResources().put(wantedResource,
                                                  this.getPlayerResources().get(wantedResource) + 1);
                    player.getPlayerResources().put(toExchangeResource, player.getPlayerResources().get(toExchangeResource) + 1);
                    exchangeMade.set(true);
                    exchangePartner.set(player.getUsername());
                }
            }
        });

        if (exchangeMade.get()){
            System.out.println(this.getUsername() + " exchanged " + wantedResource + " for " + toExchangeResource + " with " + exchangePartner.get());
        }
    }

    public void createResource(Resource wantedResource) {
        HashMap<Resource, Integer> playerResources = this.getPlayerResources();
        if(playerResources != null) {
            if (playerResources.get(Resource.SHEEP) < 1
                || playerResources.get(Resource.GRAIN) < 1
                || playerResources.get(Resource.STONE) < 1) {
                System.out.println(this.username + " You don't have enough resources to create " +
                                   wantedResource + ". Please check your resources.");
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                playerLock.lock();
                try {

                    HashMap<Resource, Integer> commonResources = Game.getCommonResources();
                    removeResourcesInExchangeForAnotherResource(playerResources, commonResources);

                    switch (wantedResource) {
                        case BRICK:
                            if (commonResources.get(Resource.BRICK) > 0) {
                                playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) + 1);
                                commonResources.put(Resource.BRICK, commonResources.get(Resource.BRICK) - 1);
                                System.out.println("Brick resource created successfully for player: " + this.username);
                            } else {
                                System.out.println("There are no more bricks available for player: " + this.username);
                                //todo get common resources available
                            }
                        case WOOD:
                            if (commonResources.get(Resource.WOOD) > 0) {
                                playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) + 1);
                                commonResources.put(Resource.WOOD, commonResources.get(Resource.WOOD) - 1);
                                System.out.println("Wood resource created successfully for player: " + this.username);
                            } else {
                                System.out.println("There is no more wood available for player: " + this.username);
                                //todo get common resources available
                            }
                    }
                } finally {
                    playerLock.unlock();
                }
            }
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
        if (playerResources != null) {
            if (playerResources.get(Resource.BRICK) < 1
                || playerResources.get(Resource.WOOD) < 1
                || playerResources.get(Resource.GRAIN) < 1
                || playerResources.get(Resource.SHEEP) < 1) {
                System.out.println(this.username + " You don't have enough resources to create a house. Please check your resources.");
                System.out.println();
                //todo if grain or sheep are not enough => made possible exchange ???
            } else {
                playerLock.lock();
                try {
                    HashMap<Resource, Integer> commonResources = Game.getCommonResources();
                    removeResourcesInExchangeForHouse(playerResources, commonResources);
                    this.increaseNoOfHouses();
                    this.increasePointsForHouse();
                    System.out.println("House created successfully for player " + this.username);
                    System.out.println("Player " + this.username + " you currently have: " + this.points + " points.");
                    this.printPlayerResources();
                    System.out.println("Player " + this.username + " you have " + this.houses + " houses");
                    System.out.println("Player " + this.username + " you have " + this.cities + " cities");
                    System.out.println();

                    //just to test it
                    //todo check for the points
                    if (this.points >= 20) {
                        System.out.println("Player: " + this.username + " won the Game");
                        Game.stopExecutor();
                        System.exit(0);
                    }
                } finally {
                    playerLock.unlock();
                }
            }
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
        playerLock.lock();
        try {
            HashMap<Resource, Integer> playerResources = this.getPlayerResources();
            HashMap<Resource, Integer> commonResources = Game.getCommonResources();
            if (playerResources != null) {
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
                    System.out.println("Player " + this.username + " you currently have: " + this.points + " points");
                    this.printPlayerResources();
                    System.out.println("Player " + this.username + " you have " + this.houses + " houses");
                    System.out.println("Player " + this.username + " you have " + this.cities + " cities");
                    System.out.println();

                    //just to test it
                    //todo check for the points
                    if (this.points >= 20) {
                        System.out.println("Player: " + this.username + " won the Game");
                        Game.stopExecutor();
                        System.exit(0);
                    }
                }
            }
        } finally {
            playerLock.unlock();
        }
    }

    private void removeResourcesInExchangeForCity(HashMap<Resource, Integer> playerResources,
                                                  HashMap<Resource, Integer> commonResources) {
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 2);
        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 3);

        commonResources.put(Resource.GRAIN, commonResources.get(Resource.GRAIN) + 2);
        commonResources.put(Resource.STONE, commonResources.get(Resource.STONE) + 3);
    }

    //method to get a random number so we can get a random action in the switch
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void validatePlayer() {
        if(this.playerResources.get(Resource.BRICK) < 1 ||
           this.playerResources.get(Resource.WOOD) < 1 ||
           this.playerResources.get(Resource.SHEEP) < 1 ||
           this.playerResources.get(Resource.STONE) < 1 ||
           this.playerResources.get(Resource.GRAIN) < 1) {
            Game.stopExecutor();
        }
    }

    @Override public void run() {
        System.out.println("Executing thread " + Thread.currentThread().getName() + " for player " + this.getUsername());
        try {
            while (true) {
                int step = getRandomNumber(1, 6);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                switch (step) {
                    case 1:
                        this.createHouse();

                    case 2:
                        this.createCity();

                    case 3:
                        //todo maybe sync??
                        if (this.playerResources.get(Resource.GRAIN) < 3 ||
                            this.playerResources.get(Resource.SHEEP) < 2 ||
                            this.playerResources.get(Resource.STONE) < 3) {
                            System.out.println("!!! JACKPOT !!!");
                            System.out.println("Bonus Resources for player " + this.username);
                            System.out.println("You rolled the dice really well");
                            this.playerResources.put(Resource.GRAIN, this.playerResources.get(Resource.GRAIN) + 1);
                            this.playerResources.put(Resource.SHEEP, this.playerResources.get(Resource.SHEEP) + 1);
                            this.playerResources.put(Resource.STONE, this.playerResources.get(Resource.STONE) + 1);
                            this.printPlayerResources();
                        }
                        break;

                    case 4:
                        if (this.playerResources.get(Resource.WOOD) < 0) {
                            this.createResource(Resource.WOOD);
                        } else {
                            continue;
                        }
                        break;

                    case 5:
                        if (this.playerResources.get(Resource.BRICK) < 0) {
                            this.createResource(Resource.BRICK);
                        } else {
                            continue;
                        }
                        break;

                    case 6:
                        System.out.println("setting player " + this.getUsername() + " open to exchange, B, W");
                        this.setPlayerOpenToExchange(Resource.BRICK, Resource.WOOD);
                        break;
                    case 7:
                        System.out.println("setting player " + this.getUsername() + " open to exchange, W, B");
                        this.setPlayerOpenToExchange(Resource.WOOD, Resource.BRICK);
                        break;
                }
            }
        } catch (Exception exp) {
            System.out.println("All the threads have been interrupted: " + exp);
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

    public HashMap<Resource, Integer> getPlayerResources() {
        return playerResources;
    }

    public void setUsername(String username) {
        this.username = username;
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
