package com.example.set.database;

import androidx.room.TypeConverter;

import com.example.set.model.Card;
import com.example.set.model.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Converters class
 * A class holding the converters for the database.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class Converters {
    /**
     * Converts loaded String to player array.
     *
     * @param value loaded string
     * @return player array
     */
    @TypeConverter
    public static Player[] toPlayerArray(String value) {
        return new Gson().fromJson(value, Player[].class);
    }

    /**
     * Converts player array to string.
     *
     * @param values player array
     * @return result string
     */
    @TypeConverter
    public static String fromPlayerArray(Player[] values) {
        return new Gson().toJson(values, Player[].class);
    }

    /**
     * Converts loaded String to cards stack.
     *
     * @param value loaded string
     * @return cards stack
     */
    @TypeConverter
    public static Stack<Card> toCardsStack(String value) {
        Type listType = new TypeToken<Stack<Card>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Converts cards stack to string.
     *
     * @param values cards stack
     * @return result string
     */
    @TypeConverter
    public static String fromCardsStack(Stack<Card> values) {
        Type listType = new TypeToken<Stack<Card>>() {
        }.getType();
        return new Gson().toJson(values, listType);
    }

    /**
     * Converts loaded String to cards array list.
     *
     * @param value loaded string
     * @return cards array list
     */
    @TypeConverter
    public static ArrayList<Card> toCardsArrayList(String value) {
        Type listType = new TypeToken<ArrayList<Card>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Converts cards array list to string.
     *
     * @param values cards array list
     * @return result string
     */
    @TypeConverter
    public static String fromCardsArrayList(ArrayList<Card> values) {
        Type listType = new TypeToken<ArrayList<Card>>() {
        }.getType();
        return new Gson().toJson(values, listType);
    }
}
