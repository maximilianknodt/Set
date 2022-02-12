package com.example.set.controller;

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
    public void createNewSinglePlayerGame() {
        this.singlePlayerGameController = new SinglePlayerGameController();
    }

    /**
     * Creates a new multi player game.
     */
    public void createNewMultiPlayerGame(String[] players) {
        this.multiPlayerGameController = new MultiPlayerGameController(players);
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
}
