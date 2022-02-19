package com.example.set.controller;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

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
     * the time for periodical updates in Hz (1/s)
     */
    private final static int UPDATES_PER_SECOND = 60;
    /**
     * the model of the game
     */
    protected Game game;
    /**
     * the ui element for writing the game
     */
    protected GameScreen gameScreen;
    /**
     * the timer for periodical updates
     */
    private Timer timer;

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
     * @param context the context for getting the rules
     * @return the current rules
     */
    protected Rules getCurrentRules(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new Rules(sharedPreferences.getBoolean("Settings_Preferences_SP_Deduction", false), sharedPreferences.getBoolean("Settings_Preferences_MP_Deduction", false), sharedPreferences.getBoolean("Settings_Preferences_Suspension", false), Integer.parseInt(sharedPreferences.getString("Settings_Preferences_Timer", "20")));
    }

    /**
     * Writes the cards to the UI.
     */
    public void writeCards() {
        gameScreen.writeCards(game.getTableCards());
    }

    /**
     * Writes the duration of the game to the UI.
     */
    protected void writeDuration() {
        gameScreen.writeTime(game.getDuration());
    }

    /**
     * Writes the score to the UI.
     */
    protected void writeCardsLeft() {
        gameScreen.writeCardsLeft(game.getCardsLeft());
    }

    /**
     * Writes the score to the UI.
     */
    abstract protected void writeScore();

    /**
     * Called when the game is over.
     */
    protected void gameOver() {
        timer.cancel();
        this.game = null;
    }

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
    public abstract boolean takeSetPressed(int position1, int position2, int position3);

    /**
     * Pauses the game.
     * @param context the context for saving the game in database
     */
    public void pause(Context context) {
        if (game != null) {
            game.pause();
            Thread thread = new Thread() {
                @Override
                public void run() {
                    AppControllerHolder.getAppController().saveGamesInDatabase(context);
                }
            };
            thread.start();
        }
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

    /**
     * Getter
     * Returns the game
     */
    Game getGame() {
        return game;
    }

    /**
     * creates the timer.
     */
    private void createTimer() {
        if(timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (game != null) {
                    periodicallyUpdate();
                }
            }
        }, 0, 1000 / UPDATES_PER_SECOND);
    }
}
