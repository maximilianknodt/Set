package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;

/**
 * @author Maximilian Knodt
 */
public class GameScreen extends AppCompatActivity {
    private static final int rows = 3;
    private RecyclerView rvList;

    /**
     * Class to handle onClickListener and to commit necessary information to the CardColumnRecyclerViewAdapter
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

    /**
     * Method to create a new '2D' array of the parameter
     * @param values onedimensional array with the values for the cards
     * @return '2D' array with the values for the cards customised for the ViewHolder of the RecyclerViewAdapter to fit to the column layout
     */
    private int[][] cardValues(int[] values){
        int[][] result = new int[values.length/rows][rows];
        int counterColumn = -1;
        int counterRow = -1;

        for(int i = 0; i < values.length; i++){
            if(values[i] % 3 == 0){
                counterColumn++;
                counterRow = 0;
            } else {
                counterRow++;
            }
            result[counterColumn][counterRow] = values[i];
        }
        return result;
    }

    //TODO: ueberlegen, ob dieser Codeteil in eigener Klasse ausgegliedert bleiben soll
    private void envokeRecyclerView(){
        //TODO: Ersetzten des Platzhalter durch Array mit den korrekten Kartendaten
        int[] platzhalter = new int[21];
        for(int i = 0; i < platzhalter.length; i++) platzhalter[i] = i;

        this.rvList = findViewById(R.id.recyclerView_Game_Field);
        // LinearLayout will show the Data in a horizontal List and will lay out from start to end ( = false)
        this.rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(),this.cardValues(platzhalter)));
        this.rvList.getAdapter();
    }
}
