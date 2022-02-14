package com.example.set.controller;

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
     *
     * @param gameScreen the gameScreen object for the game
     * @param shortGame if the game should be a short game
     */
    SinglePlayerGameController(GameScreen gameScreen, boolean shortGame) {
        super(gameScreen);
        game = new SinglePlayerGame(getCurrentRules(), shortGame);
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
        gameScreen.gameOver(game.isShortGame(), ((SinglePlayerGame)game).getSetAmount(), game.getDuration(), game.getStartTime(), game.getRules().isSinglePlayerDeduction());
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
     * Called when pause is clicked.
     */
    @Override
    public void pauseScreen() {
        gameScreen.openPause(game.isShortGame(), ((SinglePlayerGame)game).getSetAmount(), game.getCardsLeft(), game.getDuration(), game.getStartTime(), game.getRules().isSinglePlayerDeduction());
    }

    /**
     * Called when resume is clicked.
     */
    @Override
    protected void resumeGameSpecific() {
        writeCards();
        writeScore();
        writeCardsLeft();
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
        writeCardsLeft();
        if(game.isOver()) {
            writeEndScreen();
        }
        return result;
    }

}
