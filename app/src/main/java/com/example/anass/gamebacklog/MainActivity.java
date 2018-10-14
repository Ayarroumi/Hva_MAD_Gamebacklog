package com.example.anass.gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    static AppDatabase db;
    public List<Game> mGames = new ArrayList<>();
    private GameAdapter mAdapter;
    private RecyclerView mGameRecyclerView;
    private FloatingActionButton fab;
    GameClickListerner listener;

    public final static int TASK_GET_ALL_GAMES = 0;
    public final static int TASK_DELETE_GAME = 1;
    public final static int TASK_UPDATE_GAME = 2;
    public final static int TASK_INSERT_GAME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        System.out.println(getDatabasePath("game_db").getAbsolutePath() + "!!!!!!!!!!");
        new GameAsyncTask(TASK_GET_ALL_GAMES).execute();

        mGames = db.gameDao().getAllGames();

        RecyclerView mGameRecyclerView = findViewById(R.id.RecylcerViewGames);
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        mGameRecyclerView.setLayoutManager(mLayoutManager);


        listener = new GameClickListerner() {
            @Override
            public void gameOnClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, EditGameActivity.class);
                intent.putExtra("game", mGames.get(position));
                MainActivity.this.startActivityForResult(intent, 45678);
            }
        };

        mAdapter = new GameAdapter(this,mGames, listener);
        mGameRecyclerView.setAdapter(mAdapter);

        updateUI();

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGameActivity.class);
                startActivityForResult(intent,1234);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());
                        new GameAsyncTask(TASK_DELETE_GAME).execute(mGames.get(position));
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGameRecyclerView);

    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new GameAdapter(this, mGames, listener );
            mGameRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mGames);
        }
    }

    public void onGameDbUpdated(List list) {
        mGames = list;
        updateUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            if (resultCode == RESULT_OK) {
                Game addedGame = data.getParcelableExtra("game");
                new GameAsyncTask(TASK_INSERT_GAME).execute(addedGame);
            }
        }else if(requestCode == 45678){
            Game updatedGame = data.getParcelableExtra("game");
            new GameAsyncTask(TASK_UPDATE_GAME).execute(updatedGame);
        }
        updateUI();
    }

    public class GameAsyncTask extends AsyncTask<Game, Void, List> {
        private int taskCode;

        public GameAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }
        @Override
        protected List doInBackground(Game... games) {
            switch (taskCode){
                case TASK_DELETE_GAME:
                    db.gameDao().deleteGames(games[0]);
                    break;
                case TASK_UPDATE_GAME:
                    db.gameDao().updateGames(games[0]);
                    break;
                case TASK_INSERT_GAME:
                    db.gameDao().insertGames(games[0]);
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db.gameDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onGameDbUpdated(list);
        }
    }


}
