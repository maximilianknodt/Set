package com.example.set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

//Source: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

/**
 * The RecyclerViewAdapter for the Card Columns to display on the Game Screen
 *
 * @author Maximilian Knodt
 */
public class CardColumnRecyclerViewAdapter extends RecyclerView.Adapter<CardColumnRecyclerViewAdapter.ViewHolder>{
    private int cards[];                                // Contains the Data for the RecyclerView
    private LayoutInflater inflater;

    /**
     * Constructor CardColumnRecyclerViewAdapter
     *
     * @param context
     * @param cards int Array with Values for the Cards
     */
    public CardColumnRecyclerViewAdapter(Context context, int[] cards){
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
    }

    /**
     * Creates a new Views for every Card Column
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
        holder.cardOne.setText(Integer.toString(cards[position]));
        holder.cardTwo.setText(Integer.toString(cards[position]));
        holder.cardThree.setText(Integer.toString(cards[position]));
    }

    /**
     * Size of the RecyclerView Dataset
     * @return int[]
     */
    @Override
    public int getItemCount(){ return this.cards.length; }

    /**
     * Inner Class to handle the Elements within the Views that are used by the RecyclerViewAdapter (Outer Class)
     */
    // ------- Inner Class -------
    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView cardOne;               // TODO: Durch "Kartendatei" ersetzen
        protected final TextView cardTwo;
        protected final TextView cardThree;

        /**
         * Constructor ViewHolder
         *
         * @param view
         */
        public ViewHolder(View view){
            super(view);

            this.cardOne = view.findViewById(R.id.platzhalterOne);
            this.cardTwo = view.findViewById(R.id.platzhalterTwo);
            this.cardThree = view.findViewById(R.id.platzhalterThree);
        }
    }
}
