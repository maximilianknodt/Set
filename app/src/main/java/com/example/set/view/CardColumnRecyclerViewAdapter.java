package com.example.set.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;
import com.example.set.model.Card;
import com.example.set.model.Color;
import com.example.set.model.Count;
import com.example.set.model.Filling;
import com.example.set.model.Shape;

//Source: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

/**
 * The RecyclerViewAdapter for the Card Columns to display on the Game Screen
 *
 * @author Maximilian Knodt
 */
public class CardColumnRecyclerViewAdapter extends RecyclerView.Adapter<CardColumnRecyclerViewAdapter.ViewHolder>{
    // Contains the Data for the RecyclerView
    private Card cards[][];
    private LayoutInflater inflater;
    private GameScreen gameScreen;

    /**
     * Constructor CardColumnRecyclerViewAdapter
     * @param context
     * @param gameScreen
     * @param cards int Array with Values for the Cards
     */
    public CardColumnRecyclerViewAdapter(Context context, GameScreen gameScreen, Card[][] cards){
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
        this.gameScreen = gameScreen;
    }

    /**
     * Creates a new View for every Card Column
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = this.inflater.inflate(R.layout.game_screen_card_column, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.cardOne.setOnClickListener(view -> {
            gameScreen.onCardClicked(position, 0, view);
        });
        holder.cardTwo.setOnClickListener(view -> {
            gameScreen.onCardClicked(position, 1, view);
        });
        holder.cardThree.setOnClickListener(view -> {
            gameScreen.onCardClicked(position, 2, view);
        });

        Context context = this.inflater.getContext();
        holder.drawCard(context, holder.cardOne, this.cards[position][0]);
        holder.drawCard(context, holder.cardTwo, this.cards[position][1]);
        holder.drawCard(context, holder.cardThree, this.cards[position][2]);
    }

    /**
     * Size of the RecyclerView Dataset
     *
     * @return int
     */
    @Override
    public int getItemCount(){ return this.cards.length; }

    /**
     * Inner Class to handle the Elements within the Views that are used by the RecyclerViewAdapter (Outer Class)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final LinearLayout cardOne;               // TODO: Durch "Kartendatei" ersetzen
        protected final LinearLayout cardTwo;
        protected final LinearLayout cardThree;

        /**
         * Constructor ViewHolder
         *
         * @param view
         */
        public ViewHolder(View view){
            super(view);

            this.cardOne = view.findViewById(R.id.cardTop);
            this.cardTwo = view.findViewById(R.id.cardMiddle);
            this.cardThree = view.findViewById(R.id.cardBottom);
        }

        /**
         * Getter
         * Returns the drawable for a card.
         *
         * @return drawable for a card
         *
         * @author Linus Kurze
         */
        private int symbolImage(Card card){
            int image = 0;
            Shape shape = card.getShape();
            Filling filling = card.getFilling();
            switch(shape){
                case DIAMOND:
                    switch(filling){
                        case FULL:
                            image = R.drawable.card_item_diamond_full;
                            break;
                        case HALF_FULL:
                            image = R.drawable.card_item_diamond_half_full;
                            break;
                        case EMPTY:
                            image = R.drawable.card_item_diamond_empty;
                            break;
                    }
                    break;
                case OVAL:
                    switch(filling){
                        case FULL:
                            image = R.drawable.card_item_oval_full;
                            break;
                        case HALF_FULL:
                            image = R.drawable.card_item_oval_half_full;
                            break;
                        case EMPTY:
                            image = R.drawable.card_item_oval_empty;
                            break;
                    }
                    break;
                case WAVE:
                    switch(filling){
                        case FULL:
                            image = R.drawable.card_item_wave_full;
                            break;
                        case HALF_FULL:
                            image = R.drawable.card_item_wave_half_full;
                            break;
                        case EMPTY:
                            image = R.drawable.card_item_wave_empty;
                            break;
                    }
                    break;
            }
            return image;
        }

        /**
         * Getter
         * Returns the color for a card.
         *
         * @return color for a card
         *
         * @author Maximilian Knodt
         * @author Linus Kurze
         */
        private int symbolColor(Card card){
            int colorValue = 0;
            Color color = card.getColor();
            switch(color){
                case RED: colorValue = R.color.card_red;
                    break;
                case GREEN: colorValue = R.color.card_green;
                    break;
                case BLUE: colorValue = R.color.card_blue;
                    break;
            }
            return colorValue;
        }

        /**
         * Getter
         * Returns the symbol count for a card.
         *
         * @return symbol count for a card
         *
         * @author Maximilian Knodt
         */
        private int symbolCount(Card card){
            int countValue = 0;
            Count count = card.getCount();
            switch(count){
                case ONE: countValue = 1;
                    break;
                case TWO: countValue = 2;
                    break;
                case THREE: countValue = 3;
                    break;
            }
            return countValue;
        }

        /**
         * Draws a card.
         *
         * @author Maximilian Knodt
         * @author Linus Kurze
         *
         * @param context
         * @param linearlayout the layout that is the shown card container
         * @param card the data for the cards to be drawn
         */
        private void drawCard(Context context,LinearLayout linearlayout, Card card){
            int count = this.symbolCount(card);
            int color = this.symbolColor(card);
            int image = this.symbolImage(card);

            for(int i = 0; i < count; i++){
                ImageView symbol = new ImageView(context);

                Drawable drawable = AppCompatResources.getDrawable(context, image);
                symbol.setImageDrawable(drawable);
                symbol.setColorFilter(ContextCompat.getColor(context, color)); // replaced drawable.setTint(ContextCompat.getColor(context, color)); because it does not work on nexus 4 api 28

                int height = (int)((75-2*7) / 3 * context.getResources().getDisplayMetrics().density);
                int width = (int)((55-2*7) * context.getResources().getDisplayMetrics().density);

                symbol.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                linearlayout.addView(symbol);
            }
        }
    }
}
