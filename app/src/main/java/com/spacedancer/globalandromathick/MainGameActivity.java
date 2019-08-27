package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.GeneratingQuestions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class MainGameActivity extends AppCompatActivity
        implements View.OnClickListener {

    String language;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(MainGameActivity.this, LoginActivity.class));
            finish();
        }

        language = GameUtilities.getGameLanguage(this);
        String languageToLoad = "";
        if (language.toLowerCase().equals("en")) languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_maingame);

        Button buttonChildsPlay = (Button)findViewById(R.id.main_childsplay_mode_button);
        buttonChildsPlay.setOnClickListener(this);

        Button buttonStandardMode = (Button)findViewById(R.id.main_standard_mode_button);
        buttonStandardMode.setOnClickListener(this);

        Button buttonToughMode = (Button)findViewById(R.id.main_tough_mode_button);
        buttonToughMode.setOnClickListener(this);

        Button buttonUserAccount = (Button)findViewById(R.id.main_user_setup);
        buttonUserAccount.setOnClickListener(this);


        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.main_game_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_leaderboard:
                        Intent actLead = new Intent(MainGameActivity.this,LeaderboardActivity.class);
                        startActivity(actLead);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(MainGameActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(MainGameActivity.this,OptionsActivity.class);
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
        if (v.getId() == R.id.main_childsplay_mode_button) {
            GameUtilities.setTempMode(this, 0);
            Question[] listofQuestions = GeneratingQuestions.generate(language, 0);
            GameUtilities.setGeneratedQuestions(this, 0, listofQuestions);

            Intent i = new Intent(this, PlayActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
        }

        if (v.getId() == R.id.main_standard_mode_button) {
            GameUtilities.setTempMode(this, 1);
            // Toast.makeText(this,"selectedMode: Set 1",Toast.LENGTH_LONG).show();
            Question[] listofQuestions = GeneratingQuestions.generate(language, 1);
            GameUtilities.setGeneratedQuestions(this, 1, listofQuestions);

            Intent i = new Intent(this, PlayActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
        }

        if (v.getId() == R.id.main_tough_mode_button) {
            GameUtilities.setTempMode(this, 2);
            Question[] listofQuestions = GeneratingQuestions.generate(language, 2);
            GameUtilities.setGeneratedQuestions(this, 2, listofQuestions);

            Intent i = new Intent(this, PlayActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
        }

        if (v.getId() == R.id.main_user_setup){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
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
                MainGameActivity.this.finish();
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
