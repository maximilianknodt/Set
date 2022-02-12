package com.example.set.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;
import com.example.set.model.Card;
import com.example.set.model.Color;
import com.example.set.model.Count;

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

    /**
     * Constructor CardColumnRecyclerViewAdapter
     * @param context
     * @param cards int Array with Values for the Cards
     */
    public CardColumnRecyclerViewAdapter(Context context, Card[][] cards){
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
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
        View view = inflater.inflate(R.layout.game_screen_card_column, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        //holder.cardOne.setText(Integer.toString(cards[position][0]));
        //holder.cardTwo.setText(Integer.toString(cards[position][1]));
        //holder.cardThree.setText(Integer.toString(cards[position][2]));

        //holder.cardOne.findViewById(R.id.cardTop_imageView_Top).setVisibility(View.VISIBLE);

        /**
        holder.test(holder.cardOne, this.cards[position][0]);
        holder.test(holder.cardTwo, this.cards[position][1]);
        holder.test(holder.cardThree, this.cards[position][2]);
*/

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
    // ------- Inner Class -------
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
        private void test(LinearLayout linearlayout, Card card){
            Count count = card.getCount();
            switch(count){
                case ONE: linearlayout.findViewById(R.id.cardTop_imageView_Top).setVisibility(View.VISIBLE);
                    break;
                case TWO: ImageView iv = linearlayout.findViewById(R.id.cardTop_imageView_Top);
                        iv.setVisibility(View.VISIBLE);
                    linearlayout.findViewById(R.id.cardTop_imageView_Middle).setVisibility(View.VISIBLE);
                    break;
                case THREE: linearlayout.findViewById(R.id.cardTop_imageView_Top).setVisibility(View.VISIBLE);
                    linearlayout.findViewById(R.id.cardTop_imageView_Middle).setVisibility(View.VISIBLE);
                    linearlayout.findViewById(R.id.cardTop_imageView_Bottom).setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
         */

        private int symbolColor(Card card){
            int colorValue = 0;
            Color color = card.getColor();
            switch(color){
                case RED: colorValue = 2550000;
                    break;
                case GREEN: colorValue = 0025500;
                    break;
                case BLUE: colorValue = 0000255;
                    break;
                default:
                    break;
            }
            return colorValue;
        }

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
                default:
                    break;
            }
            return countValue;
        }

        private void drawCard(Context context,LinearLayout linearlayout, Card card){
            int count = this.symbolCount(card);
            int color = 0;
            //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            for(int i = 0; i < count; i++){
                ImageView symbol = new ImageView(context);
                symbol.setImageResource(R.drawable.card_item_diamond_empty);
                //symbol.setLayoutParams(params);
                linearlayout.addView(symbol);
            }

        }
    }
}
