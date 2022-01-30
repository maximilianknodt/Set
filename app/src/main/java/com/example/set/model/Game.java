package com.example.set.model;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The game class for of the logic
 * An abstract class holding the common logic of single player and multiplayer games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public abstract class Game {
    /**
     * the table for the game
     */
    protected final Table table;

    /**
     * the rules for the game
     */
    protected final Rules rules;

    /**
     * the time the game started
     */
    private long startTime;

    /**
     * the time the game resumed
     */
    private long resumeTime;

    /**
     * the time the game took before paused
     */
    private long timeBeforePaused;

    /**
     * Constructor
     * Creates a game with as parameter given rules. Creates a table for the game.
     *
     * @param rules the rules for the game
     */
    Game(Rules rules) {
        this.rules = rules.clone();
        this.table = new Table();
    }

    /**
     * starts a game by revealing the first cards and setting the time the game started
     */
    public void startGame() {
        timeBeforePaused = 0;
        startTime = System.currentTimeMillis();
        resumeTime = startTime;
        revealCards();
    }

    /**
     * Getter
     * returns the time the game lasted at the current moment in seconds
     */
    public long getDuration() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - resumeTime) + timeBeforePaused;
    }

    /**
     * Getter
     * returns the time the game started in seconds
     */
    public long getStartTime() {
        return TimeUnit.MILLISECONDS.toSeconds(startTime);
    }

    /**
     * Abstract method
     * reveals as much cards as necessary or possible
     */
    protected abstract void revealCards();

    /**
     * pauses game (timer)
     */
    public void pause() {
        timeBeforePaused = getDuration();
    }

    /**
     * resumes game (timer)
     */
    public void resume() {
        resumeTime = System.currentTimeMillis();
    }

    /**
     * checks if the game is over
     *
     * @return the game is over
     */
    public boolean isOver() {
        return table.getStackSize() <= 0 && noSetExists();
    }

    /**
     * Takes a set if the cards at the as parameter given positions are a set.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the given cards are a set
     */
    protected boolean takeSetChecked(int position1, int position2, int position3) {
        if (positionsValid(position1, position2, position3)) {
            if (isSet(position1, position2, position3)) {
                table.takeCardsFromTableCards(position1, position2, position3);
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if a set exists in the table cards.
     *
     * @return a set exists in the table cards
     */
    protected boolean noSetExists() {
        ArrayList<Card> tableCards = table.getTableCards();
        for (int i = 0; i < tableCards.size() - 2; i++) {
            for (int j = i + 1; j < tableCards.size() - 1; j++) {
                for (int k = j + 1; k < tableCards.size(); k++) {
                    if (isSet(i, j, k)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if the cards at the as parameter given positions are a set.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the given cards are a set
     */
    private boolean isSet(int position1, int position2, int position3) {
        ArrayList<Card> tableCards = table.getTableCards();
        if (positionsValid(position1, position2, position3)) {
            Card card1 = tableCards.get(position1);
            Card card2 = tableCards.get(position2);
            Card card3 = tableCards.get(position3);
            return ((sameColor(card1, card2, card3) || differentColor(card1, card2, card3)) && (sameShape(card1, card2, card3) || differentForm(card1, card2, card3)) && (sameFilling(card1, card2, card3) || differentFilling(card1, card2, card3)) && (sameCount(card1, card2, card3) || differentCount(card1, card2, card3)));
        }
        return false;
    }

    /**
     * Getter
     * Returns the table cards.
     *
     * @return table cards
     */
    public ArrayList<Card> getTableCards() {
        return table.getTableCards();
    }

    /**
     * Getter
     * Returns the rules of the game.
     *
     * @return the rules of the game
     */
    public Rules getRules() {
        return rules;
    }

    /**
     * Checks if the positions of the cards given as parameter are valid.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the given cards are valid
     */
    private boolean positionsValid(int position1, int position2, int position3) {
        return positionValid(position1) && positionValid(position2) && positionValid(position3);
    }

    /**
     * Checks if the position of the card given as parameter is valid.
     *
     * @param position position of the card
     * @return if the given card is valid
     */
    private boolean positionValid(int position) {
        return position >= 0 && position < table.getTableCards().size();
    }

    /**
     * Checks if the elements on the cards given as parameter have the same color.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have the same color
     */
    private boolean sameColor(Card card1, Card card2, Card card3) {
        return card1.getColor() == card2.getColor() && card2.getColor() == card3.getColor();
    }

    /**
     * Checks if the elements on the cards given as parameter have the same shape.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have the same shape
     */
    private boolean sameShape(Card card1, Card card2, Card card3) {
        return card1.getShape() == card2.getShape() && card2.getShape() == card3.getShape();
    }

    /**
     * Checks if the elements on the cards given as parameter have the same filling.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have the same filling
     */
    private boolean sameFilling(Card card1, Card card2, Card card3) {
        return card1.getFilling() == card2.getFilling() && card2.getFilling() == card3.getFilling();
    }

    /**
     * Checks if the count of the elements of the cards given as parameter is the same.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return the count of the elements of the cards is the same
     */
    private boolean sameCount(Card card1, Card card2, Card card3) {
        return card1.getCount() == card2.getCount() && card2.getCount() == card3.getCount();
    }

    /**
     * Checks if the elements on the cards given as parameter have all different colors.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have all different colors
     */
    private boolean differentColor(Card card1, Card card2, Card card3) {
        return card1.getColor() != card2.getColor() && card2.getColor() != card3.getColor() && card1.getColor() != card3.getColor();
    }

    /**
     * Checks if the elements on the cards given as parameter have all different shapes.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have all different shapes
     */
    private boolean differentForm(Card card1, Card card2, Card card3) {
        return card1.getShape() != card2.getShape() && card2.getShape() != card3.getShape() && card1.getShape() != card3.getShape();
    }

    /**
     * Checks if the elements on the cards given as parameter have all different fillings.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return elements on the cards have all different fillings
     */
    private boolean differentFilling(Card card1, Card card2, Card card3) {
        return card1.getFilling() != card2.getFilling() && card2.getFilling() != card3.getFilling() && card1.getFilling() != card3.getFilling();
    }

    /**
     * Checks if the count of the elements of the cards given as parameter are all different.
     *
     * @param card1 first card
     * @param card2 second card
     * @param card3 third card
     * @return the count of the elements of the cards are all different
     */
    private boolean differentCount(Card card1, Card card2, Card card3) {
        return card1.getCount() != card2.getCount() && card2.getCount() != card3.getCount() && card1.getCount() != card3.getCount();
    }
}
