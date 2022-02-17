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

        Button btnNewGame = findViewById(R.id.button_Game_End_Screen_New_Game);
        Button btnMenu = findViewById(R.id.button_Game_End_Screen_Menu);
        TextView tvType = findViewById(R.id.textView_Game_End_Screen_Type_Body);
        TextView tvPoints = findViewById(R.id.textView_Game_End_Screen_Points_Body);
        TextView tvTime = findViewById(R.id.textView_Game_End_Screen_Time_Body);
        TextView tvStart = findViewById(R.id.textView_Game_End_Screen_Start_Body);
        TextView tvRules = findViewById(R.id.textView_Game_End_Screen_Rules_Body);

        Bundle bundle = getIntent().getExtras();
        boolean shortGame = false;

        if (bundle != null) {
            tvType.setText(bundle.getString("gameType"));
            tvPoints.setText(""+bundle.getInt("points"));
            tvTime.setText(bundle.getString("duration"));
            tvStart.setText(bundle.getString("startTime"));
            tvRules.setText(bundle.getString("rules"));
            shortGame = bundle.getBoolean("shortGame");
        }

        if(shortGame) {
            btnNewGame.setText(R.string.new_short_game);
        } else {
            btnNewGame.setText(R.string.new_normal_game);
        }

        final boolean finalShortGame = shortGame;
        btnNewGame.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Game_screen");

            Intent intentSP = new Intent();
            intentSP.setClass(this, SinglePlayerGameScreen.class);
            intentSP.putExtra("newGame", true);
            intentSP.putExtra("shortGame", finalShortGame);
            startActivity(intentSP);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Start_Screen");

            Intent intentStart = new Intent();
            intentStart.setClass(this, HomeScreen.class);
            intentStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentStart);
        });
    }
}
