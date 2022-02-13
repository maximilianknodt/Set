package com.example.set.ui;

import com.example.set.controller.AppController;
import com.example.set.controller.SinglePlayerGameController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;
import com.example.set.model.Card;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Maximilian Knodt
 */
public class GameScreen extends AppCompatActivity {
    private static final int rows = 3;
    private RecyclerView rvList;
    private TextView timer;
    private TextView points;
    private Button takeSet;

    private LinkedList<CardData> lastCardPos;

    /**
     * Class to handle onClickListener and to commit necessary information to the CardColumnRecyclerViewAdapter
     *
     * @param savedInstanceState
     */

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

        ImageButton btnSettings = this.findViewById(R.id.imageButton_Game_Settings);

        // --------- SETTINGS ---------
        btnSettings.setOnClickListener(v -> {
            Log.d("Debug", "On Click - From Gamesscreen to Settingsscreen");

            Intent intentSettings = new Intent();
            intentSettings.setClass(this, SettingsScreen.class);
            startActivity(intentSettings);
        });

        // -------- Controller --------
        AppController appController = new AppController();
        appController.createNewSinglePlayerGame(this);
        SinglePlayerGameController singlePlayerGameController = appController.getSinglePlayerGameController();


        // -------- set Elements --------
        rvList = findViewById(R.id.recyclerView_Game_Field);
        timer = findViewById(R.id.duration_content);
        points = findViewById(R.id.points_content);
        lastCardPos = new LinkedList<>();

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

                singlePlayerGameController.takeSetPressed(card1, card2, card3);
            } else {
                //TODO: ggf. Benachrichtigungen
            }
        });

        singlePlayerGameController.startGame();

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
            setCardNotSelected(view);
            lastCardPos.remove(current);
        } else {
            if (lastCardPos.size() >= 3) {
                setCardNotSelected(lastCardPos.removeLast().getView());
            }
            lastCardPos.add(current);
            setCardSelected(view);
        }
    }

    /**
     * Method which writes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void setCardSelected(View view){
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.card_selected));
    }

    /**
     * Method which removes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void setCardNotSelected(View view){
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
                timer.setText(convertTime(time));
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
     * Converts time in seconds as long to a string.
     *
     * @param time the time to convert
     * @return the time as readable String
     *
     * @author Linus Kurze
     */
    private String convertTime(long time) {
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
     * @author Linus Kurze
     */
    private void envokeRecyclerView(ArrayList<Card> cards){
        //TODO: Ersetzten des Platzhalter durch Array mit den korrekten Kartendaten
        /*ArrayList<Card> platzhalter = new ArrayList<>();
        for(int i = 0; i < 21; i++) {
            Card card1 = new Card(Color.RED, Shape.WAVE, Filling.EMPTY, Count.THREE);
            Card card2 = new Card(Color.BLUE, Shape.DIAMOND, Filling.HALF_FULL, Count.TWO);
            Card card3 = new Card(Color.GREEN, Shape.OVAL, Filling.FULL, Count.ONE);
            if(i % 3 == 0) platzhalter.add(card1);
            else if (i % 3 == 1) platzhalter.add(card2);
                    else platzhalter.add(card3);
        }
        cards = platzhalter;*/


        /*this.rvList = findViewById(R.id.recyclerView_Game_Field);
        // LinearLayout will show the Data in a horizontal List and will lay out from start to end ( = false)
        this.rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        this.rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(),this.alignTableCards(cards)));
        this.rvList.getAdapter();*/


        // LinearLayout will show the Data in a horizontal List and will lay out from start to end ( = false)
        rvList.removeAllViews();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardData cardData = (CardData) o;
        return x == cardData.x && y == cardData.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}