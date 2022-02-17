package com.example.set.controller;

import com.example.set.ui.MultiPlayerGameScreen;
import com.example.set.ui.SinglePlayerGameScreen;

/**
 * The controller class for the whole logic
 * Holds and returns other controllers.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class AppController {
    /**
     * the controller of the current single player game
     */
    SinglePlayerGameController singlePlayerGameController;

    /**
     * the controller of the current multi player game
     */
    MultiPlayerGameController multiPlayerGameController;

    /**
     * Constructor
     * Initializes the single and multi player games. TODO: Read from saved data
     */
    public AppController() {
        singlePlayerGameController = null;
        multiPlayerGameController = null;
    }

    /**
     * Creates a new single player game.
     */
    public void createNewSinglePlayerGame(SinglePlayerGameScreen singlePlayerGameScreen, boolean shortGame) {
        this.singlePlayerGameController = new SinglePlayerGameController(singlePlayerGameScreen, shortGame);
    }

    /**
     * Creates a new multi player game.
     */
    public void createNewMultiPlayerGame(MultiPlayerGameScreen multiPlayerGameScreen, String[] players, boolean shortGame) {
        this.multiPlayerGameController = new MultiPlayerGameController(multiPlayerGameScreen, players, shortGame);
    }

    /**
     * Getter
     * Returns the controller of the current single player game.
     *
     * @return controller of the current single player game
     */
    public SinglePlayerGameController getSinglePlayerGameController() {
        return singlePlayerGameController;
    }

    /**
     * Getter
     * Returns the controller of the current multi player game.
     *
     * @return controller of the current multi player game
     */
    public MultiPlayerGameController getMultiPlayerGameController() {
        return multiPlayerGameController;
    }

    /**
     * Getter
     * Returns the controller of the current single player game.
     *
     * @return controller of the current single player game
     */
    public boolean singlePlayerGameExists() {
        return singlePlayerGameController != null;
    }

    /**
     * Getter
     * Returns the controller of the current multi player game.
     *
     * @return controller of the current multi player game
     */
    public boolean multiPlayerGameExists() {
        return multiPlayerGameController != null;
    }
}
