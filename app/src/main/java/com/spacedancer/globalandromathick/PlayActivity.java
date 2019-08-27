package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity
        implements View.OnClickListener {

    Integer score = 0;
    String language;
    Question[] allQuestions;
    Integer mod;
    Integer tempQuestionNumber = 0;
    CountDownTimer cdtimer;

    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;

    Integer factor;

    long startTime = 0, endTime = 0;
    int absTime = 0;

    private SoundPool soundYes;
    private Boolean loadedSoundYes = false;
    private int sIDSoundYes;
    private SoundPool soundNo;
    private Boolean loadedSoundNo = false;
    private int sIDSoundNo;
    private int soundsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        soundsId = GameUtilities.getSoundsId(this);
        loadSounds(soundsId);

        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.play_navigation);
        bnv.getMenu().findItem(R.id.navigation_home).setChecked(true);

        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(PlayActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_leaderboard:
                        Intent actLead = new Intent(PlayActivity.this,LeaderboardActivity.class);
                        startActivity(actLead);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(PlayActivity.this, language);
                        break;
                    case R.id.navigation_settings:
                        Intent actOptions = new Intent(PlayActivity.this,OptionsActivity.class);
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


        mod = GameUtilities.getTempMode(this);
        allQuestions = GameUtilities.getGeneratedQuestions(this, mod);
        language = GameUtilities.getGameLanguage(this);

        CountDownTimer readySteady = new CountDownTimer(3000, 100) {

            public void onTick(long millisUntilFinished) {
                TextView expressionTextView = (TextView)findViewById(R.id.play_QuestionExpressionTextView);

                if (millisUntilFinished < 250) {
                    expressionTextView.setText(Translation.translate(language, "SAD!"));
                } else if (millisUntilFinished < 1250) {
                    expressionTextView.setText(Translation.translate(language, "Pozor!"));
                } else {
                    expressionTextView.setText(Translation.translate(language, "Priprema!"));
                }
            }
            public void onFinish() {
                playGame();
            }

        }.start();

        buttonAnswer1 = (Button)findViewById(R.id.play_question1_button);
        buttonAnswer1.setOnClickListener(this);

        buttonAnswer2 = (Button)findViewById(R.id.play_question2_button);
        buttonAnswer2.setOnClickListener(this);

        buttonAnswer3 = (Button)findViewById(R.id.play_question3_button);
        buttonAnswer3.setOnClickListener(this);

        buttonAnswer1.setEnabled(false);
        buttonAnswer2.setEnabled(false);
        buttonAnswer3.setEnabled(false);

    }

    protected void playGame(){

        if (tempQuestionNumber >= 0 && tempQuestionNumber <= 1) factor = 1000;
        if (tempQuestionNumber >= 2 && tempQuestionNumber <= 5) factor = 1500;
        if (tempQuestionNumber >= 6 && tempQuestionNumber <= 7) factor = 2000;
        if (tempQuestionNumber >= 8 && tempQuestionNumber <= 9) factor = 2500;
        if (tempQuestionNumber >= 10) factor = 1500;

        writeQuestion(tempQuestionNumber);
        startTime = SystemClock.elapsedRealtime();

        Integer timeCount = 0;
        if (mod == 0) timeCount = 15;
        if (mod == 1) timeCount = 30;
        if (mod == 2) timeCount = 60;
        cdtimer = new CountDownTimer(timeCount * 1000, 100) {

            public void onTick(long millisUntilFinished) {
                TextView timerDown = (TextView)findViewById(R.id.play_TimeTextView);

                int se1 = (int) (millisUntilFinished/100);
                int se2 = se1%10;
                se1 = se1/10;

                timerDown.setText(Translation.translate(language,"Vrijeme: ") +
                        se1 + "." + se2);

            }
            public void onFinish() {
                TextView timerDown = (TextView)findViewById(R.id.play_TimeTextView);
                timerDown.setText(Translation.translate(language,"Vrijeme: ") +
                        Translation.translate(language,"isteklo!"));
            }

        }.start();
    }

    protected void writeQuestion(Integer tempQuestionNumber){

        Question question = allQuestions[tempQuestionNumber];

        TextView writeMode = (TextView)findViewById(R.id.play_ModeTextView);
        if (mod == 0){
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "početni"));
        } else if (mod == 1){
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "standardni"));
        } else {
            writeMode.setText(Translation.translate(language, "Mod: ") +
                    Translation.translate(language, "napredni"));
        }

        TextView writeQuestionNumber = (TextView)findViewById(R.id.play_QuestionTextView);
        writeQuestionNumber.setText(Translation.translate(language, "Pitanje: ") +
                question.getQuestionNumber() + "/20");

        TextView writeQuestionTime = (TextView)findViewById(R.id.play_TimeTextView);
        writeQuestionTime.setText(Translation.translate(language, "Vrijeme: ") + "0");

        TextView writeQuestionTitle = (TextView)findViewById(R.id.play_QuestionTitleTextView);
        writeQuestionTitle.setText(question.getTitle());

        TextView writeQuestionExpression = (TextView)findViewById(R.id.play_QuestionExpressionTextView);
        writeQuestionExpression.setText(question.getExpression());

        buttonAnswer1.setText(question.getChoices()[0]);
        buttonAnswer2.setText(question.getChoices()[1]);
        buttonAnswer3.setText(question.getChoices()[2]);

        buttonAnswer1.setEnabled(true);
        buttonAnswer2.setEnabled(true);
        buttonAnswer3.setEnabled(true);

        TextView writeQuestionScore = (TextView)findViewById(R.id.play_ScoreTextView);
        writeQuestionScore.setText(Translation.translate(language, "Bodova: ") +
                score);
    }

    public void answerClicked(int indexOfAnswered) {

        buttonAnswer1.setEnabled(false);
        buttonAnswer2.setEnabled(false);
        buttonAnswer3.setEnabled(false);

        allQuestions[tempQuestionNumber].setIndexOfAnswered(indexOfAnswered);
        boolean isTrue = (indexOfAnswered == allQuestions[tempQuestionNumber].getIndexOfAnswer());
        // Zaustavljanje brojača sekundi
        cdtimer.cancel();

        // Računanje vremena u kojem je kliknut odgovor
        endTime = SystemClock.elapsedRealtime();
        absTime = (int)(endTime - startTime);

        Integer questionScore = 0;

        // Računanje bodova za kliknut odgovor
        if (mod == 0) {
            if (absTime>15000) absTime=15000;
            questionScore=(int)((float)factor*((15000-(float)absTime)/15000));
        }
        if (mod == 1) {
            if (absTime>30000) absTime=30000;
            questionScore=(int)((float)factor*((30000-(float)absTime)/30000));
        }
        if (mod == 2) {
            if (absTime>60000) absTime=60000;
            questionScore=(int)((float)factor*((60000-(float)absTime)/60000));
        }

        // Pozitivni ili negativni bodovi
        if (isTrue) {
            score += questionScore;
            allQuestions[tempQuestionNumber].setScore(questionScore);
            allQuestions[tempQuestionNumber].setElapsedTime(absTime);
            if ((soundsId!=0)&&(loadedSoundYes)) { soundYes.play(sIDSoundYes,  1.0f, 1.0f, 1, 0, 1.0f); }
        }
        else {
            score -= questionScore;
            allQuestions[tempQuestionNumber].setScore((-1) * questionScore);
            allQuestions[tempQuestionNumber].setElapsedTime(absTime);
            if ((soundsId!=0)&&(loadedSoundNo)) { soundNo.play(sIDSoundNo,  1.0f, 1.0f, 1, 0, 1.0f); }
        }

        tempQuestionNumber++;
        if (tempQuestionNumber < 20) {
            playGame();
        } else {
            GameUtilities.setLastGameQuestions(this, mod, allQuestions, "-", language, score);
            Integer bestScore = GameUtilities.getScoreFromQuestions(this, mod, "best");
            if (bestScore <= score) {
                GameUtilities.setIsBestResult(this, true);
                GameUtilities.setBestGameQuestions(this, mod, allQuestions, "-", language, score);
            }

            GameUtilities.setModeForResults(this,mod);
            GameUtilities.setTitleForResults(this,"last");
            Intent i = new Intent(this, SavingsActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.play_question1_button) {
            answerClicked(0);
        }
        if (v.getId() == R.id.play_question2_button) {
            answerClicked(1);
        }
        if (v.getId() == R.id.play_question3_button) {
            answerClicked(2);
        }
    }


    public void loadSounds(Integer index){
        soundYes = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundYes.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundYes, int sampleId, int status) {
                loadedSoundYes = true;
            }
        });

        soundNo = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundNo.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundNo, int sampleId, int status) {
                loadedSoundNo = true;
            }
        });

        if (index==1) {
            sIDSoundYes = soundYes.load(this, R.raw.bebayes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.bebano, 1);	}
        if (index==2) {
            sIDSoundYes = soundYes.load(this, R.raw.glasyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.glasno, 1);	}
        if (index==3) {
            sIDSoundYes = soundYes.load(this, R.raw.laseryes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.laserno, 1);	}
        if (index==4) {
            sIDSoundYes = soundYes.load(this, R.raw.manijakyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.manijakno, 1);	}
        if (index==5) {
            sIDSoundYes = soundYes.load(this, R.raw.pijanacyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.pijanacno, 1);	}
        if (index==6) {
            sIDSoundYes = soundYes.load(this, R.raw.skokyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.skokno, 1);	}
        if (index==7) {
            sIDSoundYes = soundYes.load(this, R.raw.smijehyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.smijehno, 1);	}
        if (index==8) {
            sIDSoundYes = soundYes.load(this, R.raw.startrekyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.startrekno, 1);	}
        if (index==9) {
            sIDSoundYes = soundYes.load(this, R.raw.udaracyes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.udaracno, 1);	}
        if (index==10) {
            sIDSoundYes = soundYes.load(this, R.raw.vjevericayes, 1);
            sIDSoundNo = soundNo.load(this, R.raw.vjevericano, 1);	}
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
                PlayActivity.this.finish();
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