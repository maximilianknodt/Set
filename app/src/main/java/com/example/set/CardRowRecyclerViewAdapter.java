package com.example.set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Quelle: https://developer.android.com/guide/topics/ui/layout/recyclerview#java

public class CardRowRecyclerViewAdapter extends RecyclerView.Adapter<CardRowRecyclerViewAdapter.ViewHolder>{
    private int cards[];                                // Contains the Data for the RecyclerView
    private LayoutInflater inflater;

    public CardRowRecyclerViewAdapter(Context context,int[] cards){
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = inflater.inflate(R.layout.game_screen_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.cardOne.setText(Integer.toString(cards[position]));
        holder.cardTwo.setText(Integer.toString(cards[position]));
        holder.cardThree.setText(Integer.toString(cards[position]));
    }

    @Override
    public int getItemCount(){ return this.cards.length; }

    // ------- Inner Class -------
    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView cardOne;               // TODO: Durch "Kartendatei" ersetzen
        protected final TextView cardTwo;
        protected final TextView cardThree;

        public ViewHolder(View view){
            super(view);

            this.cardOne = view.findViewById(R.id.platzhalterOne);
            this.cardTwo = view.findViewById(R.id.platzhalterTwo);
            this.cardThree = view.findViewById(R.id.platzhalterThree);
        }
    }
}
