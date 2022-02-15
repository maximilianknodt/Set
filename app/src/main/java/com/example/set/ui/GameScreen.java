package com.example.set.ui;

import com.example.set.controller.AppControlerHolder;
import com.example.set.controller.AppController;
import com.example.set.controller.SinglePlayerGameController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;
import com.example.set.model.Card;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Game screen class
 * Class which implements the ui and their function for the game
 *
 * @author Maximilian Knodt
 * @author Linus Kurze
 */
public class GameScreen extends AppCompatActivity {
    private static final int rows = 3;
    private RecyclerView rvList;
    private TextView timer;
    private TextView points;
    private TextView cardsLeft;
    private Button takeSet;

    private SinglePlayerGameController singlePlayerGameController;

    private LinkedList<CardData> lastCardPos;

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
        setContentView(R.layout.game_screen);

        ImageButton btnSettings = this.findViewById(R.id.imageButton_Game_Pause);

        // -------- set Elements --------
        rvList = findViewById(R.id.recyclerView_Game_Field);
        timer = findViewById(R.id.duration_content);
        points = findViewById(R.id.points_content);
        cardsLeft = findViewById(R.id.cards_left_content);
        lastCardPos = new LinkedList<>();

        // -------- Controller --------
        AppController appController = AppControlerHolder.getAppController();
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
        singlePlayerGameController = appController.getSinglePlayerGameController();
        if(newGame) {
            singlePlayerGameController.startGame();
        } else {
            singlePlayerGameController.resume(this);
        }

        // --------- PAUSE ---------
        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From GameScreen to PauseScreen");

            singlePlayerGameController.pauseScreen();
        });

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

                if(singlePlayerGameController.takeSetPressed(card1, card2, card3)) {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_successful, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_incorrect, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_select_3_cards, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        singlePlayerGameController.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        singlePlayerGameController.resume();
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

    /**
     * Method called when a card is clicked.
     *
     * @param x the x position of the card
     * @param y the y position of the card
     *
     * @author Linus Kurze
     */
    public void cardClicked(int x, int y, View view) {
        CardData current = new CardData(x,y,view);
        if(lastCardPos.contains(current)) {
            unselectCard(view);
            lastCardPos.remove(current);
        } else {
            if (lastCardPos.size() >= 3) {
                unselectCard(lastCardPos.removeLast().getView());
            }
            lastCardPos.add(current);
            selectCard(view);
        }
    }

    /**
     * Method which writes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void selectCard(View view){
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.card_selected));
    }

    /**
     * Method which removes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void unselectCard(View view){
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    /**
     * Method called when the ui should write the cards
     *
     * @param cards the cards to write
     *
     * @author Linus Kurze
     */
    public void setCards(ArrayList<Card> cards){
        this.envokeRecyclerView(cards);
    }

    /**
     * Method called when the ui should write the time
     *
     * @param time the time to write
     *
     * @author Linus Kurze
     */
    public void setTime(long time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.setText(timeToString(time));
            }
        });

    }

    /**
     * Method called when the ui should write the points
     *
     * @param value the points to write
     *
     * @author Linus Kurze
     */
    public void setPoints(int value){
        points.setText(""+value);
    }

    /**
     * Method called when the ui should write the cards left
     *
     * @param value the cards left
     *
     * @author Linus Kurze
     */
    public void setCardsLeft(int value){
        cardsLeft.setText(""+value);
    }


    public void openPause(boolean shortGame, int points, int cardsLeft, long duration, long startTime, boolean deduction) {
        Intent intentBreakeScreen = new Intent();
        intentBreakeScreen.setClass(this, PauseScreen.class);
        intentBreakeScreen.putExtra("gameType", gameTypeToString(shortGame));
        intentBreakeScreen.putExtra("points", points);
        intentBreakeScreen.putExtra("cardsLeft", cardsLeft);
        intentBreakeScreen.putExtra("duration", timeToString(duration));
        intentBreakeScreen.putExtra("startTime", timestampToString(startTime));
        intentBreakeScreen.putExtra("rules", rulesToString(deduction));
        startActivity(intentBreakeScreen);
    }

    /**
     * Method called when the game is over
     *
     * @param shortGame if the game is a short game
     * @param points the points the player received
     * @param duration the time the game took
     * @param startTime the time the game started
     * @param deduction if the deduction rule was enabled
     *
     * @author Linus Kurze
     */
    public void gameOver(boolean shortGame, int points, long duration, long startTime, boolean deduction) {
        Intent intentES = new Intent();
        intentES.setClass(this, GameEndScreen.class);
        intentES.putExtra("gameType", gameTypeToString(shortGame));
        intentES.putExtra("points", points);
        intentES.putExtra("duration", timeToString(duration));
        intentES.putExtra("startTime", timestampToString(startTime));
        intentES.putExtra("rules", rulesToString(deduction));
        intentES.putExtra("shortGame", shortGame);
        startActivity(intentES);
        finish();
    }

    /**
     * Converts time in seconds as long to a string.
     *
     * @param time the time to convert
     * @return the time as readable String
     *
     * @author Linus Kurze
     */
    private String timeToString(long time) {
        long hrs = time / 3600;
        long min = time % 3600 / 60;
        long sec = time % 3600 % 60;
        String text = "";
        if(hrs > 99) {
            text = "too long";
        } else {
            text = fillToTwoDigits(hrs) + ":" + fillToTwoDigits(min) + ":" + fillToTwoDigits(sec);
        }
        return text;
    }

    /**
     * Converts rules in seconds as long to a string.
     *
     * @param deduction if the deduction is active
     * @return the rules as readable String
     *
     * @author Linus Kurze
     */
    private String rulesToString(boolean deduction) {
        String rules = getResources().getString(R.string.deduction) + ": ";
        if (deduction) {
            rules += getResources().getString(R.string.switchOn);
        } else {
            rules += getResources().getString(R.string.switchOff);
        }
        return rules;
    }

    /**
     * Converts the game type to a string.
     *
     * @param shortGame if the game is a short game
     * @return the game type as readable String
     *
     * @author Linus Kurze
     */
    public String gameTypeToString(boolean shortGame) {
        String type = getResources().getString(R.string.single_player) + " ";
        if (shortGame) {
            type += getResources().getString(R.string.short_game);
        } else {
            type += getResources().getString(R.string.normal_game);
        }
        return type;
    }

    /**
     * Fills a number to at least 2 digits with zeros.
     *
     * @param number the number to edit
     * @return the edited number as String
     *
     * @author Linus Kurze
     */
    private String fillToTwoDigits(long number) {
        String string = "";
        if(number > 9) {
            string += number;
        } else {
            string+= "0" + number;
        }
        return string;
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

    /**
     * Class to handle onClickListener and to commit necessary information to the CardColumnRecyclerViewAdapter
     *
     * @param cards the cards to write on the screen
     *
     * @author Maximilian Knodt
     */
    private void envokeRecyclerView(ArrayList<Card> cards){
        rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(), this, this.alignTableCards(cards)));
    }
}

/**
 * The card position class
 * An inner class holding the position of a card.
 * <p>
 * The author is responsible for this class.
 *
 * @author Linus Kurze
 * @version 1.0
 */
class CardData {
    /**
     * the x position of the card
     */
    private int x;

    /**
     * the y position of the card
     */
    private int y;

    /**
     * the id of the card
     */
    private View view;

    /**
     * Constructor
     * Creates an instance and initializes it with the position.
     *
     * @param x the x position of the card
     * @param y the y position of the card
     */
    public CardData(int x, int y, View view) {
        this.x = x;
        this.y = y;
        this.view = view;
    }

    /**
     * Getter
     * Returns the x position.
     *
     * @return the x position
     */
    public int getX() {
        return x;
    }

    /**
     * Getter
     * Returns the y position.
     *
     * @return the y position
     */
    public int getY() {
        return y;
    }

    /**
     * Getter
     * Returns the id.
     *
     * @return the id
     */
    public View getView() {
        return view;
    }

    /**
     * Equals method
     *
     * @return if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardData cardData = (CardData) o;
        return x == cardData.x && y == cardData.y;
    }

    /**
     * hash code method
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}