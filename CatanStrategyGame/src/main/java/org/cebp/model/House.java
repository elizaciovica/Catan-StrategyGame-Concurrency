package org.cebp.model;

import java.util.HashMap;

import static java.lang.System.exit;

public class House {

    public House() {

    }

    public void createHouse(Player currentPlayer) {
        HashMap<Resource, Integer> playerResources = currentPlayer.getPlayerResources();
        Game game = new Game();
        HashMap<Resource, Integer> commonResources = game.getCommonResources();
        if (playerResources != null) {
            if (playerResources.get(Resource.BRICK) < 1
                    || playerResources.get(Resource.WOOD) < 1
                    || playerResources.get(Resource.GRAIN) < 1
                    || playerResources.get(Resource.SHEEP) < 1) {
                System.out.println("You don't have enough resources. Please check your resources.");
                exit(0);
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForHouse(playerResources, commonResources);
                currentPlayer.increaseNoOfHouses();
                System.out.println("House created succesfully!");
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
}
