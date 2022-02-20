package com.example.set.model;

import androidx.room.Entity;

/**
 * The single player game class
 * A class holding the logic of single player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Entity
public class SinglePlayerGame extends Game {
    /**
     * amount of found sets the player found
     */
    private int setAmount;

    /**
     * Constructor
     * Calls super constructor with as parameter given rules. Initializes the amount of sets the player found with zero.
     *
     * @param rules the rules for the game
     */
    public SinglePlayerGame(Rules rules, boolean shortGame) {
        super(rules, shortGame);
        setAmount = 0;
    }

    /**
     * Reveals cards till the default amount is reached and at least one set exists if possible.
     */
    @Override
    protected void revealCards() {
        while ((table.getTableCardsCount() < table.DEFAULT_CARD_COUNT || noSetExists()) && table.getStackSize() > 0) {
            table.revealThreeCards();
        }
    }

    /**
     * Simulates the player taking a set. If the cards given as parameters are a set the amount of sets the player found is increased. Otherwise the player could be punished according to the rules. Checks if the game is over.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the set was correct
     */
    public boolean takeCards(int position1, int position2, int position3) {
        if (takeSetChecked(position1, position2, position3)) {
            setAmount++;
            revealCards();
            return true;
        } else if (rules.isPlayerDeduction()) {
            if (setAmount > 0) {
                setAmount--;
            }
        }
        return false;
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        timeBeforePaused = getDuration();
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        resumeTime = System.currentTimeMillis();
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
     * Setter
     * Sets the set amount.
     *
     * @param setAmount the set amount
     */
    public void setSetAmount(int setAmount) {
        this.setAmount = setAmount;
    }
}
