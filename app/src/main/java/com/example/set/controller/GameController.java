package com.example.set.controller;

import com.example.set.model.Card;
import com.example.set.model.Game;
import com.example.set.model.MultiPlayerGame;
import com.example.set.model.Player;
import com.example.set.model.Rules;
import com.example.set.model.SinglePlayerGame;

import java.util.ArrayList;

public abstract class GameController {
    protected Game game;

    abstract void newGame();

    protected Rules getCurrentRules() {
        //TODO: read current rules wand write them
        return new Rules(true,true,true,10);
    }

    protected void updateCards() {
        ArrayList<Card> cards = game.getTableCards();
        //TODO: write to UI
    }

    abstract protected void updateScore();

    abstract protected void gameEndScreen();
}
