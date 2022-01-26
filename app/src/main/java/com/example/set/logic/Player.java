package com.example.set.logic;

/**
 * The player class for of the logic
 * Holds and manages the data associated with the player.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class Player {
    /**
     * name of the player
     */
    private final String name;
    /**
     * amount of found sets the player found
     */
    private int setAmount;
    /**
     * the player being exposed
     */
    private boolean exposed;

    /**
     * Constructor
     * Creates a player with the name from the parameter and initializes other variables.
     *
     * @param name name of the player
     */
    Player(String name) {
        this.name = name;
        this.setAmount = 0;
        this.exposed = false;
    }

    /**
     * increases the amount of found sets the player found
     */
    void increaseSetAmount() {
        setAmount++;
    }

    /**
     * decreases the amount of found sets the player found
     */
    void decreaseSetAmount() {
        if (setAmount > 0) {
            setAmount--;
        }
    }

    /**
     * Getter
     * returns the amount of found sets the player found
     *
     * @return amount of found sets the player found
     */
    int getSetAmount() {
        return setAmount;
    }

    /**
     * Getter
     * returns the name of the player
     *
     * @return the name of the player
     */
    String getName() {
        return name;
    }

    /**
     * Getter
     * returns if the player is exposed
     */
    boolean isExposed() {
        return exposed;
    }

    /**
     * Setter
     * starts the exposure of the player
     */
    void startExposure() {
        exposed = true;
    }

    /**
     * Setter
     * ends the exposure of the player
     */
    void endExposure() {
        exposed = false;
    }
}
