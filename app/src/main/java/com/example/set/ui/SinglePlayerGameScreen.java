package com.example.set.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.set.R;
import com.example.set.controller.AppController;
import com.example.set.controller.AppControllerHolder;

import java.util.LinkedList;

public class SinglePlayerGameScreen extends GameScreen {
    /**
     * onCreate method of the gameScreen, called when a game starts
     *
     * @param savedInstanceState
     *
     * @author Maximilian Knodt
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // -------- Controller --------
        AppController appController = AppControllerHolder.getAppController();
        boolean newGame = true;
        boolean shortGame = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newGame = bundle.getBoolean("newGame");
            if(newGame) {
                shortGame = bundle.getBoolean("shortGame");
            }
        }
        if(newGame) {
            appController.createNewSinglePlayerGame(this, shortGame);
        }
        gameController = appController.getSinglePlayerGameController();
        if(newGame) {
            gameController.startGame();
        } else {
            gameController.resume(this);
        }

        // -------- set onClick Listener --------
        takeSet = findViewById(R.id.button_Game_Set);
        takeSet.setOnClickListener(view -> {
            if(lastCardPos.size() == 3) {
                CardData cardData = lastCardPos.removeLast();
                int card1 = cardData.getX()*3 + cardData.getY();
                cardData = lastCardPos.removeLast();
                int card2 = cardData.getX()*3 + cardData.getY();
                cardData = lastCardPos.removeLast();
                int card3 = cardData.getX()*3 + cardData.getY();

                if(gameController.takeSetPressed(card1, card2, card3)) {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_successful, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_incorrect, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_select_3_cards, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
