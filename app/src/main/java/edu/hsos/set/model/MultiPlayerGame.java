package edu.hsos.set.model;

import androidx.room.Embedded;
import androidx.room.Entity;

/**
 * The multi player game class
 * A class holding the logic of multi player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Entity
public class MultiPlayerGame extends Game {
    /**
     * players playing in the game
     */
    private Player[] players;

    /**
     * the multi player rules for the game (not held by the superclass because it creates problems with room)
     */
    @Embedded
    private MultiPlayerRules multiPlayerRules;

    /**
     * the time a player has pressed set or the time resumed
     */
    private long takeSetTimeStart;

    /**
     * the time passed a player has pressed set before paused
     */
    private long takeSetTimeBeforePaused;

    /**
     * Constructor
     * Calls super constructor with as parameter given rules. Initializes the players with the as parameter given ones.
     *
     * @param players          players playing in the game
     * @param multiPlayerRules the rules for the game
     * @param shortGame        if the game should be a short game
     */
    public MultiPlayerGame(Player[] players, MultiPlayerRules multiPlayerRules, boolean shortGame) {
        super(shortGame);
        this.multiPlayerRules = multiPlayerRules.clone();
        this.players = players;
    }

    /**
     * Reveals cards till the default amount is reached if possible.
     */
    @Override
    protected void revealCards() {
        while (table.getTableCardsCount() < table.DEFAULT_CARD_COUNT) {
            table.revealThreeCards();
        }
    }

    /**
     * Simulates the dealer adding three cards if possible.
     *
     * @return if the addition of cards was possible
     */
    public boolean addCards() {
        if (table.getTableCardsCount() < table.MAX_CARD_COUNT && table.getStackSize() > 0) {
            table.revealThreeCards();
            return true;
        }
        return false;
    }

    /**
     * Simulates as parameter given player calling set. Checks if the player is exposed and starts the time the player has to select a set.
     *
     * @param player player called set
     * @return player can try to take it (is not exposed)
     */
    public boolean callSet(Player player) {
        if (player.isSuspended()) {
            return false;
        }

        for (Player p : players) {
            if (p.isSuspended()) {
                p.endSuspension();
            }
        }

        takeSetTimeStart = System.currentTimeMillis();
        takeSetTimeBeforePaused = 0;
        return true;
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        timeBeforePaused = getDuration();
        takeSetTimeBeforePaused = getTakeSetDuration();
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        resumeTime = System.currentTimeMillis();
        takeSetTimeStart = System.currentTimeMillis();
    }

    /**
     * Simulates the player taking a set. If the cards given as parameters are a set the amount of sets the player found is increased. Otherwise the player could be punished according to the rules. Checks if the game is over.
     *
     * @param player    player taking the set
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the set was correct
     */
    public boolean takeCards(Player player, int position1, int position2, int position3) {
        boolean result = false;

        if (!isTakeSetTimeOver()) {
            if (takeSetChecked(position1, position2, position3)) {
                player.increaseSetAmount();
                result = true;
            } else {
                punishPlayer(player);
            }
        }
        return result;
    }

    /**
     * Punishes the player according to the rules.
     *
     * @param player the player getting punished
     */
    public void punishPlayer(Player player) {
        if (multiPlayerRules.isPlayerDeduction()) {
            player.decreaseSetAmount();
        }
        if (multiPlayerRules.isMultiPlayerSuspension()) {
            player.startSuspension();
        }
    }

    /**
     * Getter
     * Returns the time a player has left to select a set in seconds.
     *
     * @return the time a player has left to select a set in seconds
     */
    public long getTakeSetTimeLeft() {
        return multiPlayerRules.getMultiPlayerSetTime() - getTakeSetDuration() / 1000;
    }

    /**
     * Getter
     * Returns the time since a player pressed set.
     *
     * @return the time since a player pressed set
     */
    private long getTakeSetDuration() {
        return System.currentTimeMillis() - takeSetTimeStart + takeSetTimeBeforePaused;
    }

    /**
     * Getter
     * Returns if the time a player has left to select is over.
     *
     * @return the time a player has left to select is over
     */
    public boolean isTakeSetTimeOver() {
        return getTakeSetTimeLeft() <= 0;
    }

    /**
     * Getter
     * Returns the players.
     *
     * @return the players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Getter
     * Returns the time a player has pressed set or the time resumed.
     *
     * @return the time a player has pressed set or the time resumed
     */
    public long getTakeSetTimeStart() {
        return takeSetTimeStart;
    }

    /**
     * Getter
     * Returns the time passed a player has pressed set before paused.
     *
     * @return the time passed a player has pressed set before paused
     */
    public long getTakeSetTimeBeforePaused() {
        return takeSetTimeBeforePaused;
    }

    /**
     * Getter
     * Returns the multi player rules of the game
     *
     * @return the multi player rules of the game
     */
    public MultiPlayerRules getMultiPlayerRules() {
        return multiPlayerRules;
    }

    /**
     * Setter
     * Sets the time a player has pressed set or the time resumed.
     *
     * @param takeSetTimeStart the time a player has pressed set or the time resumed
     */
    public void setTakeSetTimeStart(long takeSetTimeStart) {
        this.takeSetTimeStart = takeSetTimeStart;
    }

    /**
     * Setter
     * Sets the time passed a player has pressed set before paused.
     *
     * @param takeSetTimeBeforePaused the time passed a player has pressed set before paused
     */
    public void setTakeSetTimeBeforePaused(long takeSetTimeBeforePaused) {
        this.takeSetTimeBeforePaused = takeSetTimeBeforePaused;
    }
}
