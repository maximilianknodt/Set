package com.example.set.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.set.R;


public class PlayerPointsRecyclerView extends RecyclerView.Adapter<PlayerPointsRecyclerView.ViewHolder>{
    private String[][] playerData;
    private LayoutInflater inflater;

    public PlayerPointsRecyclerView(Context context, String[][] data){
        this.inflater = LayoutInflater.from(context);
        this.playerData = data;
    }

    /**
     * Creates a new View for every player
     *
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = this.inflater.inflate(R.layout.breake_screen, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     *
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.playerName.setText(this.playerData[position][0]);
        holder.playerScore.setText(this.playerData[position][1]);
    }

    /**
     * Returns the size of the playerData = count of Players
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return this.playerData.length;
    }

    /**
     * Inner Class to handle the Elements within the Views that are used by the RecyclerViewAdapter
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        protected final TextView playerName;
        protected final TextView playerScore;

        public ViewHolder(View view){
            super(view);
            this.playerName = view.findViewById(R.id.textView_Breake_Screen_PlayerPoints_Name);
            this.playerScore = view.findViewById(R.id.textView_Breake_Screen_PlayerPoints_Score);
        }
    }
}
