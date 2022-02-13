package com.example.set.controller;

import com.example.set.model.Card;
import com.example.set.model.Game;
import com.example.set.model.Rules;
import com.example.set.ui.GameScreen;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The game controller class
 * An abstract class holding the common logic of single player and multiplayer game controllers.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public abstract class GameController {
    /**
     * the model of the game
     */
    protected Game game;

    /**
     * the timer for periodical updates
     */
    private Timer timer;

    /**
     * the ui element for writing the game
     */
    private GameScreen gameScreen;

    /**
     * the time for periodical updates in Hz (1/s)
     */
    private final int UPDATES_PER_SECOND = 60;

    /**
     * Constructor
     * Initializes the ui element for the game.
     */
    protected GameController(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        game.startGame();
        writeCards();
        writeScore();
        createPeriodicalTimer();
    }

    /**
     * Reads in the current rules and returns them.
     *
     * @return the current rules
     */
    protected Rules getCurrentRules() {
        //TODO: read current rules and return them
        return new Rules(true,true,true,10);
    }

    /**
     * Writes the cards to the UI.
     */
    protected void writeCards() {
        ArrayList<Card> cards = game.getTableCards();
        //TODO: write to UI
        gameScreen.setCards(cards);
    }

    /**
     * Writes the duration of the game to the UI.
     */
    protected void writeDuration() {
        long duration = game.getDuration();
        //TODO: write to UI
    }

    /**
     * Writes the score to the UI.
     */
    abstract protected void writeScore();

    /**
     * Writes the screen at the end of a game to the UI.
     */
    abstract protected void writeEndScreen();

    /**
     * Writes the game info to the UI.
     */
    abstract protected void writeGameInfo();

    /**
     * Contains the things to be periodically updated.
     */
    abstract protected void periodicallyUpdate();

    /**
     * Pauses the game.
     */
    protected void pause() {
        game.pause();
        timer.cancel();
        //TODO: write pause screen
        writeGameInfo();
    }

    /**
     * Resumes the game.
     */
    protected abstract void resume();

    /**
     * Function called when the button take set is pressed.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     */
    abstract void takeSetPressed(int position1, int position2, int position3);

    /**
     * Creates the timer for the periodical update.
     */
    protected void createPeriodicalTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                periodicallyUpdate();
            }
        }, 0, 1000 / UPDATES_PER_SECOND);
    }
}
