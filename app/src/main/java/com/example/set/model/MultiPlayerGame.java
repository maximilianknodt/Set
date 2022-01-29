package com.example.set.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The multi player game class for of the logic
 * A class holding the logic of multi player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 * <p>
 * TODO: Implement Messaging
 */
public class MultiPlayerGame extends Game {
    /**
     * players playing in the game
     */
    private final Player[] players;

    /**
     * the time a player has to select a set count down as timer
     */
    private Timer takeSetTimer;

    /**
     * the time a player has left to select a set
     */
    private int takeSetTimeLeft;

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
     * reveals cards till the default amount is reached if possible
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
     * TODO: implement the messaging of the UI via Controller
     */
    <takeSetTimeLeft> boolean set(Player player) {
        if (player.isExposed()) {
            return false;
        }

        if (rules.getMultiPlayerSetTime() > 0) {
            takeSetTimer = new Timer();

            takeSetTimeLeft = rules.getMultiPlayerSetTime();
            takeSetTimer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    takeSetTimeLeft--;
                    if (takeSetTimeLeft <= 0) {
                        punishPlayer(player);
                        takeSetTimer.cancel();
                    }
                }
            }, 1000, 1000);
        }
        return true;
    }

    /**
     * Getter
     * returns the time a player has left to select a set in seconds
     */
    int getTakeSetTimeLeft() {
        return takeSetTimeLeft;
    }
    /**
     * Getter
     * returns the time a player has left to select a set in seconds
     */
    boolean isTakeSetTimeOver() {
        return takeSetTimeLeft <= 0;
    }

    /**
     * Simulates the player taking a set. If the cards given as parameters are a set the amount of sets the player found is increased. Otherwise the player could be punished according to the rules. Checks if the game is over.
     *
     * @param player    player taking the set
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    void takeSet(Player player, int position1, int position2, int position3) {
        takeSetTimer.cancel();

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
    private void punishPlayer(Player player) {
        if (rules.isMultiPlayerDeduction()) {
            player.decreaseSetAmount();
        } else if (rules.getMultiPlayerExposure()) {
            player.startExposure();
        }
    }
}
