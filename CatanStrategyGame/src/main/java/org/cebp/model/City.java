package org.cebp.model;

import java.util.HashMap;

import static java.lang.System.exit;

public class City {

    public City() {

    }

    public void createCity(Player currentPlayer) {
        HashMap<Resource, Integer> playerResources = currentPlayer.getPlayerResources();
        HashMap<Resource, Integer> commonResources = Game.getCommonResources();
        if(playerResources != null) {
            if (playerResources.get(Resource.GRAIN) < 2 || playerResources.get(Resource.STONE) < 3) {
                System.out.println("You don't have enough resources. Please check your resources.");
                System.out.println();
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForCity(playerResources, commonResources);
                currentPlayer.increaseNoOfCities();
                currentPlayer.increasePointsForCity();
                System.out.println("City created succesfully!");
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
    }

}
