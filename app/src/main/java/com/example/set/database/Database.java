package com.example.set.database;

import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.set.model.MultiPlayerGame;
import com.example.set.model.SinglePlayerGame;
/**
 * Database class
 * A class representing the database.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@androidx.room.Database(entities = {MultiPlayerGame.class, SinglePlayerGame.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    /**
     * Abstract method returning the multi player game dao.
     *
     * @return the multi player game dao
     */
    public abstract MultiPlayerGameDao MultiPlayerGameDao();

    /**
     * Abstract method returning the single player game dao.
     *
     * @return the single player game dao
     */
    public abstract SinglePlayerGameDao SinglePlayerGameDao();
}
