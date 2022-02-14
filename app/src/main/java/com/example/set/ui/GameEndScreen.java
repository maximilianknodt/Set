package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

/**
 * @author Maximilian Knodt
 */
public class GameEndScreen extends AppCompatActivity {
    /**
     * onCreate method of the gameEndScreen, called when the game is finished
     *
     * @param savedInstanceState
     *
     * @author Maximilian Knodt
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_screen);

        Button btnNewGame = findViewById(R.id.button_New_Game);
        Button btnMenu = findViewById(R.id.button_Menu);
        TextView tvWinner = findViewById(R.id.textView_Game_End_Winner_Body);
        TextView tvPoints = findViewById(R.id.textView_Game_End_Points_Body);
        TextView tvTime = findViewById(R.id.textView_Game_End_Time_Body);
        TextView tvStart = findViewById(R.id.textView_Game_End_Start_Body);
        TextView tvRules = findViewById(R.id.textView_Game_End_Rules_Body);

        btnNewGame.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Game_screen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, GameEndScreen.class);
            startActivity(intentSettings);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Start_Screen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, GameEndScreen.class);
            startActivity(intentSettings);
        });
    }
}
