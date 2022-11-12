package org.cebp.model;

import java.util.HashMap;

import static java.lang.System.exit;

public class WantedResource {

    public WantedResource() {

    }

    public void createResource(Player currentPlayer, Resource wantedResource) {
        HashMap<Resource, Integer> playerResources = currentPlayer.getPlayerResources();
        Game game = new Game();
        HashMap<Resource, Integer> commonResources = game.getCommonResources();
        if(playerResources != null) {
            if (playerResources.get(Resource.SHEEP) < 1
                    || playerResources.get(Resource.GRAIN) < 1
                    || playerResources.get(Resource.STONE) < 1) {
                System.out.println("You don't have enough resources. Please check your resources.");
                //to be implemented => if the player doesn't have enough resources,
                // he will be redirected to the menu actions
            } else {
                removeResourcesInExchangeForAnotherResource(playerResources, commonResources);

                switch (wantedResource) {
                    case BRICK:
                        playerResources.put(Resource.BRICK, playerResources.get(Resource.BRICK) + 1);
                        System.out.println("Brick resource created succesfully!");
                    case WOOD:
                        playerResources.put(Resource.WOOD, playerResources.get(Resource.WOOD) + 1);
                        System.out.println("Wood resource created succesfully!");
                    case SHEEP:
                        playerResources.put(Resource.SHEEP, playerResources.get(Resource.SHEEP) + 1);
                        System.out.println("Sheep resource created succesfully!");
                    case GRAIN:
                        playerResources.put(Resource.GRAIN, playerResources.get(Resource.GRAIN) + 1);
                        System.out.println("Grain resource created succesfully!");
                    case STONE:
                        playerResources.put(Resource.STONE, playerResources.get(Resource.STONE) + 1);
                        System.out.println("Stone resource created succesfully!");
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
}
