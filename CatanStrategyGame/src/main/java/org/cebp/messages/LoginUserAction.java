package org.cebp.messages;

import org.cebp.model.Game;
import org.cebp.model.Player;

import java.io.IOException;

// Clientul trimite un mesaj de tip

/**
 * {
 *     actionName: "LoginUserAction"
 *     data: {
 *         "playerName": "myPlayer"
 *     }
 * }
 */

// Serverul primeste mesajul, il deserializeaza intr-un map.

/**
 * in functioe de actionName, cu un switch, returneaza o clasa LoginUserAction si seteaza playerName
 */

// Apoi serverul isi construieste actiunea folosind action factory si apeleaza action-ul pe instanta de game

public class LoginUserAction implements IAction {
    public String playerName;
    public Game game;
    @Override
    public ActionResult executeAction() {
        // the action that is corresponding to the respective kind of message
        try {
            game.loginUser(new Player(playerName));
            return ActionResult.ACTION_RESULT_SUCCESS;
        } catch (IOException e) {
            return ActionResult.ACTION_RESULT_FAILED;
        }
    }

}

