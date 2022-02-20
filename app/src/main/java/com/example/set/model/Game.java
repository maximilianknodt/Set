package com.example.set.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * The game class
 * An abstract class holding the common logic of single player and multiplayer games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Entity
public abstract class Game {
    /**
     * the rules for the game
     */
    @Embedded
    protected final Rules rules;
    /**
     * the table for the game
     */
    @Embedded
    protected Table table;
    /**
     * the time the game resumed
     */
    protected long resumeTime;
    /**
     * the time the game took before paused
     */
    protected long timeBeforePaused;
    /**
     * if the game is a short game
     */
    protected boolean shortGame;
    /**
     * the time the game started
     */
    @PrimaryKey
    private long startTime;

    /**
     * Constructor
     * Creates a game with as parameter given rules. Creates a table for the game.
     *
     * @param rules     the rules for the game
     * @param shortGame if the game is a short game
     */
    Game(Rules rules, boolean shortGame) {
        this.rules = rules.clone();
        this.table = new Table(shortGame);
        this.shortGame = shortGame;
    }

    /**
     * Starts a game by revealing the first cards and setting the time the game started.
     */
    public void startGame() {
        timeBeforePaused = 0;
        startTime = System.currentTimeMillis();
        resumeTime = startTime;
        revealCards();
    }

    /**
     * Abstract method
     * Reveals as much cards as necessary or possible.
     */
    protected abstract void revealCards();

    /**
     * Pauses the game.
     */
    public abstract void pause();

    /**
     * Resumes the game.
     */
    public abstract void resume();

    /**
     * Checks if the game is over.
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
        if (positionsValid(position1, position2, position3) && isSet(position1, position2, position3)) {
            table.takeCardsFromTableCards(position1, position2, position3);
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

    /**
     * Getter
     * Returns the time the game lasted at the current moment in seconds.
     *
     * @return the time the game lasted at the current moment in seconds
     */
    public long getDuration() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - resumeTime) + timeBeforePaused;
    }

    /**
     * Getter
     * Returns the timestamp the game started.
     *
     * @return the timestamp the game started
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Setter
     * Sets the timestamp the game started.
     *
     * @param startTime the timestamp the game started
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter
     * Returns the time the game resumed.
     *
     * @return the time the game resumed
     */
    public long getResumeTime() {
        return resumeTime;
    }

    /**
     * Setter
     * Sets the time the game resumed.
     *
     * @param resumeTime the the time the game resumed
     */
    public void setResumeTime(long resumeTime) {
        this.resumeTime = resumeTime;
    }

    /**
     * Getter
     * Returns the time the game took before paused.
     *
     * @return the time the game took before paused
     */
    public long getTimeBeforePaused() {
        return timeBeforePaused;
    }

    /**
     * Setter
     * Sets the time the game took before paused.
     *
     * @param timeBeforePaused the time the game took before paused
     */
    public void setTimeBeforePaused(long timeBeforePaused) {
        this.timeBeforePaused = timeBeforePaused;
    }

    /**
     * Getter
     * Returns the table object.
     *
     * @return the table object
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Setter
     * Sets the table object.
     *
     * @param table the table object
     */
    public void setTable(Table table) {
        this.table = table;
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
     * Returns the count of cards on the stack.
     *
     * @return count of cards on the stack
     */
    public int getCardsLeft() {
        return table.getStackSize();
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
     * Getter
     * Returns if the game is a short game.
     *
     * @return if the game is a short game
     */
    public boolean isShortGame() {
        return shortGame;
    }

    /**
     * Setter
     * Sets if the game is a short game.
     *
     * @param shortGame if the game is a short game
     */
    public void setShortGame(boolean shortGame) {
        this.shortGame = shortGame;
    }
}
