package com.example.set.controller;

import android.util.Log;

import com.example.set.model.SinglePlayerGame;
import com.example.set.ui.GameScreen;

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
    SinglePlayerGameController (GameScreen gameScreen) {
        super(gameScreen);
        game = new SinglePlayerGame(getCurrentRules());
    }

    /**
     * Writes the score to the UI.
     */
    @Override
    protected void writeScore() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        gameScreen.setPoints(score);
    }

    /**
     * Writes the screen at the end of a game to the UI.
     */
    @Override
    protected void writeEndScreen() {
        gameScreen.gameOver(((SinglePlayerGame)game).getSetAmount(), game.getDuration(), game.getStartTime(), game.getRules().isSinglePlayerDeduction());
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
     * @return if the set was correct
     */
    @Override
    public boolean takeSetPressed(int position1, int position2, int position3) {
        boolean result = ((SinglePlayerGame)game).takeCards(position1, position2, position3);
        writeCards();
        writeScore();
        if(game.isOver()) {
            writeEndScreen();
        }
        return result;
    }

}
