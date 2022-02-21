package edu.hsos.set.controller;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import edu.hsos.set.database.Database;
import edu.hsos.set.database.MultiPlayerGameDao;
import edu.hsos.set.database.SinglePlayerGameDao;
import edu.hsos.set.model.MultiPlayerGame;
import edu.hsos.set.model.SinglePlayerGame;

public class DatabaseController {
    /**
     * Loads the games saved in the database.
     *
     * @param context the context
     */
    public void loadGamesFromDatabase(Context context) {
        Database db = Room.databaseBuilder(context, Database.class, "set_database").build();
        AppController appController = AppController.getAppController();

        SinglePlayerGameDao singlePlayerGameDao = db.SinglePlayerGameDao();
        List<SinglePlayerGame> singlePlayerGameList = singlePlayerGameDao.getAll();
        if (singlePlayerGameList.size() > 0) {
            appController.setSinglePlayerGame(singlePlayerGameList.get(0));
        }

        MultiPlayerGameDao multiPlayerGameDao = db.MultiPlayerGameDao();
        List<MultiPlayerGame> multiPlayerGameList = multiPlayerGameDao.getAll();
        if (multiPlayerGameList.size() > 0) {
            appController.setMultiPlayerGame(multiPlayerGameList.get(0));
        }
    }

    /**
     * Saves the games saved in the database and deletes old games.
     *
     * @param context the context
     */
    public void saveGamesInDatabase(Context context) {
        Database db = Room.databaseBuilder(context, Database.class, "set_database").build();
        AppController appController = AppController.getAppController();

        SinglePlayerGameDao singlePlayerGameDao = db.SinglePlayerGameDao();
        List<SinglePlayerGame> singlePlayerGameList = singlePlayerGameDao.getAll();
        for (SinglePlayerGame singlePlayerGame : singlePlayerGameList) {
            singlePlayerGameDao.delete(singlePlayerGame);
        }
        if (appController.singlePlayerGameExists()) {
            singlePlayerGameDao.insertAll(appController.getSinglePlayerGame());
        }

        MultiPlayerGameDao multiPlayerGameDao = db.MultiPlayerGameDao();
        List<MultiPlayerGame> multiPlayerGameList = multiPlayerGameDao.getAll();
        for (MultiPlayerGame multiPlayerGame : multiPlayerGameList) {
            multiPlayerGameDao.delete(multiPlayerGame);
        }
        if (appController.multiPlayerGameExists()) {
            multiPlayerGameDao.insertAll(appController.getMultiPlayerGame());
        }
    }
}
