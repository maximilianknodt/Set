package edu.hsos.set.model;

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
    private final boolean playerDeduction;

    /**
     * Constructor
     * Creates a rules object with rules given as parameters.
     *
     * @param playerDeduction Rule if wrong set should deduct players count of sets
     */
    public Rules(boolean playerDeduction) {
        this.playerDeduction = playerDeduction;
    }

    /**
     * Clones the current object.
     *
     * @return the cloned rule object
     */
    public Rules clone() {
        return new Rules(this.playerDeduction);
    }

    /**
     * Getter
     * Returns if wrong set in single player mode should deduct players count of sets.
     *
     * @return wrong set in single player mode should deduct players count of sets
     */
    public boolean isPlayerDeduction() {
        return playerDeduction;
    }
}
