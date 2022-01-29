package com.example.set.controller;

import com.example.set.model.SinglePlayerGame;

public class SinglePlayerGameController extends GameController {

    @Override
    void newGame() {
        game = new SinglePlayerGame(getCurrentRules());
        game.startGame();
    }

    @Override
    protected void updateScore() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        //TODO: write to UI
    }

    @Override
    protected void gameEndScreen() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        long start = game.getStartTime();
        long duration = game.getDuration();
        boolean deduction = game.getRules().isSinglePlayerDeduction();
        //TODO: write to UI
    }

    void takeSetPressed(int position1, int position2, int position3) {
        ((SinglePlayerGame)game).takeCards(position1, position2, position3);
        updateCards();
        updateScore();
        if(game.isOver()) {
            gameEndScreen();
        }
    }
}
