package edu.hsos.set.model;

import androidx.annotation.NonNull;

/**
 * The rules class for of the logic
 * Holds and returns the defined rules of the game.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class MultiPlayerRules extends Rules {

    /**
     * Rule if wrong set in multiplayer mode should lead to suspension
     */
    private final boolean multiPlayerSuspension;

    /**
     * The time a player has in a multiplayer game to select a set after pressing set button
     */
    private final int multiPlayerSetTime;

    /**
     * Constructor
     * Creates a rules object with rules given as parameters.
     *
     * @param playerDeduction       Rule if wrong set should deduct players count of sets
     * @param multiPlayerSuspension Rule if wrong set in multi player mode should lead to exposure
     * @param multiPlayerSetTime    The time a player has in a multiplayer game to select a set after pressing set button
     */
    public MultiPlayerRules(boolean playerDeduction, boolean multiPlayerSuspension, int multiPlayerSetTime) {
        super(playerDeduction);
        this.multiPlayerSuspension = multiPlayerSuspension;
        this.multiPlayerSetTime = multiPlayerSetTime;
    }

    /**
     * Clones the current object.
     *
     * @return the cloned rule object
     */
    @NonNull
    public MultiPlayerRules clone() {
        return new MultiPlayerRules(this.isPlayerDeduction(), this.multiPlayerSuspension, this.multiPlayerSetTime);
    }

    /**
     * Getter
     * Returns if wrong set in multi player mode should lead to exposure.
     *
     * @return if wrong set in multi player mode should lead to exposure
     */
    public boolean isMultiPlayerSuspension() {
        return multiPlayerSuspension;
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