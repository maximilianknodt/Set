package com.example.set.controller;

import com.example.set.model.Card;
import com.example.set.model.Game;
import com.example.set.model.Rules;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameController {
    protected Game game;
    private Timer timer;

    private final int UPDATES_PER_SECOND = 60;

    public void startGame() {
        game.startGame();
        writeCards();
        writeScore();
        createPeriodicalTimer();
    }

    protected Rules getCurrentRules() {
        //TODO: read current rules wand write them
        return new Rules(true,true,true,10);
    }

    protected void writeCards() {
        ArrayList<Card> cards = game.getTableCards();
        //TODO: write to UI
    }

    protected void updateTimer() {
        long duration = game.getDuration();
        //TODO: write to UI
    }

    abstract protected void writeScore();

    abstract protected void writeEndScreen();

    abstract protected void writeGameInfo();

    abstract protected void periodicallyUpdate();

    protected void pause() {
        game.pause();
        timer.cancel();
        //TODO: write pause screen
        writeGameInfo();
    }

    protected abstract void resume();

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
