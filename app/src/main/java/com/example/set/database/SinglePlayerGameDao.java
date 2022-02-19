package com.example.set.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.set.model.SinglePlayerGame;

import java.util.List;

/**
 * Single Player Dao class
 * A class implementing the database operations for the single player.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Dao
public interface SinglePlayerGameDao {
    /**
     * Returning all in the database found SinglePlayerGame objects.
     *
     * @return list of SinglePlayerGame objects
     */
    @Query("SELECT * FROM SinglePlayerGame")
    List<SinglePlayerGame> getAll();

    /**
     * Inserts SinglePlayerGame objects into the database.
     *
     * @param singlePlayerGames all SinglePlayerGame objects to insert
     */
    @Insert
    void insertAll(SinglePlayerGame... singlePlayerGames);

    /**
     * Deletes a SinglePlayerGame object from the database.
     *
     * @param singlePlayerGame the SinglePlayerGame object to delete
     */
    @Delete
    void delete(SinglePlayerGame singlePlayerGame);

}
