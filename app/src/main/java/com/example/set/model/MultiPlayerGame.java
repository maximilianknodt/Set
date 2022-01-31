package com.example.set.model;

/**
 * The multi player game class
 * A class holding the logic of multi player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class MultiPlayerGame extends Game {
    /**
     * players playing in the game
     */
    private final Player[] players;

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
     * @param players players playing in the game
     * @param rules   the rules for the game
     */
    public MultiPlayerGame(Player[] players, Rules rules) {
        super(rules);
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
     */
    void addCards() {
        if (table.getTableCardsCount() < table.MAX_CARD_COUNT) {
            table.revealThreeCards();
        }
    }

    /**
     * Simulates as parameter given player calling set. Checks if he is exposed and starts the time he has to select a set.
     *
     * @param player player called set
     * @return player can try to take it (is not exposed)
     * <p>
     */
    public boolean set(Player player) {
        if (player.isExposed()) {
            return false;
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
     * Getter
     * Returns the time a player has left to select a set.
     *
     * @return the time a player has left to select a set
     */
    public long getTakeSetTimeLeft() {
        return rules.getMultiPlayerSetTime() - getTakeSetDuration();
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
     * Simulates the player taking a set. If the cards given as parameters are a set the amount of sets the player found is increased. Otherwise the player could be punished according to the rules. Checks if the game is over.
     *
     * @param player    player taking the set
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    public void takeCards(Player player, int position1, int position2, int position3) {
        if (takeSetChecked(position1, position2, position3)) {
            player.increaseSetAmount();
        } else {
            punishPlayer(player);
        }

        for (Player p : players) {
            if (p.isExposed()) {
                p.endExposure();
            }
        }

        isOver();
    }

    /**
     * Punishes the player according to the rules.
     *
     * @param player the player getting punished
     */
    public void punishPlayer(Player player) {
        if (rules.isMultiPlayerDeduction()) {
            player.decreaseSetAmount();
        } else if (rules.isMultiPlayerExposure()) {
            player.startExposure();
        }
    }
}
