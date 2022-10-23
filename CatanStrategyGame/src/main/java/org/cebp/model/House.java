package org.cebp.model;

import java.util.HashMap;

public class House {

    public House() {

    }

    public void createHouse(Player currentPlayer) {
        HashMap<Resource, Integer> playerResources = currentPlayer.getPlayerResources();
        if(playerResources.get(Resource.BRICK) < 1
                || playerResources.get(Resource.WOOD) < 1
                || playerResources.get(Resource.GRAIN) < 1
                || playerResources.get(Resource.SHEEP) < 1) {
            System.out.println("You don't have enough resources. Please check your resources.");
            //to be implemented => if the player doesn't have enough resources,
            // he will be redirected to the menu actions
        } else {
            removeResourcesInExchangeForHouse(playerResources);
            System.out.println("House created succesfully!");
        }
    }

    private void removeResourcesInExchangeForHouse(HashMap<Resource, Integer> playerResources) {
        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) - 1);
        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) - 1);
        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) - 1);
        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) - 1);
    }


}
