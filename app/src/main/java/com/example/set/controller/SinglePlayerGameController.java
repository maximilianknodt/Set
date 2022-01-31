package com.example.set.controller;

import com.example.set.model.SinglePlayerGame;

public class SinglePlayerGameController extends GameController {

    SinglePlayerGameController () {
        super();
        game = new SinglePlayerGame(getCurrentRules());
    }

    @Override
    protected void writeScore() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        //TODO: write to UI
    }

    @Override
    protected void writeEndScreen() {
        //TODO: write to ui game over
        writeGameInfo();
    }

    @Override
    protected void writeGameInfo() {
        int score = ((SinglePlayerGame)game).getSetAmount();
        long start = game.getStartTime();
        long duration = game.getDuration();
        boolean deduction = game.getRules().isSinglePlayerDeduction();
        //TODO: write to UI
    }

    @Override
    protected void periodicallyUpdate() {
        updateTimer();
    }

    @Override
    protected void resume() {
        game.resume();
        createPeriodicalTimer();
        writeCards();
        writeScore();
    }

    void takeSetPressed(int position1, int position2, int position3) {
        ((SinglePlayerGame)game).takeCards(position1, position2, position3);
        writeCards();
        writeScore();
        if(game.isOver()) {
            writeEndScreen();
        }
    }

}
