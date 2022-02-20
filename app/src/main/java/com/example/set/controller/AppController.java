package com.example.set.controller;

import android.content.Context;

import androidx.room.Room;

import com.example.set.database.Database;
import com.example.set.database.MultiPlayerGameDao;
import com.example.set.database.SinglePlayerGameDao;
import com.example.set.model.MultiPlayerGame;
import com.example.set.model.SinglePlayerGame;
import com.example.set.view.MultiPlayerGameScreen;
import com.example.set.view.SinglePlayerGameScreen;

import java.util.List;

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
    private SinglePlayerGameController singlePlayerGameController;

    /**
     * the controller of the current multi player game
     */
    private MultiPlayerGameController multiPlayerGameController;

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
        return singlePlayerGameController != null && singlePlayerGameController.getGame() != null;
    }

    /**
     * Getter
     * Returns the controller of the current multi player game.
     *
     * @return controller of the current multi player game
     */
    public boolean multiPlayerGameExists() {
        return multiPlayerGameController != null && multiPlayerGameController.getGame() != null;
    }

    /**
     * loads the games saved in the database.
     *
     * @param context the context
     */
    public void loadGamesFromDatabase(Context context) {
        Database db = Room.databaseBuilder(context, Database.class, "set_database").build();

        SinglePlayerGameDao singlePlayerGameDao = db.SinglePlayerGameDao();
        List<SinglePlayerGame> singlePlayerGameList = singlePlayerGameDao.getAll();
        if (singlePlayerGameList.size() > 0) {
            singlePlayerGameController = new SinglePlayerGameController(singlePlayerGameList.get(0), null);
        }

        MultiPlayerGameDao multiPlayerGameDao = db.MultiPlayerGameDao();
        List<MultiPlayerGame> multiPlayerGameList = multiPlayerGameDao.getAll();
        if (multiPlayerGameList.size() > 0) {
            multiPlayerGameController = new MultiPlayerGameController(multiPlayerGameList.get(0), null);
        }
    }

    /**
     * saves the games saved in the database.
     *
     * @param context the context
     */
    public void saveGamesInDatabase(Context context) {
        Database db = Room.databaseBuilder(context, Database.class, "set_database").build();

        SinglePlayerGameDao singlePlayerGameDao = db.SinglePlayerGameDao();
        List<SinglePlayerGame> singlePlayerGameList = singlePlayerGameDao.getAll();
        for (SinglePlayerGame singlePlayerGame : singlePlayerGameList) {
            singlePlayerGameDao.delete(singlePlayerGame);
        }
        if (singlePlayerGameExists()) {
            singlePlayerGameDao.insertAll((SinglePlayerGame) singlePlayerGameController.getGame());
        }

        MultiPlayerGameDao multiPlayerGameDao = db.MultiPlayerGameDao();
        List<MultiPlayerGame> multiPlayerGameList = multiPlayerGameDao.getAll();
        for (MultiPlayerGame multiPlayerGame : multiPlayerGameList) {
            multiPlayerGameDao.delete(multiPlayerGame);
        }
        if (multiPlayerGameExists()) {
            multiPlayerGameDao.insertAll((MultiPlayerGame) multiPlayerGameController.getGame());
        }
    }
}