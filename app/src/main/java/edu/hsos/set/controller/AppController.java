package edu.hsos.set.controller;

import android.content.Context;

import androidx.room.Room;

import edu.hsos.set.database.Database;
import edu.hsos.set.database.MultiPlayerGameDao;
import edu.hsos.set.database.SinglePlayerGameDao;
import edu.hsos.set.model.MultiPlayerGame;
import edu.hsos.set.model.SinglePlayerGame;
import edu.hsos.set.view.MultiPlayerGameScreen;
import edu.hsos.set.view.SinglePlayerGameScreen;

import java.util.List;

/**
 * The controller class for the whole logic
 * Holds and returns other controllers. Is a singleton.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class AppController {
    /**
     * the instance of the controller for the app
     */
    private static final AppController instance = new AppController();

    /**
     * the controller of the current single player game
     */
    private SinglePlayerGameController singlePlayerGameController;

    /**
     * the controller of the current multi player game
     */
    private MultiPlayerGameController multiPlayerGameController;

    /**
     * the controller of the database
     */
    private DatabaseController databaseController;

    /**
     * Constructor
     * Creates the instance of the database controller
     */
    public AppController() {
        databaseController = new DatabaseController();
    }

    /**
     * Getter
     * Returns the app controller.
     *
     * @return the app controller
     * @author Linus Kurze
     */
    public static AppController getAppController() {
        return instance;
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
     * Returns the database controller.
     *
     * @return database controller
     */
    public DatabaseController getDatabaseController() {
        return databaseController;
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
     * Returns if a single player game exists.
     *
     * @return single player game exists
     */
    public boolean singlePlayerGameExists() {
        return singlePlayerGameController != null && singlePlayerGameController.getGame() != null;
    }

    /**
     * Getter
     * Returns if a multi player game exists.
     *
     * @return multi player game exists
     */
    public boolean multiPlayerGameExists() {
        return multiPlayerGameController != null && multiPlayerGameController.getGame() != null;
    }

    /**
     * Getter
     * Returns the current single player game.
     *
     * @return the current single player game
     */
    public SinglePlayerGame getSinglePlayerGame() {
        return (SinglePlayerGame) singlePlayerGameController.getGame();
    }

    /**
     * Getter
     * Returns the current multi player game.
     *
     * @return the current multi player game
     */
    public MultiPlayerGame getMultiPlayerGame() {
        return (MultiPlayerGame) multiPlayerGameController.getGame();
    }

    /**
     * Setter
     * Sets the current single player game and creates the controller.
     *
     * @param singlePlayerGame the single player game
     */
    void setSinglePlayerGame(SinglePlayerGame singlePlayerGame) {
        singlePlayerGameController = new SinglePlayerGameController(singlePlayerGame, null);
    }

    /**
     * Setter
     * Sets the current multi player game and creates the controller.
     *
     * @param multiPlayerGame the single player game
     */
    void setMultiPlayerGame(MultiPlayerGame multiPlayerGame) {
        multiPlayerGameController = new MultiPlayerGameController(multiPlayerGame, null);
    }
}