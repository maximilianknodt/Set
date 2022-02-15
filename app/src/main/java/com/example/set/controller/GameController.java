package com.example.set.controller;

import com.example.set.model.Game;
import com.example.set.model.Rules;
import com.example.set.ui.GameScreen;

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
    protected GameScreen gameScreen;

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
        writeCardsLeft();
        createTimer();
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
        gameScreen.setCards(game.getTableCards());
    }

    /**
     * Writes the duration of the game to the UI.
     */
    protected void writeDuration() {
        gameScreen.setTime(game.getDuration());
    }

    /**
     * Writes the score to the UI.
     */
    protected void writeCardsLeft() {
        gameScreen.setCardsLeft(game.getCardsLeft());
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
     * Called when pause is clicked.
     */
    public abstract void pauseScreen();

    /**
     * Called when game resumes.
     */
    protected abstract void resumeGameSpecific();

    /**
     * Function called when the button take set is pressed.
     *
     * @param position1 position of the first card
     * @param position2 position of the second card
     * @param position3 position of the third card
     * @return if the set was correct
     */
    abstract boolean takeSetPressed(int position1, int position2, int position3);

    /**
     * Pauses the game.
     */
    public void pause() {
        game.pause();
        timer.cancel();
    }

    /**
     * Resumes the game on a given game screen.
     *
     * @param gameScreen the game screen
     */
    public void resume(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        resume();
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        game.resume();
        createTimer();
        resumeGameSpecific();
    }

    private void createTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                periodicallyUpdate();
            }
        }, 0, 1000 / UPDATES_PER_SECOND);
    }
}
