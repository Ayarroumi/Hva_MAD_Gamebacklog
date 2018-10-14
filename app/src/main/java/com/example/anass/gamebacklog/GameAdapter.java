package com.example.anass.gamebacklog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder>{


    private Context context;
    public List<Game> listGames;
    private GameClickListerner mGameClickListerner;


    public GameAdapter(Context context, List<Game> listGames, GameClickListerner mGameClickListerner) {
        this.context = context;
        this.listGames = listGames;
        this.context = context;
        this.mGameClickListerner = mGameClickListerner;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_cell,viewGroup,false);
        return new GameViewHolder(view, mGameClickListerner);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int position) {

        final Game game = listGames.get(position);
        gameViewHolder.title.setText(game.getTitle());
        gameViewHolder.platform.setText(game.getPlatform());
        gameViewHolder.status.setText(game.getStatus());
        gameViewHolder.date.setText(game.getDate());
    }

    @Override
    public int getItemCount() {
        return listGames.size();
    }

    public void swapList (List<Game> newList) {
        listGames = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

}
