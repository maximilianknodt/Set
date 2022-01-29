package com.example.set.controller;

import com.example.set.model.MultiPlayerGame;
import com.example.set.model.Player;
import com.example.set.model.Rules;
import com.example.set.model.SinglePlayerGame;

public class GameController {
    private SinglePlayerGame currentSinglePlayerGame;
    private MultiPlayerGame currentMultiPlayerGame;

    GameController() {

    }

    void createNewSinglePlayerGame() {
        currentSinglePlayerGame = new SinglePlayerGame(getCurrentRules());
    }

    void createNewMultiPlayerGame(Player[] players) {
        currentMultiPlayerGame = new MultiPlayerGame(players, getCurrentRules());
    }


    private Rules getCurrentRules() {
        //TODO: read current rules wand write them
        return new Rules(true,true,true,10);
    }
}
