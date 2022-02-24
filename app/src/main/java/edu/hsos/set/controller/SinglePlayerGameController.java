package edu.hsos.set.controller;


import edu.hsos.set.model.SinglePlayerGame;
import edu.hsos.set.view.SinglePlayerGameScreen;

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
     * @param singlePlayerGameScreen the singlePlayerGameScreen object for the game
     * @param shortGame              if the game should be a short game
     */
    SinglePlayerGameController(SinglePlayerGameScreen singlePlayerGameScreen, boolean shortGame) {
        super(singlePlayerGameScreen);
        game = new SinglePlayerGame(getCurrentSinglePlayerRules(), shortGame);
    }

    /**
     * Constructor
     * Calls super constructor. Initializes the game with a given instance.
     *
     * @param singlePlayerGame       the game to load
     * @param singlePlayerGameScreen the singlePlayerGameScreen object for the game
     */
    SinglePlayerGameController(SinglePlayerGame singlePlayerGame, SinglePlayerGameScreen singlePlayerGameScreen) {
        super(singlePlayerGameScreen);
        this.game = singlePlayerGame;
    }

    /**
     * Writes the score to the view.
     */
    @Override
    protected void writeScore() {
        int score = ((SinglePlayerGame) game).getSetAmount();
        ((SinglePlayerGameScreen) gameScreen).writePoints(score);
    }

    /**
     * Called when the game is over. Writes the screen at the end of a game to the view.
     */
    @Override
    protected void gameOver() {
        ((SinglePlayerGameScreen) gameScreen).gameOver(game.isShortGame(), ((SinglePlayerGame) game).getSetAmount(), game.getDuration(), game.getStartTime(), ((SinglePlayerGame) game).getRules().isPlayerDeduction());
        super.gameOver();
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
        ((SinglePlayerGameScreen) gameScreen).openPause(game.isShortGame(), ((SinglePlayerGame) game).getSetAmount(), game.getCardsLeft(), game.getDuration(), game.getStartTime(), ((SinglePlayerGame) game).getRules().isPlayerDeduction());
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
        boolean result = ((SinglePlayerGame) game).takeCards(position1, position2, position3);
        writeCards();
        writeScore();
        writeCardsLeft();
        if (game.isOver()) {
            gameOver();
        }
        return result;
    }

}
