package com.example.set.model;

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
public class Table {
    /**
     * The default count of table cards on the table
     */
    final int DEFAULT_CARD_COUNT = 12;
    /**
     * The maximum count of table cards on the table
     */
    final int MAX_CARD_COUNT = 21;
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
     * Creates a table with a shuffled stack of cards.
     */
    Table() {
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
        stack.setSize(21); //TODO: Rausnehmen
    }

    /**
     * Getter
     * Returns the table cards.
     *
     * @return table cards
     */
    ArrayList<Card> getTableCards() {
        return tableCards;
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
     * Removes cards from table cards. Tries to replace them with a card from the stack or the table.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    void takeCardsFromTableCards(int position1, int position2, int position3) {
        if (tableCards.size() <= DEFAULT_CARD_COUNT) {
            replaceCardFromStack(position1);
            if(position2 >= getTableCardsCount()) {
                position2--;
            }
            replaceCardFromStack(position2);
            while(position3 >= getTableCardsCount()) {
                position3--;
            }
            replaceCardFromStack(position3);
        } else {
            if(position1 < getTableCardsCount() -3) {
                replaceCard(position1, getTableCardsCount() - 1);
            } else {
                tableCards.remove(position1);
            }
            if(position2 < getTableCardsCount() -2) {
                replaceCard(position2, getTableCardsCount() - 1);
            } else {
                tableCards.remove(position2);
            }
            if(position3 < getTableCardsCount() -1) {
                replaceCard(position3, getTableCardsCount() - 1);
            } else {
                tableCards.remove(position3);
            }
        }
    }

    /**
     * Reveals a card from the stack if the maximum amount is not reached.
     *
     * @return revealing was possible
     */
    private boolean revealCard() {
        if (tableCards.size() + 1 < MAX_CARD_COUNT) {
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
     */
    private void replaceCardFromStack(int position) {
        tableCards.remove(position);
        Card card = takeCardFromStack();
        if (card != null) {
            tableCards.add(position, card);
        }
    }

    /**
     * Removes the card from the position given as parameter. Replaces it with the card from the position given as other parameter.
     */
    private void replaceCard(int positionToReplace, int positionToTake) {
        tableCards.remove(positionToReplace);
        if(positionToReplace < positionToTake) {
            positionToTake -= 1;
        }
        Card card = tableCards.remove(positionToTake);
        if (card != null) {
            tableCards.add(positionToReplace, card);
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
}
