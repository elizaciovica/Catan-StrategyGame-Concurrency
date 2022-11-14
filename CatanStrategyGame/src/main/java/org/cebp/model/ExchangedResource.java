package org.cebp.model;


import java.util.HashMap;

public class ExchangedResource {
    private HashMap<Resource, Integer> playerResourceToExchange;
    private HashMap<Resource, Integer> playerWantedResource;

    public HashMap<Resource, Integer> getPlayerResourceToExchange() {
        return playerResourceToExchange;
    }

    public HashMap<Resource, Integer> getPlayerWantedResource() {
        return playerWantedResource;
    }

    public ExchangedResource(HashMap<Resource, Integer> playerExchangedResources,
                             HashMap<Resource, Integer> playerWantedResource) {

        this.playerResourceToExchange = playerExchangedResources;
        this.playerWantedResource = playerWantedResource;
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
}
