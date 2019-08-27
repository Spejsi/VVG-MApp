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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spacedancer.globalandromathick.components.DatabasePlayer;
import com.spacedancer.globalandromathick.components.Player;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SavingsActivity extends AppCompatActivity
        implements View.OnClickListener {

    String language;
    DatabaseReference databasePlayers;
    Context ctx;
    List<Player> allPlayers;
    EditText entername;
    Button savebutton;
    Integer lowestScore;
    Integer mode;
    Integer gameScore;
    Integer rank;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressBar = (ProgressBar) findViewById(R.id.savings_progressBar);

        language = GameUtilities.getGameLanguage(this);
        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_savings);

        ctx = this;

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        mode = GameUtilities.getTempMode(this);

        TextView writeMode = (TextView)findViewById(R.id.savingsModeTextView);
        if (mode == 0){
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "početni"));
        } else if (mode == 1){
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "standardni"));
        } else {
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "napredni"));
        }

        String modeString = "LevelBeginner";
        if (mode == 1) modeString = "LevelStandard";
        if (mode == 2) modeString = "LevelTough";

        databasePlayers = rootRef.child(modeString);

        gameScore = GameUtilities.getScoreFromQuestions(this, mode, "last");

        TextView writeScore = (TextView)findViewById(R.id.savingsScoreTextView);

        writeScore.setText(String.valueOf(gameScore));

        entername = (EditText) findViewById(R.id.savingsEnterNameEditText);
        savebutton = (Button) findViewById(R.id.savingsSaveButton);
        entername.setEnabled(false);
        savebutton.setEnabled(false);
        savebutton.setOnClickListener(this);

        getAllPlayers();


        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.savings_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(SavingsActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_leaderboard:
                        Intent actLead = new Intent(SavingsActivity.this,LeaderboardActivity.class);
                        startActivity(actLead);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(SavingsActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(SavingsActivity.this,OptionsActivity.class);
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

    @Override
    public void onClick(View v) {
        // tipka childsplay prebacuje na options activity (za sada)
        if (v.getId() == R.id.savingsSaveButton) {
            EditText txtEntered = (EditText) findViewById(R.id.savingsEnterNameEditText);
            String playerName = txtEntered.getText().toString().trim();
            if (playerName.equals("")){
                Toast.makeText(this, Translation.translate(language, "Molim unesi ime!"),
                        Toast.LENGTH_LONG).show();
            } else {
                GameUtilities.setPlayerNameToQuestions(ctx, mode,"last", playerName);
                Boolean isBest = GameUtilities.getIsBestResult(ctx);
                if (isBest) {
                    GameUtilities.setPlayerNameToQuestions(ctx, mode,"best", playerName);
                }

                String id = databasePlayers.push().getKey();

                if (rank <= 250){
                    DatabasePlayer playerNew = new DatabasePlayer(playerName, gameScore);
                    databasePlayers.child(id).setValue(playerNew);
                }

                removeScoresLowerThan(lowestScore);

                // skoči na rezultate
                Intent i = new Intent(ctx, ResultsActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(0, 0);
            }
        }

    }

    public void getAllPlayers(){
        progressBar = (ProgressBar) findViewById(R.id.savings_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Player> playersList = new ArrayList<>();

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

                allPlayers = playersList;
                entername.setEnabled(true);
                savebutton.setEnabled(true);
                progressBar = (ProgressBar) findViewById(R.id.savings_progressBar);
                progressBar.setVisibility(View.GONE);
                getRanking();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                allPlayers = new ArrayList<>();
                entername.setEnabled(true);
                savebutton.setEnabled(true);
                progressBar = (ProgressBar) findViewById(R.id.savings_progressBar);
                progressBar.setVisibility(View.GONE);
                getRanking();
            }
        };
        databasePlayers.addListenerForSingleValueEvent(eventListener);
    }

    private void getRanking(){
        List<Player> tempCopy = new ArrayList<Player>(allPlayers);
        Player ply = new Player("!NEW!", gameScore);
        tempCopy.add(ply);
        Collections.sort(tempCopy, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return p2.getScore().compareTo(p1.getScore());
            }
        });

        for (int i = 0; i < tempCopy.size(); i++){
            tempCopy.get(i).setPosition(i+1);
            if (i > 0){
                if (tempCopy.get(i).getScore().equals(tempCopy.get(i-1).getScore())){
                    tempCopy.get(i).setPosition(tempCopy.get(i-1).getPosition());
                }
            }
        }

        rank = 1;
        for (Player pl : tempCopy){
            if (pl.getName().equals("!NEW!")) rank = pl.getPosition();
        }

        TextView rankingText = (TextView) findViewById(R.id.savingsLeaderboardRankTextView);

        if (rank <= 250) {
            rankingText.setText(String.valueOf(rank));
        } else {
            rankingText.setText(Translation.translate(language, "Ispod granice"));
        }

        if (tempCopy.size() >= 250) {
            lowestScore = tempCopy.get(249).getScore() - 1;
        } else {
            lowestScore = -1;
        }
    }

    private void removeScoresLowerThan(Integer maxScore){

        ValueEventListener valueEventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                List<String> allKeysToDelete = new ArrayList<String>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    String key = postSnapshot.getKey();
                    allKeysToDelete.add(key);
                }

                for (String key : allKeysToDelete){
                    databasePlayers.child(key).removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        Query query = databasePlayers.orderByChild("score").endAt(Double.valueOf(maxScore));
        query.addValueEventListener(valueEventListener);
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
                SavingsActivity.this.finish();
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
