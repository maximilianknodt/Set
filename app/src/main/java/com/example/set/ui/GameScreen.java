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

        // -------- RECYCLERVIEW HANDLING --------
        int[] platzhalter = new int[4];
        for(int i = 0; i < platzhalter.length; i++) platzhalter[i] = i + 1;

        this.rvList = findViewById(R.id.recyclerView_Game_Field);
        // LinearLayout will show the Data in a horizontal List and will lay out from start to end ( = false)
        this.rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(),platzhalter));
        this.rvList.getAdapter();

    }
}
