package com.example.set.controller;

import com.example.set.model.MultiPlayerGame;
import com.example.set.model.Player;

public class MultiPlayerGameController extends GameController {
    private Player[] players;
    private int currentPlayerIndex;

    MultiPlayerGameController (String[] names) {
        super();
        currentPlayerIndex = -1;

        players = new Player[names.length];
        for(int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i]);
        }

        game = new MultiPlayerGame(players, getCurrentRules());
    }

    @Override
    protected void writeScore() {
        for(Player player : players) {
            int score = player.getSetAmount();
            //TODO: write to UI
        }
    }

    @Override
    protected void writeEndScreen() {
        //TODO: write to UI game over
        Player winner = players[0];
        for(int i = 1; i < players.length; i++) {
            if(players[i].getSetAmount() > winner.getSetAmount()) {
                winner = players[i];
            }
        }
        //TODO: write winner to UI
        writeGameInfo();
    }

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

    @Override
    protected void periodicallyUpdate() {
        updateTimer();
        if (currentPlayerIndex != -1) {
            long selectSetTimeLeft = ((MultiPlayerGame)game).getTakeSetTimeLeft();
            if (((MultiPlayerGame)game).isTakeSetTimeOver()) {
                ((MultiPlayerGame)game).punishPlayer(players[currentPlayerIndex]);
                //TODO: message via UI
            }
            //TODO: write to UI
        }
    }

    String[] getPlayerNames() {
        String[] names = new String[players.length];
        for(int i = 0; i < players.length; i++) {
            names[i] = players[i].getName();
        }
        return names;
    }

    boolean[] getPlayerExposed() {
        boolean[] exposures = new boolean[players.length];
        for(int i = 0; i < players.length; i++) {
            exposures[i] = players[i].isExposed();
        }
        return exposures;
    }

    boolean setPressed(int playerIndex) {
        currentPlayerIndex = playerIndex;
        return ((MultiPlayerGame)game).set(players[currentPlayerIndex]);
    }

    void takeSetPressed(int position1, int position2, int position3) {
        if(currentPlayerIndex > -1) {
            ((MultiPlayerGame) game).takeCards(players[currentPlayerIndex], position1, position2, position3);
            updateCards();
            writeScore();
            currentPlayerIndex = -1;
            if (game.isOver()) {
                writeEndScreen();
            }
        }
    }
}
