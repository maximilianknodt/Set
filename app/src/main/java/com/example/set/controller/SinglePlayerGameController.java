package com.example.set.controller;

import com.example.set.model.SinglePlayerGame;

/**
 * The single player game controller class
 * A class holding the logic of single player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class SinglePlayerGameController extends GameController {

    /**
     * Constructor
     * Calls super constructor. Initializes the game with a new instance.
     */
    SinglePlayerGameController () {
        super();
        game = new SinglePlayerGame(getCurrentRules());
    }

    /**
     * Writes the score to the UI.
     */
    @Override
    protected void writeScore() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        //TODO: write to UI
    }

    /**
     * Writes the screen at the end of a game to the UI.
     */
    @Override
    protected void writeEndScreen() {
        //TODO: write to ui game over
        writeGameInfo();
    }

    /**
     * Writes the game info to the UI.
     */
    @Override
    protected void writeGameInfo() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        long start = game.getStartTime();
        long duration = game.getDuration();
        boolean deduction = game.getRules().isSinglePlayerDeduction();
        //TODO: write to UI
    }

    /**
     * Contains the things to be periodically updated.
     */
    @Override
    protected void periodicallyUpdate() {
        writeDuration();
    }

    /**
     * Resumes the game.
     */
    @Override
    protected void resume() {
        game.resume();
        createPeriodicalTimer();
        writeCards();
        writeScore();
    }

    /**
     * Function called when the button take set is pressed.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    @Override
    void takeSetPressed(int position1, int position2, int position3) {
        ((SinglePlayerGame)game).takeCards(position1, position2, position3);
        writeCards();
        writeScore();
        if(game.isOver()) {
            writeEndScreen();
        }
    }

}
