package com.example.set.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.set.model.MultiPlayerGame;

import java.util.List;

/**
 * Multi Player Dao class
 * A class implementing the database operations for the multi player.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Dao
public interface MultiPlayerGameDao {
    /**
     * Returning all in the database found MultiPlayerGame objects.
     *
     * @return list of MultiPlayerGame objects
     */
    @Query("SELECT * FROM MultiPlayerGame")
    List<MultiPlayerGame> getAll();

    /**
     * Inserts MultiPlayerGame objects into the database.
     *
     * @param multiPlayerGames all MultiPlayerGame objects to insert
     */
    @Insert
    void insertAll(MultiPlayerGame... multiPlayerGames);

    /**
     * Deletes a MultiPlayerGame object from the database.
     *
     * @param multiPlayerGame the MultiPlayerGame object to delete
     */
    @Delete
    void delete(MultiPlayerGame multiPlayerGame);

}
