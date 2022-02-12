package com.example.set.model;

/**
 * colors of a Elements on a card
 */
enum Color {
    RED, BLUE, GREEN
}

/**
 * shapes of a Elements on a card
 */
enum Shape {
    WAVE, OVAL, DIAMOND
}

/**
 * fillings of a Elements on a card
 */
enum Filling {
    EMPTY, HALF_FULL, FULL
}

/**
 * count of a Elements on a card
 */
enum Count {
    ONE, TWO, THREE
}

/**
 * The card class of the logic
 * Holds and returns the characteristics of a Card.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class Card {
    /**
     * color of the elements on the card
     */
    private final Color color;

    /**
     * shape of the elements on the card
     */
    private final Shape shape;

    /**
     * filling of the elements on the card
     */
    private final Filling filling;

    /**
     * count of the elements on the card
     */
    private final Count count;

    /**
     * Constructor
     * Creates a card with as parameter passed characteristics.
     *
     * @param color   color of the elements on the card
     * @param shape   shape of the elements on the card
     * @param filling filling of the elements on the card
     * @param count   count of the elements on the card
     */
    Card(Color color, Shape shape, Filling filling, Count count) {
        this.color = color;
        this.shape = shape;
        this.filling = filling;
        this.count = count;
    }

    /**
     * Getter
     * Returns color of Elements on the card.
     *
     * @return color of Elements on the card
     */
    Color getColor() {
        return color;
    }

    /**
     * Getter
     * Returns shape of Elements on the card.
     *
     * @return shape of Elements on the card
     */
    Shape getShape() {
        return shape;
    }

    /**
     * Getter
     * Returns filling of Elements on the card.
     *
     * @return filling of Elements on the card
     */
    Filling getFilling() {
        return filling;
    }

    /**
     * Getter
     * Returns count of Elements on the card.
     *
     * @return count of Elements on the card
     */
    Count getCount() {
        return count;
    }

}


