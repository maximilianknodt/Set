package com.example.set.view;

import android.os.Bundle;
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
import com.example.set.controller.GameController;
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
public abstract class GameScreen extends AppCompatActivity {
    private static final int ROW_COUNT = 3;

    protected RecyclerView rvList;
    protected Button takeSet;
    protected TextView points;
    protected GameController gameController;
    protected LinkedList<CardData> selectedCards;
    private TextView timer;
    private TextView cardsLeft;

    /**
     * onCreate method of the gameScreen, called when a game starts
     *
     * @param savedInstanceState
     * @author Maximilian Knodt
     * @author Linus Kurze
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        ImageButton btnSettings = this.findViewById(R.id.imageButton_Game_Pause);
        takeSet = this.findViewById(R.id.button_Game_Set);

        // -------- set Elements --------
        rvList = findViewById(R.id.recyclerView_Game_Field);
        timer = findViewById(R.id.duration_content);
        cardsLeft = findViewById(R.id.cards_left_content);
        points = findViewById(R.id.points_content);

        selectedCards = new LinkedList<>();

        // --------- PAUSE ---------
        btnSettings.setOnClickListener(v -> {
            gameController.pauseScreen();
        });

        // -------- takeSet onClick Listener --------
        takeSet.setOnClickListener(view -> {
            if (selectedCards.size() == 3) {
                CardData cardData = selectedCards.removeLast();
                int card1 = cardData.getX() * 3 + cardData.getY();
                cardData = selectedCards.removeLast();
                int card2 = cardData.getX() * 3 + cardData.getY();
                cardData = selectedCards.removeLast();
                int card3 = cardData.getX() * 3 + cardData.getY();

                if (gameController.takeSetPressed(card1, card2, card3)) {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_successful, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this.getBaseContext(), R.string.message_take_set_incorrect, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this.getBaseContext(), R.string.message_select_3_cards, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method called when the activity is paused.
     *
     * @author Linus Kurze
     */
    @Override
    public void onPause() {
        super.onPause();
        gameController.pause(this);
    }

    /**
     * Method called when the activity is resumed.
     *
     * @author Linus Kurze
     */
    @Override
    public void onResume() {
        super.onResume();
        gameController.resume();
    }

    /**
     * Method called when a card is clicked.
     *
     * @param x    the x position of the card
     * @param y    the y position of the card
     * @param view the view of the card
     * @author Linus Kurze
     */
    public abstract void onCardClicked(int x, int y, View view);

    /**
     * Method changing the card selection with the selected card.
     *
     * @param x    the x position of the card
     * @param y    the y position of the card
     * @param view the view of the card
     * @author Linus Kurze
     */
    protected void cardSelected(int x, int y, View view) {
        CardData current = new CardData(x, y, view);
        if (selectedCards.contains(current)) {
            unselectCard(view);
            selectedCards.remove(current);
        } else {
            if (selectedCards.size() >= 3) {
                unselectCard(selectedCards.removeLast().getView());
            }
            selectedCards.add(current);
            selectCard(view);
        }
    }

    /**
     * Method which writes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void selectCard(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.card_selected));
    }

    /**
     * Method which removes the outline of the selected cards
     *
     * @author Linus Kurze
     */
    public void unselectCard(View view) {
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.card_background));
    }

    /**
     * Method called when the ui should write the cards
     *
     * @param cards the cards to write
     * @author Linus Kurze
     */
    public void writeCards(ArrayList<Card> cards) {
        this.envokeRecyclerView(cards);
    }

    /**
     * Method called when the ui should write the time
     *
     * @param time the time to write
     * @author Linus Kurze
     */
    public void writeTime(long time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timer.setText(timeToString(time));
            }
        });
    }

    /**
     * Method called when the ui should write the cards left
     *
     * @param value the cards left
     * @author Linus Kurze
     */
    public void writeCardsLeft(int value) {
        cardsLeft.setText("" + value);
    }

    /**
     * Converts time in seconds as long to a string.
     *
     * @param time the time to convert
     * @return the time as readable String
     * @author Linus Kurze
     */
    protected String timeToString(long time) {
        long hrs = time / 3600;
        long min = time % 3600 / 60;
        long sec = time % 3600 % 60;
        String text = "";
        if (hrs > 99) {
            text = "too long";
        } else {
            text = fillToTwoDigits(hrs) + ":" + fillToTwoDigits(min) + ":" + fillToTwoDigits(sec);
        }
        return text;
    }

    /**
     * Converts the game type to a string.
     *
     * @param shortGame if the game is a short game
     * @return the game type as readable String
     * @author Linus Kurze
     */
    protected String gameTypeToString(boolean shortGame) {
        if (shortGame) {
            return getResources().getString(R.string.short_game);
        }
        return getResources().getString(R.string.normal_game);
    }

    /**
     * Fills a number to at least 2 digits with zeros.
     *
     * @param number the number to edit
     * @return the edited number as String
     * @author Linus Kurze
     */
    protected String fillToTwoDigits(long number) {
        String string = "";
        if (number > 9) {
            string += number;
        } else {
            string += "0" + number;
        }
        return string;
    }

    /**
     * Converts a time stamp to a string
     *
     * @param timeStamp the time stamp to convert
     * @return the time stamp as string
     * @author Linus Kurze
     */
    protected String timestampToString(long timeStamp) {
        Date date = new Date(timeStamp);
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }

    /**
     * Method to create a new '2D' array of the parameter
     *
     * @param tableCards arraylist with the cards to place on the table
     * @return '2D' array with the cards customised for the ViewHolder of the RecyclerViewAdapter to fit to the column layout
     * @author Maximilian Knodt
     */
    private Card[][] alignTableCards(ArrayList<Card> tableCards) {
        Card[][] result = new Card[tableCards.size() / ROW_COUNT][ROW_COUNT];

        int counterColumn = -1;
        int counterRow = -1;

        for (int i = 0; i < tableCards.size(); i++) {
            if (i % 3 == 0) {
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
     * @author Maximilian Knodt
     */
    private void envokeRecyclerView(ArrayList<Card> cards) {
        rvList.setLayoutManager(new LinearLayoutManager(this.getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
        rvList.setAdapter(new CardColumnRecyclerViewAdapter(this.getBaseContext(), this, this.alignTableCards(cards)));
    }
}

/**
 * The card data class
 * An inner class holding the position of a card and the reference to the view of the card.
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
    private final int x;

    /**
     * the y position of the card
     */
    private final int y;

    /**
     * the id of the card
     */
    private final View view;

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