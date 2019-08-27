package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spacedancer.globalandromathick.adapters.QuestionResultsAdapter;
import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class ResultsActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private String language;

    private RecyclerView resultsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = GameUtilities.getGameLanguage(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_results);

        //getting the recyclerview from xml
        resultsRecyclerView = (RecyclerView) findViewById(R.id.results_recycler_view);
        resultsRecyclerView.setHasFixedSize(true);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Question[] allQuestions = GeneratingQuestions.generate("EN", 1);



        Integer mod = GameUtilities.getModeForResults(this);

        String title = GameUtilities.getTitleForResults(this);
        TextView titleTextView = (TextView) findViewById(R.id.questionResultsMainTitleTextView);

        Question[] allQuestions;
        String playerName = GameUtilities.getPlayerNameFromQuestions(this, mod, title);
        String gameLanguage = GameUtilities.getLanguageFromQuestions(this, mod, title);
        Integer playerScore = GameUtilities.getScoreFromQuestions(this,mod, title);
        if (title.equals("last")) {
            allQuestions = GameUtilities.getLastGameQuestions(this, mod);
            titleTextView.setText(Translation.translate(language, "Zadnja igra"));
        } else {
            allQuestions = GameUtilities.getBestGameQuestions(this, mod);
            titleTextView.setText(Translation.translate(language, "Najbolja igra"));
        }

        TextView playerTextView = (TextView) findViewById(R.id.questionResultsPlayerTextView);
        playerTextView.setText(Translation.translate(language,"Igrač:") + "\n" + playerName);

        TextView scoresTextView = (TextView) findViewById(R.id.questionResultsScoresTextView);
        scoresTextView.setText(Translation.translate(language, "Bodova:") + String.valueOf(playerScore));

        String modText = Translation.translate(language,"Mod: početni");
        if (mod == 1) modText = Translation.translate(language,"Mod: standardni");
        if (mod == 2) modText = Translation.translate(language,"Mod: napredni");
        TextView modeTextView = (TextView) findViewById(R.id.questionResultsModeTextView);
        modeTextView.setText(modText);

        String languageText = Translation.translate(language, "Jezik:");
        if (gameLanguage.equals("HR")) {
            languageText+= "\n" + Translation.translate(language, "hrvatski");
        } else {
            languageText+= "\n" + Translation.translate(language, "engleski");
        }
        TextView languageTextView = (TextView) findViewById(R.id.questionResultsLanguageTextView);
        languageTextView.setText(languageText);


        //creating recyclerview adapter
        QuestionResultsAdapter adapter = new QuestionResultsAdapter(this, allQuestions, language);

        //setting adapter to recyclerview
        resultsRecyclerView.setAdapter(adapter);



        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.results_recycler_view_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(ResultsActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_leaderboard:
                        Intent actLead = new Intent(ResultsActivity.this,LeaderboardActivity.class);
                        startActivity(actLead);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(ResultsActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(ResultsActivity.this,OptionsActivity.class);
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
                ResultsActivity.this.finish();
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