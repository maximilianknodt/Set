package com.example.set.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.set.R;
import com.example.set.controller.AppController;
import com.example.set.controller.AppControllerHolder;

/**
 * The single player game screen class
 * A class implementing the ui for the single player game screen.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class SinglePlayerGameScreen extends GameScreen {

    /**
     * onCreate method of the gameScreen, called when a game starts
     *
     * @param savedInstanceState
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppController appController = AppControllerHolder.getAppController();
        boolean newGame = true;
        boolean shortGame = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newGame = bundle.getBoolean("newGame");
            if (newGame) {
                shortGame = bundle.getBoolean("shortGame");
            }
        }
        if (newGame) {
            appController.createNewSinglePlayerGame(this, shortGame);
        }
        gameController = appController.getSinglePlayerGameController();
        if (newGame) {
            gameController.startGame();
        } else {
            gameController.resume(this);
        }
    }

    /**
     * Method called when the game is over
     *
     * @param shortGame if the game is a short game
     * @param points    the points the player received
     * @param duration  the time the game took
     * @param startTime the time the game started
     * @param deduction if the deduction rule was enabled
     * @author Linus Kurze
     */
    public void gameOver(boolean shortGame, int points, long duration, long startTime, boolean deduction) {
        Intent intentES = new Intent();
        intentES.setClass(this, GameEndScreen.class);
        intentES.putExtra("gameMode", getResources().getString(R.string.single_player));
        intentES.putExtra("gameType", gameTypeToString(shortGame));
        intentES.putExtra("points", points);
        intentES.putExtra("duration", timeToString(duration));
        intentES.putExtra("startTime", timestampToString(startTime));
        intentES.putExtra("rules", rulesToString(deduction));
        intentES.putExtra("shortGame", shortGame);
        startActivity(intentES);
        finish();
    }

    /**
     * Method called when the pause screen should be opened
     *
     * @param shortGame if the game is a short game
     * @param points    the points the player received
     * @param cardsLeft the cards left on the stack
     * @param duration  the time the game took
     * @param startTime the time the game started
     * @param deduction if the deduction rule was enabled
     * @author Linus Kurze
     */
    public void openPause(boolean shortGame, int points, int cardsLeft, long duration, long startTime, boolean deduction) {
        Intent intentPauseScreen = new Intent();
        intentPauseScreen.setClass(this, PauseScreen.class);
        intentPauseScreen.putExtra("gameMode", getResources().getString(R.string.single_player));
        intentPauseScreen.putExtra("gameType", gameTypeToString(shortGame));
        intentPauseScreen.putExtra("points", points);
        intentPauseScreen.putExtra("cardsLeft", cardsLeft);
        intentPauseScreen.putExtra("duration", timeToString(duration));
        intentPauseScreen.putExtra("startTime", timestampToString(startTime));
        intentPauseScreen.putExtra("rules", rulesToString(deduction));
        startActivity(intentPauseScreen);
    }

    /**
     * Method called when the ui should write the points.
     *
     * @param value the points to write
     * @author Linus Kurze
     */
    public void writePoints(int value) {
        points.setText("" + value);
    }

    /**
     * Method called when a card is clicked.
     *
     * @param x    the x position of the card
     * @param y    the y position of the card
     * @param view the view of the card
     * @author Linus Kurze
     */
    public void onCardClicked(int x, int y, View view) {
        cardSelected(x, y, view);
    }

    /**
     * Converts rules in seconds as long to a string.
     *
     * @param deduction if the deduction is active
     * @return the rules as readable String
     * @author Linus Kurze
     */
    private String rulesToString(boolean deduction) {
        String rules = getResources().getString(R.string.deduction) + ": ";
        if (deduction) {
            rules += getResources().getString(R.string.switchOn);
        } else {
            rules += getResources().getString(R.string.switchOff);
        }
        return rules;
    }
}
