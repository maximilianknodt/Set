package com.example.set.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
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
        TextView tvWinner = findViewById(R.id.textView_Game_End_Winner_Body);
        TextView tvPoints = findViewById(R.id.textView_Game_End_Points_Body);
        TextView tvTime = findViewById(R.id.textView_Game_End_Time_Body);
        TextView tvStart = findViewById(R.id.textView_Game_End_Start_Body);
        TextView tvRules = findViewById(R.id.textView_Game_End_Rules_Body);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
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

        btnNewGame.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Game_screen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, GameScreen.class);
            startActivity(intentSettings);
        });

        btnMenu.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Games_End_Screen to Start_Screen");

            goToMainMenu();
        });
    }

    /**
     * Method called when back button is pressed.
     *
     * @author Linus Kurze
     */
    @Override
    public void onBackPressed() {
        goToMainMenu();
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

    /**
     * Starts an intent to the main menu
     *
     * @author Maximilian Knodt
     */
    public void goToMainMenu() {
        Intent intentSettings = new Intent();
        intentSettings.setClass(this, StartScreen.class);
        startActivity(intentSettings);
    }
}
