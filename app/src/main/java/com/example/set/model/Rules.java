package com.example.set.model;

/**
 * The rules class for of the logic
 * Holds and returns the defined rules of the game.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class Rules {
    /**
     * Rule if wrong set in single player mode should deduct players count of sets
     */
    private final boolean singlePlayerDeduction;

    /**
     * Rule if wrong set in multi player mode should deduct players count of sets
     */
    private final boolean multiPlayerDeduction;

    /**
     * Rule if wrong set in multi player mode should lead to exposure
     */
    private final boolean multiPlayerExposure;

    /**
     * The time a player has in a multiplayer game to select a set after pressing set button
     */
    private final int multiPlayerSetTime;

    /**
     * Constructor
     * Creates a rules object with rules given as parameters.
     *
     * @param singlePlayerDeduction Rule if wrong set in single player mode should deduct players count of sets
     * @param multiPlayerDeduction  Rule if wrong set in multi player mode should deduct players count of sets
     * @param multiPlayerExposure   Rule if wrong set in multi player mode should lead to exposure
     * @param multiPlayerSetTime    The time a player has in a multiplayer game to select a set after pressing set button
     */
    public Rules(boolean singlePlayerDeduction, boolean multiPlayerDeduction, boolean multiPlayerExposure, int multiPlayerSetTime) {
        this.singlePlayerDeduction = singlePlayerDeduction;
        this.multiPlayerDeduction = multiPlayerDeduction;
        this.multiPlayerExposure = multiPlayerExposure;
        this.multiPlayerSetTime = multiPlayerSetTime;
    }

    public Rules clone() {
        return new Rules(this.singlePlayerDeduction, this.multiPlayerDeduction, this.multiPlayerExposure, this.multiPlayerSetTime);
    }

    /**
     * Getter
     * Returns if wrong set in single player mode should deduct players count of sets.
     *
     * @return wrong set in single player mode should deduct players count of sets
     */
    public boolean isSinglePlayerDeduction() {
        return singlePlayerDeduction;
    }

    /**
     * Getter
     * Returns if wrong set in multi player mode should deduct players count of sets.
     *
     * @return wrong set in multi player mode should deduct players count of sets
     */
    public boolean isMultiPlayerDeduction() {
        return multiPlayerDeduction;
    }

    /**
     * Getter
     * Returns if wrong set in multi player mode should lead to exposure.
     *
     * @return if wrong set in multi player mode should lead to exposure
     */
    public boolean getMultiPlayerExposure() {
        return multiPlayerExposure;
    }

    /**
     * Getter
     * Returns the time a player has in a multiplayer game to select a set after pressing set button.
     *
     * @return the time a player has in a multiplayer game to select a set after pressing set button
     */
    public int getMultiPlayerSetTime() {
        return multiPlayerSetTime;
    }
}
