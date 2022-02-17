package com.example.set.controller;

import com.example.set.model.MultiPlayerGame;
import com.example.set.model.Player;
import com.example.set.ui.MultiPlayerGameScreen;

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
    MultiPlayerGameController(MultiPlayerGameScreen gameScreen, String[] names, boolean shortGame) {
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
    public void writeScore() {
        ((MultiPlayerGameScreen)gameScreen).writePoints(getPlayerPoints());
    }

    /**
     * Called when the game is over. Writes the screen at the end of a game to the UI.
     */
    @Override
    protected void gameOver() {
        ((MultiPlayerGameScreen)gameScreen).gameOver(game.isShortGame(), getPlayerPoints(), game.getDuration(), game.getStartTime(), game.getRules().isMultiPlayerDeduction(), game.getRules().isMultiPlayerSuspension(), getLeaders());
        super.gameOver();
    }

    /**
     * Contains the things to be periodically updated.
     */
    @Override
    protected void periodicallyUpdate() {
        writeDuration();
        if (isSetSelectionActive()) {
            if (((MultiPlayerGame)game).isTakeSetTimeOver()) {
                cancelSetSelection();
            }
            ((MultiPlayerGameScreen)gameScreen).writeSetSelectionTime(((MultiPlayerGame)game).getTakeSetTimeLeft());
        } else {
            ((MultiPlayerGameScreen)gameScreen).writeSetSelectionTime(-1);
        }
    }

    public void cancelSetSelection() {
        ((MultiPlayerGame)game).punishPlayer(players[currentPlayerIndex]);
        ((MultiPlayerGameScreen)gameScreen).writeSetSelectionOver();
        currentPlayerIndex = -1;
    }

    /**
     * Called when pause is clicked.
     */
    @Override
    public void pauseScreen() {
        ((MultiPlayerGameScreen)gameScreen).openPause(game.isShortGame(), getPlayerPoints(), game.getCardsLeft(), game.getDuration(), game.getStartTime(), game.getRules().isMultiPlayerDeduction(), game.getRules().isMultiPlayerSuspension());
    }

    /**
     * Called when resume is clicked.
     */
    @Override
    protected void resumeGameSpecific() {
        if (isSetSelectionActive()) {
            ((MultiPlayerGameScreen)gameScreen).writeSetSelection(players[currentPlayerIndex].getName());
        }
        writeCards();
        writeScore();
        writeCardsLeft();
    }

    /**
     * Function called when player is selected.
     *
     * @param playerIndex the index of the player being selected
     * @return
     */
    public boolean selectPlayer(int playerIndex) {
        if (((MultiPlayerGame)game).set(players[playerIndex])) {
            currentPlayerIndex = playerIndex;
            ((MultiPlayerGameScreen)gameScreen).writeSetSelection(players[currentPlayerIndex].getName());
            return true;
        }
        ((MultiPlayerGameScreen)gameScreen).writeDefaultView();
        return false;
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
        if(isSetSelectionActive()) {
            result = ((MultiPlayerGame) game).takeCards(players[currentPlayerIndex], position1, position2, position3);
            writeCards();
            writeScore();
            writeCardsLeft();
            currentPlayerIndex = -1;
            if (game.isOver()) {
                gameOver();
            }
        }
        ((MultiPlayerGameScreen)gameScreen).writeDefaultView();
        return result;
    }

    /**
     * Adds cards to the table cards.
     *
     * @return if adding the cards was possible
     */
    public boolean addCards() {
        boolean result = ((MultiPlayerGame)game).addCards();
        writeCards();
        writeCardsLeft();
        return result;
    }

    /**
     * Function called when the button set is pressed.
     */
    public void setPressed() {
        ((MultiPlayerGameScreen)gameScreen).writePlayerSelection();
    }

    /**
     * Function called when the player selection is canceled.
     */
    public void playerSelectionCanceled() {
        ((MultiPlayerGameScreen)gameScreen).writeDefaultView();
    }


    /**
     * Getter
     * Returns all players names.
     *
     * @return all players names
     */
    public String[] getPlayerNames() {
        String[] names = new String[players.length];
        for(int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    /**
     * Getter
     * Returns all players points.
     *
     * @return all players points
     */
    public int[] getPlayerPoints() {
        int[] points = new int[players.length];
        for(int i = 0; i < players.length; i++) {
            points[i] = players[i].getSetAmount();
        }
        return points;
    }

    /**
     * Getter
     * Returns if the set selection is active.
     *
     * @return if the set selection is active
     */
    public boolean isSetSelectionActive() {
        return currentPlayerIndex > -1;
    }

    /**
     * Getter
     * Returns an array of the names of the current leaders.
     *
     * @return array of names of the leaders
     */
    private String[] getLeaders() {
        Player playerWithMaxSets = players[0];
        for(int i = 1; i < players.length; i++) {
            if(players[i].getSetAmount() > playerWithMaxSets.getSetAmount()) {
                playerWithMaxSets = players[i];
            }
        }
        String[] leaders = {playerWithMaxSets.getName()};
        for(int i = 1; i < players.length; i++) {
            if(players[i].getSetAmount() == playerWithMaxSets.getSetAmount() && players[i] != playerWithMaxSets) {
                String[] temp = leaders.clone();
                leaders = new String[temp.length + 1];
                for(int j = 0; j < temp.length; j++) {
                    leaders[j] = temp[j];
                }
                leaders[temp.length] = players[i].getName();
            }
        }
        return leaders;
    }
}
