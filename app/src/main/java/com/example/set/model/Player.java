package com.example.set.model;

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
    public Player(String name) {
        this.name = name;
        this.setAmount = 0;
        this.exposed = false;
    }

    /**
     * Increases the amount of found sets the player found.
     */
    void increaseSetAmount() {
        setAmount++;
    }

    /**
     * Decreases the amount of found sets the player found.
     */
    void decreaseSetAmount() {
        if (setAmount > 0) {
            setAmount--;
        }
    }

    /**
     * Getter
     * Returns the amount of found sets the player found.
     *
     * @return amount of found sets the player found
     */
    public int getSetAmount() {
        return setAmount;
    }

    /**
     * Getter
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * Returns if the player is exposed.
     */
    public boolean isSuspended() {
        return exposed;
    }

    /**
     * Setter
     * Starts the exposure of the player.
     */
    void startSuspension() {
        exposed = true;
    }

    /**
     * Setter
     * Ends the exposure of the player.
     */
    void endSuspension() {
        exposed = false;
    }
}
