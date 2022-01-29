package com.example.set.model;

/**
 * The single player game class for of the logic
 * A class holding the logic of single player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
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
    public SinglePlayerGame(Rules rules) {
        super(rules);
        setAmount = 0;
    }

    /**
     * Reveals cards till the default amount is reached and at least one set exists if possible.
     */
    @Override
    protected void revealCards() {
        while (table.getTableCardsCount() < table.DEFAULT_CARD_COUNT || noSetExists()) {
            table.revealThreeCards();
        }
    }

    /**
     * Simulates the player taking a set. If the cards given as parameters are a set the amount of sets the player found is increased. Otherwise the player could be punished according to the rules. Checks if the game is over.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    void takeCard(int position1, int position2, int position3) {
        if (takeSetChecked(position1, position2, position3)) {
            setAmount++;
            revealCards();
        } else if (rules.isSinglePlayerDeduction()) {
            setAmount--;
        }

        testForGameOver();
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
}
