package com.example.set.ui;

import com.example.set.model.Color;
import com.example.set.model.Shape;
import com.example.set.model.Filling;
import com.example.set.model.Count;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;
import com.example.set.model.Card;

import java.util.ArrayList;

/**
 * @author Maximilian Knodt
 */
public class GameScreen extends AppCompatActivity {
    private static final int rows = 3;
    private RecyclerView rvList;
    private ArrayList<Card> cards;

    /**
     * Class to handle onClickListener and to commit necessary information to the CardColumnRecyclerViewAdapter
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        ImageButton btnSettings = this.findViewById(R.id.imageButton_Game_Settings);

        // --------- SETTINGS ---------
        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Gamesscreen to Settingsscreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        // -------- RECYCLERVIEW --------
        this.envokeRecyclerView();
    }


    private int[][] cardValues(ArrayList<Integer> test){
        int[][] result = new int[test.size()/rows][rows];
        int counterColumn = -1;
        int counterRow = -1;

        for(int i = 0; i < test.size(); i++){
            if(i % 3 == 0){
                counterColumn++;
                counterRow = 0;
            } else {
                counterRow++;
            }
            result[counterColumn][counterRow] = test.get(i);
        }
        return result;
    }

    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }

    /**
     * Method to create a new '2D' array of the parameter
     *
     * @param tableCards arraylist with the cards to place on the table
     * @return '2D' array with the cards customised for the ViewHolder of the RecyclerViewAdapter to fit to the column layout
     */
    private Card[][] alignTableCards(ArrayList<Card> tableCards){
        Card[][] result = new Card[tableCards.size()/rows][rows];

        int counterColumn = -1;
        int counterRow = -1;

        for(int i = 0; i < tableCards.size(); i++){
            if(i % 3 == 0){
                counterColumn++;
                counterRow = 0;
            } else {
                counterRow++;
            }
            result[counterColumn][counterRow] = tableCards.get(i);
        }
        return result;
    }


    private void envokeRecyclerView(){
        //TODO: Ersetzten des Platzhalter durch Array mit den korrekten Kartendaten
        ArrayList<Card> platzhalter = new ArrayList<>();
        for(int i = 0; i < 21; i++) {
            Card card1 = new Card(Color.RED, Shape.WAVE, Filling.EMPTY, Count.THREE);
            Card card2 = new Card(Color.BLUE, Shape.DIAMOND, Filling.HALF_FULL, Count.TWO);
            Card card3 = new Card(Color.GREEN, Shape.OVAL, Filling.FULL, Count.ONE);
            if(i % 3 == 0) platzhalter.add(card1);
            else if (i % 3 == 1) platzhalter.add(card2);
                    else platzhalter.add(card3);
        }

        this.rvList = findViewById(R.id.recyclerView_Game_Field);
        // LinearLayout will show the Data in a horizontal List and will lay out from start to end ( = false)
        this.rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(),this.alignTableCards(platzhalter)));
        this.rvList.getAdapter();
    }
}
