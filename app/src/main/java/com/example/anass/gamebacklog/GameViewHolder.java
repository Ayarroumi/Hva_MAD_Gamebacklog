package com.example.anass.gamebacklog;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.anass.gamebacklog.R;

public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView title;
    public TextView platform;
    public TextView status;
    public TextView date;
    private GameClickListerner mGameClickListener;


    public GameViewHolder(View itemView, GameClickListerner listerner) {
        super(itemView);

        title = itemView.findViewById(R.id.Title);
        platform = itemView.findViewById(R.id.Status);
        status = itemView.findViewById(R.id.Platform);
        date = itemView.findViewById(R.id.Date);
        mGameClickListener = listerner;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        mGameClickListener.gameOnClick(view,clickedPosition);
    }
}