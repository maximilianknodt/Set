package com.example.set.controller;

import com.example.set.model.MultiPlayerGame;
import com.example.set.model.Player;
import com.example.set.ui.SinglePlayerGameScreen;

/**
 * The multi player game controller class
 * A class holding the logic of multi player games.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
public class MultiPlayerGameController extends GameController {
    /**
     * the players for the game
     */
    private Player[] players;

    /**
     * the player index who is selected for selecting a set (if there is no selected player: -1)
     */
    private int currentPlayerIndex;

    /**
     * Constructor
     * Calls super constructor. Initializes the players, the currentPlayerIndex and the game with a new instance.
     *
     * @param gameScreen the gameScreen object for the game
     * @param names names of the players
     * @param shortGame if the game should be a short game
     */
    MultiPlayerGameController(SinglePlayerGameScreen gameScreen, String[] names, boolean shortGame) {
        super(gameScreen);
        currentPlayerIndex = -1;

        players = new Player[names.length];
        for(int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i]);
        }

        game = new MultiPlayerGame(players, getCurrentRules(), shortGame);
    }

    /**
     * Writes the score to the UI.
     */
    @Override
    protected void writeScore() {
        for(Player player : players) {
            int score = player.getSetAmount();
            //TODO: write to UI
        }
    }

    /**
     * Called when the game is over. Writes the screen at the end of a game to the UI.
     */
    @Override
    protected void gameOver() {
        //TODO: write to UI game over
        Player winner = players[0];
        for(int i = 1; i < players.length; i++) {
            if(players[i].getSetAmount() > winner.getSetAmount()) {
                winner = players[i];
            }
        }
        //TODO: write winner to UI
        writeGameInfo();
        super.gameOver();
    }

    /**
     * Writes the game info to the UI.
     */
    @Override
    protected void writeGameInfo() {
        for(Player player : players) {
            int score = player.getSetAmount();
            //TODO: write to UI
        }
        long start = game.getStartTime();
        long duration = game.getDuration();
        boolean deduction = game.getRules().isMultiPlayerDeduction();
        boolean exposure = game.getRules().isMultiPlayerExposure();
        //TODO: write to UI
    }

    /**
     * Contains the things to be periodically updated.
     */
    @Override
    protected void periodicallyUpdate() {
        writeDuration();
        if (currentPlayerIndex != -1) {
            long selectSetTimeLeft = ((MultiPlayerGame)game).getTakeSetTimeLeft();
            if (((MultiPlayerGame)game).isTakeSetTimeOver()) {
                ((MultiPlayerGame)game).punishPlayer(players[currentPlayerIndex]);
                //TODO: message via UI
            }
            //TODO: write to UI
        }
    }

    /**
     * Called when pause is clicked.
     */
    @Override
    public void pauseScreen() {
        //TODO: write pause screen
        //TODO: abstract und in die jeweiligen klassen wegen write Game Info
        writeGameInfo();
    }

    /**
     * Called when resume is clicked.
     */
    @Override
    protected void resumeGameSpecific() {
        if (currentPlayerIndex != -1) {
            writeSetSelection();
        } else {
            writeCards();
            writeScore();
            writeCardsLeft();
        }
    }

    /**
     * Function called when button set is pressed.
     */
    void setPressed() {
        writePlayerSelection();
    }

    /**
     * Function called when player is selected.
     *
     * @param playerIndex the index of the player beeing selected
     */
    void selectPlayer(int playerIndex) {
        currentPlayerIndex = playerIndex;
        if (((MultiPlayerGame)game).set(players[currentPlayerIndex])) {
            writeSetSelection();
        } else {
            // TODO: notify via UI that player is exposed
            closeSetSelection();
        }
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
        boolean result = false;
        if(currentPlayerIndex > -1) {
            result = ((MultiPlayerGame) game).takeCards(players[currentPlayerIndex], position1, position2, position3);
            writeCards();
            writeScore();
            writeCardsLeft();
            currentPlayerIndex = -1;
            if (game.isOver()) {
                gameOver();
            }
        }
        return result;
    }

    /**
     * Writes the players to select to the UI.
     */
    private void writePlayerSelection() {
        String[] names = getPlayerNames();
        boolean[] exposures = getPlayersExposed();
        //TODO: Write to UI
    }

    /**
     * Writes the set selection to select to the UI.
     */
    private void writeSetSelection() {
        String name = players[currentPlayerIndex].getName();
        //TODO: Write to UI
    }

    /**
     * Closes the set selection to select on the UI.
     */
    private void closeSetSelection() {
        //TODO: Close in UI
    }

    /**
     * Getter
     * Returns all players names.
     *
     * @return all players names
     */
    private String[] getPlayerNames() {
        String[] names = new String[players.length];
        for(int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    /**
     * Getter
     * Returns for each player if he is exposed.
     *
     * @return exposure for each player
     */
    private boolean[] getPlayersExposed() {
        boolean[] exposures = new boolean[players.length];
        for(int i = 0; i < players.length; i++) {
            exposures[i] = players[i].isExposed();
        }
        return exposures;
    }
}
