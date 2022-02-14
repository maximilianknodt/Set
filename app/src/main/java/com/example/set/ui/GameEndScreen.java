package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.set.R;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        TextView tvType = findViewById(R.id.textView_Game_End_Type_Body);
        TextView tvPoints = findViewById(R.id.textView_Game_End_Points_Body);
        TextView tvTime = findViewById(R.id.textView_Game_End_Time_Body);
        TextView tvStart = findViewById(R.id.textView_Game_End_Start_Body);
        TextView tvRules = findViewById(R.id.textView_Game_End_Rules_Body);

        Bundle bundle = getIntent().getExtras();
        boolean shortGame = false;

        if (bundle != null) {
            shortGame = bundle.getBoolean("shortGame");
            String type = getResources().getString(R.string.singleplayer) + " ";
            if (shortGame) {
                type += getResources().getString(R.string.short_game);
            } else {
                type += getResources().getString(R.string.normal_game);
            }
            tvType.setText(type);
            tvPoints.setText(""+bundle.getInt("points"));
            tvTime.setText(bundle.getString("duration"));
            tvStart.setText(timestampToString(bundle.getLong("startTime")));
            String rules = getResources().getString(R.string.deduction) + ": ";
            if (bundle.getBoolean("deduction")) {
                rules += getResources().getString(R.string.switchOn);
            } else {
                rules += getResources().getString(R.string.switchOff);
            }
            tvRules.setText(rules);
        }

        final boolean finalShortGame = shortGame;
        btnNewGame.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Game_screen");

            Intent intentSP = new Intent();
            intentSP.setClass(this, GameScreen.class);
            intentSP.putExtra("shortGame", finalShortGame);
            startActivity(intentSP);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Start_Screen");

            Intent intentStart = new Intent();
            intentStart.setClass(this, StartScreen.class);
            intentStart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentStart);
        });
    }

    /**
     * Convertes a time stamp to a string
     *
     * @param timeStamp the time stamp to convert
     * @return the time stamp as string
     *
     * @author Linus Kurze
     */
    public String timestampToString(long timeStamp){
        Date date = new Date(timeStamp);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }
}
