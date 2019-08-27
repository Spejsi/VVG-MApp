package com.spacedancer.globalandromathick;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.spacedancer.globalandromathick.utilities.GameUtilities;
import com.spacedancer.globalandromathick.utilities.Translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.spacedancer.globalandromathick.utilities.GameUtilities.setSoundsId;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    private String language;
    private Integer soundsId;

    private SoundPool soundYes;
    private Boolean loadedSoundYes = false;
    private int sIDSoundYes;
    private SoundPool soundNo;
    private Boolean loadedSoundNo = false;
    private int sIDSoundNo;

    private SeekBar volumeSeekbar;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_options);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        language = GameUtilities.getGameLanguage(this);
        soundsId = GameUtilities.getSoundsId(this);

        // Toast.makeText(getApplicationContext(),"Language: " + language,Toast.LENGTH_LONG).show();

        setLanguage(language);

        RadioGroup radioButtonGroup = (RadioGroup)findViewById(R.id.optionsEngCroRadioGroup);
        if (language.equals("HR")) {
            radioButtonGroup.check(R.id.optionsCroRadioButton);
        } else {
            radioButtonGroup.check(R.id.optionsEngRadioButton);
        }

        Button optionsSaveButton = (Button)findViewById(R.id.optionsSaveButton);
        optionsSaveButton.setOnClickListener(this);

        ImageView checkYes = (ImageView) findViewById(R.id.optionsCheckSoundYesImageView);
        checkYes.setOnClickListener(this);
        ImageView checkNo = (ImageView) findViewById(R.id.optionsCheckSoundNoImageView);
        checkNo.setOnClickListener(this);

        ImageView gameBestMod0 = (ImageView) findViewById(R.id.options_best_beginner_imageView);
        gameBestMod0.setOnClickListener(this);
        ImageView gameBestMod1 = (ImageView) findViewById(R.id.options_best_standard_View);
        gameBestMod1.setOnClickListener(this);
        ImageView gameBestMod2 = (ImageView) findViewById(R.id.options_best_taugh_imageView);
        gameBestMod2.setOnClickListener(this);

        ImageView gameLastMod0 = (ImageView) findViewById(R.id.options_last_beginner_imageView);
        gameLastMod0.setOnClickListener(this);
        ImageView gameLastMod1 = (ImageView) findViewById(R.id.options_last_standard_View);
        gameLastMod1.setOnClickListener(this);
        ImageView gameLastMod2 = (ImageView) findViewById(R.id.options_last_taugh_imageView);
        gameLastMod2.setOnClickListener(this);


        // Selektiraj item na navigaciji
        BottomNavigationView bnv =(BottomNavigationView)findViewById(R.id.options_navigation);
        bnv.getMenu().findItem(R.id.navigation_settings).setChecked(true);


        // odredi što će se dogoditi klikom na navigaciju
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent actMGA = new Intent(OptionsActivity.this,MainGameActivity.class);
                        startActivity(actMGA);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.navigation_leaderboard:
                         if (auth.getCurrentUser() != null) {
                             Intent actLead = new Intent(OptionsActivity.this,LeaderboardActivity.class);
                             startActivity(actLead);
                             finish();
                             overridePendingTransition(0, 0);
                         }
                        break;
                    case R.id.navigation_help:
                        GameUtilities.setHelpDialog(OptionsActivity.this, language);
                        break;
                    case R.id.navigation_exit:
                        closeApplication();
                        break;

                }
                return false;
            }
        });

        initControls();
    }

    @Override
    public void onClick(View v) {

        // tipka childsplay prebacuje na options activity (za sada)
        if (v.getId() == R.id.optionsSaveButton) {

            RadioGroup radioButtonGroup = (RadioGroup)findViewById(R.id.optionsEngCroRadioGroup);
            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            View radioButton = radioButtonGroup.findViewById(radioButtonID);
            int idx = radioButtonGroup.indexOfChild(radioButton);

            String lang = "";
            if (idx == 0) {
                lang = "EN";
            } else {
                lang = "HR";
            }
            GameUtilities.setGameLanguage(this, lang);
            Toast.makeText(getApplicationContext(),
                    Translation.translate(lang, "Postavke spremljene!"),Toast.LENGTH_SHORT).show();

            Spinner sItems = (Spinner) findViewById(R.id.options_sounds_spinner);
            soundsId = sItems.getSelectedItemPosition();

            setSoundsId(this, soundsId);
            setLanguage(lang);
            language = lang;



            Resources res = this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale(language.toLowerCase()));
            res.updateConfiguration(conf, dm);






        }

        if (v.getId() == R.id.optionsCheckSoundNoImageView) {

            if(loadedSoundNo && soundsId!=0){
                soundNo.play(sIDSoundNo,  1.0f, 1.0f, 1, 0, 1.0f);
            }

        }

        if (v.getId() == R.id.optionsCheckSoundYesImageView) {

            if(loadedSoundYes && soundsId!=0){
                soundYes.play(sIDSoundYes, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        }


        if (v.getId() == R.id.options_best_beginner_imageView && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "best");
            GameUtilities.setModeForResults(this,0);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
        }
        if (v.getId() == R.id.options_best_standard_View && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "best");
            GameUtilities.setModeForResults(this,1);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
        }
        if (v.getId() == R.id.options_best_taugh_imageView && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "best");
            GameUtilities.setModeForResults(this,2);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
        }


        if (v.getId() == R.id.options_last_beginner_imageView && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "last");
            GameUtilities.setModeForResults(this,0);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
        }
        if (v.getId() == R.id.options_last_standard_View && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "last");
            GameUtilities.setModeForResults(this,1);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
        }
        if (v.getId() == R.id.options_last_taugh_imageView && auth.getCurrentUser() != null){
            GameUtilities.setTitleForResults(this, "last");
            GameUtilities.setModeForResults(this,2);
            Intent actres = new Intent(this, ResultsActivity.class);
            startActivity(actres);
            finish();
            overridePendingTransition(0, 0);
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

    public void setLanguage(String language){

        TextView title = (TextView)findViewById(R.id.optionsTitleTextView);
        title.setText(Translation.translate(language, "Opcije"));

        Button save = (Button)findViewById(R.id.optionsSaveButton);
        save.setText(Translation.translate(language, "Spremi"));

        TextView spinerTitle = (TextView)findViewById(R.id.optionsSpinnerTitleTextView);
        spinerTitle.setText(Translation.translate(language,"Odaberi zvukove:"));

        TextView soundsCheck = (TextView)findViewById(R.id.optionsTestSoundsTitleTextView);
        soundsCheck.setText(Translation.translate(language,"<= testiraj zvuk =>"));

        TextView bestGameTitle = (TextView)findViewById(R.id.options_bestGame_textView);
        bestGameTitle.setText(Translation.translate(language,"Najbolja igra =>"));

        TextView lastGameTitle = (TextView)findViewById(R.id.options_lastGame_textView);
        lastGameTitle.setText(Translation.translate(language,"<= Zadnja igra"));

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add(Translation.translate(language,"Bez zvuka"));   // No sounds
        spinnerArray.add(Translation.translate(language,"Beba"));   // Baby
        spinnerArray.add(Translation.translate(language,"Glas"));  // Voice
        spinnerArray.add(Translation.translate(language,"Laser"));   // Laser
        spinnerArray.add(Translation.translate(language,"Manijak"));  // Maniac
        spinnerArray.add(Translation.translate(language,"Pijanac"));  // Drunk
        spinnerArray.add(Translation.translate(language,"Skok"));  // Jump
        spinnerArray.add(Translation.translate(language,"Smijeh"));  // Laughter
        spinnerArray.add(Translation.translate(language,"Zvjezdane staze"));  // Star Trek
        spinnerArray.add(Translation.translate(language,"Udarac"));  // Kick
        spinnerArray.add(Translation.translate(language,"Vjeverica"));  // squirrel

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.options_sounds_spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                loadSounds(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });




        sItems.setSelection(soundsId);


    }

    private void initControls()
    {
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.options_volumeSeekBar);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            Integer izracun = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            izracun = (100 * izracun / 15);
            ((TextView) findViewById(R.id.optionsVolumeTextView)).setText(String.valueOf(izracun) + "%");

            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                    TextView volTxt = (TextView) findViewById(R.id.optionsVolumeTextView);

                    Integer prg = (100 * progress / 15);

                    volTxt.setText(String.valueOf(prg) + "%");
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
                OptionsActivity.this.finish();
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
