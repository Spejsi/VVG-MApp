package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spacedancer.globalandromathick.adapters.PlayersResultsAdapter;
import com.spacedancer.globalandromathick.components.Player;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class LeaderboardActivity extends AppCompatActivity
        implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private List<Player> playersList;
    private RecyclerView playersRecyclerView;
    private Context ctx;
    String language;
    PlayersResultsAdapter adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = (ProgressBar) findViewById(R.id.leaderboard_progressBar);

        language = GameUtilities.getGameLanguage(this);
        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_leaderboard);

        playersRecyclerView = (RecyclerView) findViewById(R.id.leaderboard_recycler_view);
        playersRecyclerView.setHasFixedSize(true);
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ctx = this;

        ImageView buttonChildsPlay = (ImageView)findViewById(R.id.leaderboard_beginner_imageView);
        buttonChildsPlay.setOnClickListener(this);

        ImageView buttonStandardMode = (ImageView)findViewById(R.id.leaderboard_standard_imageView);
        buttonStandardMode.setOnClickListener(this);

        ImageView buttonToughMode = (ImageView)findViewById(R.id.leaderboard_tough_imageView);
        buttonToughMode.setOnClickListener(this);

        Integer mode = GameUtilities.getTempMode(this);
        language = GameUtilities.getGameLanguage(this);
        refreshPage(mode);

        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.leaderboard_navigation);
        bnv.getMenu().findItem(R.id.navigation_leaderboard).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(LeaderboardActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(LeaderboardActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(LeaderboardActivity.this,OptionsActivity.class);
                        startActivity(actOptions);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_exit:
                        closeApplication();
                        break;

                }
                return false;
            }
        });

    }

    public void refreshPage(Integer mode){

        progressBar = (ProgressBar) findViewById(R.id.leaderboard_progressBar);
        progressBar.setVisibility(View.VISIBLE);

        if (adapter != null) {
            progressBar.setVisibility(View.VISIBLE);
            playersList.clear();
            adapter.notifyDataSetChanged();
        }

        TextView titleMode = (TextView)findViewById(R.id.leaderboard_modTitle_textView);
        if (mode == 0) titleMode.setText(Translation.translate(language, "Mod: početni"));
        if (mode == 1) titleMode.setText(Translation.translate(language, "Mod: standardni"));
        if (mode == 2) titleMode.setText(Translation.translate(language, "Mod: napredni"));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        String modeString = "LevelBeginner";
        if (mode == 1) modeString = "LevelStandard";
        if (mode == 2) modeString = "LevelTough";

        DatabaseReference productsRef = rootRef.child(modeString);

        playersList = new ArrayList<>();

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String name = postSnapshot.child("name").getValue().toString();
                    Integer score = Integer.parseInt(postSnapshot.child("score").getValue().toString());
                    Player ply = new Player(name, score);
                    playersList.add(ply);
                }

                Collections.sort(playersList, new Comparator<Player>() {
                    public int compare(Player p1, Player p2) {
                        return p2.getScore().compareTo(p1.getScore());
                    }
                });

                if (playersList.size() > 0){
                    for (int i = 0; i < playersList.size(); i++){
                        playersList.get(i).setPosition(i+1);
                        if (i > 0){
                            if (playersList.get(i).getScore().equals(playersList.get(i-1).getScore())){
                                playersList.get(i).setPosition(playersList.get(i-1).getPosition());
                            }
                        }
                    }
                }

                //creating recyclerview adapter
                adapter = new PlayersResultsAdapter(ctx, playersList);
                //setting adapter to recyclerview
                playersRecyclerView.setAdapter(adapter);

                progressBar = (ProgressBar) findViewById(R.id.leaderboard_progressBar);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        productsRef.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public void onClick(View v) {
        // tipka childsplay prebacuje na options activity (za sada)
        if (v.getId() == R.id.leaderboard_beginner_imageView) {
            refreshPage(0);
        }

        if (v.getId() == R.id.leaderboard_standard_imageView) {
            refreshPage(1);
        }

        if (v.getId() == R.id.leaderboard_tough_imageView) {
            refreshPage(2);
        }
    }


    public void onBackPressed () {
        closeApplication();
    }

    protected void closeApplication() {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        dialog.setContentView(R.layout.layout_closing);

        ImageButton yesButton = (ImageButton) dialog.findViewById(R.id.closingButtonYes);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaderboardActivity.this.finish();
            }
        });

        ImageButton noButton = (ImageButton) dialog.findViewById(R.id.closingButtonNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
