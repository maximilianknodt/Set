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
     * Loads the games saved in the database.
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
     * Saves the games saved in the database and deletes old games.
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
}