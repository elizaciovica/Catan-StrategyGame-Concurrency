package org.cebp.model;

import java.util.HashMap;

public class City {

    public City() {

    }

    public void createCity(Player currentPlayer) {
        HashMap<Resource, Integer> playerResources = currentPlayer.getPlayerResources();
        if( playerResources.get(Resource.GRAIN) < 2 || playerResources.get(Resource.STONE) < 3) {
            System.out.println("You don't have enough resources. Please check your resources.");
            //to be implemented => if the player doesn't have enough resources,
            // he will be redirected to the menu actions
        } else {
            removeResourcesInExchangeForCity(playerResources);
            System.out.println("City created succesfully!");
        }
    }

    private void removeResourcesInExchangeForCity(HashMap<Resource, Integer> playerResources) {
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 2);
        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) - 3);
    }

}
