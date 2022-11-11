package org.cebp;

import org.cebp.model.Game;
import org.cebp.model.Player;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Player player1 = new Player("ravenclawUser");
        Player player2 = new Player("slytherinUser");
        game.showGameRules();
        game.initializeUsers();
        try {
            game.loginUser(player1);
            game.loginUser(player2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
