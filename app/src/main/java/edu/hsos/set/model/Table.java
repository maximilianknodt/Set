package edu.hsos.set.model;

import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Die Table Class of the logic
 * Implements the management of the elements on the Table. (e.g. the Stack of cards and table cards).
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
@Entity(tableName = "GameTable")
public class Table {
    /**
     * The default count of table cards on the table
     */
    @Ignore
    final int DEFAULT_CARD_COUNT = 12;

    /**
     * The maximum count of table cards on the table
     */
    @Ignore
    final int MAX_CARD_COUNT = 21;

    /**
     * The maximum count of table cards on the table
     */
    @Ignore
    final int SHORT_GAME_CARD_COUNT = 39;

    /**
     * Stack of Cards
     */
    private Stack<Card> stack;

    /**
     * Cards revealed on the table
     */
    private ArrayList<Card> tableCards;

    /**
     * Constructor
     * The default constructor doing nothing.
     */
    public Table() {
    }

    /**
     * Constructor
     * Creates a table with a shuffled stack of cards.
     *
     * @param shortGame if the game is a short game with reduced amount of cards
     */
    Table(boolean shortGame) {
        stack = new Stack<>();
        tableCards = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                for (Filling filling : Filling.values()) {
                    for (Count count : Count.values()) {
                        stack.add(new Card(color, shape, filling, count));
                    }
                }
            }
        }

        Collections.shuffle(stack);

        if (shortGame) {
            stack.setSize(SHORT_GAME_CARD_COUNT);
        }
    }

    /**
     * Removes cards from table cards. Tries to replace them with a card from the stack or the table.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    void takeCardsFromTableCards(int position1, int position2, int position3) {
        Card card1 = tableCards.get(position1);
        Card card2 = tableCards.get(position2);
        Card card3 = tableCards.get(position3);

        if (tableCards.size() <= DEFAULT_CARD_COUNT) {
            replaceCardFromStack(card1);
            replaceCardFromStack(card2);
            replaceCardFromStack(card3);
        } else {
            replaceCardWithLast(card1);
            replaceCardWithLast(card2);
            replaceCardWithLast(card3);
        }
    }

    /**
     * Replaces a card with the last card (in order) on the table
     *
     * @param card card to be replaced
     */
    private void replaceCardWithLast(Card card) {
        Card last = tableCards.get(tableCards.size() - 1);
        if (last == card) {
            tableCards.remove(card);
        } else {
            tableCards.remove(last);
            replaceCardExplicit(card, last);
        }
    }

    /**
     * Reveals a card from the stack if the maximum amount is not reached.
     *
     * @return revealing was possible
     */
    private boolean revealCard() {
        if (tableCards.size() + 1 <= MAX_CARD_COUNT) {
            Card card = takeCardFromStack();
            if (card != null) {
                tableCards.add(card);
                return true;
            }
        }
        return false;
    }

    /**
     * Reveals three cards from the stack if the maximum amount is not reached.
     */
    void revealThreeCards() {
        for (int i = 3; i > 0; i--) {
            if (!revealCard()) {
                break;
            }
        }
    }

    /**
     * Removes the card from the position given as parameter. Replaces it with a card from the stack if the stack is not empty.
     *
     * @param card card to replace
     */
    private void replaceCardFromStack(Card card) {
        replaceCardExplicit(card, takeCardFromStack());
    }

    /**
     * Replaces a card on the table with another card
     *
     * @param card1 card to be replaced on the table
     * @param card2 replacement card; not on table
     */
    private void replaceCardExplicit(Card card1, Card card2) {
        int index = tableCards.indexOf(card1);
        tableCards.remove(card1);
        if (card2 != null) {
            tableCards.add(index, card2);
        }
    }

    /**
     * Takes a card from the stack if the stack is not empty.
     *
     * @return card or null if stack is empty
     */
    private Card takeCardFromStack() {
        if (stack.size() > 0) {
            return stack.pop();
        }
        return null;
    }

    /**
     * Getter
     * Returns the table cards.
     *
     * @return table cards
     */
    public ArrayList<Card> getTableCards() {
        return tableCards;
    }

    /**
     * Setter
     * Sets the table cards.
     *
     * @param tableCards the table cards
     */
    public void setTableCards(ArrayList<Card> tableCards) {
        this.tableCards = tableCards;
    }

    /**
     * Getter
     * Returns the amount of table cards.
     *
     * @return amount of table cards
     */
    int getTableCardsCount() {
        return tableCards.size();
    }

    /**
     * Getter
     * Returns the amount of cards on the stack.
     *
     * @return amount of cards on the stack
     */
    int getStackSize() {
        return stack.size();
    }

    /**
     * Getter
     * Returns the stack.
     *
     * @return the stack
     */
    public Stack<Card> getStack() {
        return stack;
    }

    /**
     * Setter
     * Sets the stack.
     *
     * @param stack the stack
     */
    public void setStack(Stack<Card> stack) {
        this.stack = stack;
    }
}
