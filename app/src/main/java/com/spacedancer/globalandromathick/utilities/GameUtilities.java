package com.spacedancer.globalandromathick.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.spacedancer.globalandromathick.PlayActivity;
import com.spacedancer.globalandromathick.R;
import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.questions.QuestionTypeA;
import com.spacedancer.globalandromathick.questions.QuestionTypeB;
import com.spacedancer.globalandromathick.questions.QuestionTypeC;
import com.spacedancer.globalandromathick.questions.QuestionTypeD;

import java.util.Locale;

public class GameUtilities {

    public static String getGameLanguage(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.generalAppSettings", Context.MODE_PRIVATE);
        String language = prefs.getString("language",
                "HR");
        return language;
    }

    public static void setGameLanguage(Context context, String language){

        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.generalAppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("language", language);
        editor.commit();
    }


    public static Integer getSoundsId(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.generalAppSettings", Context.MODE_PRIVATE);
        Integer soundsId = Integer.valueOf(prefs.getString("soundsId", "0"));
        return soundsId;
    }

    public static void setSoundsId(Context context, Integer soundsId){

        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.generalAppSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("soundsId", String.valueOf(soundsId));
        editor.commit();
    }

    public static Integer getTempMode(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        Integer mod = Integer.valueOf(prefs.getString("selectedMode", "0"));
        return mod;
    }

    public static void setTempMode(Context context, Integer mod){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selectedMode", String.valueOf(mod));
        editor.commit();
    }

    public static Question[] getGeneratedQuestions(Context context, Integer mod){
        Question[] questionsList = loadQuestions(context, mod, "generatedQuestions");
        return questionsList;
    }

    public static Question[] getLastGameQuestions(Context context, Integer mod){
        Question[] questionsList = loadQuestions(context, mod, "lastGame");
        return questionsList;
    }

    public static Question[] getBestGameQuestions(Context context, Integer mod){
        Question[] questionsList = loadQuestions(context, mod, "bestGame");
        return questionsList;
    }

    public static Question[] loadQuestions(Context context, Integer mod, String fileName){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + fileName, Context.MODE_PRIVATE);
        String prefix = "mod" + mod;

        Question[] questionsList = new Question[20];

        for (int i = 0; i < questionsList.length; i++){
            String questionPrefix = prefix + "q" + (i + 1);

            Integer questionNumber = i+1;
            String title = prefs.getString(questionPrefix + "Title", "-");
            String expression = prefs.getString(questionPrefix + "Expression", "-");

            String[] choices = new String[3];
            choices[0] = prefs.getString(questionPrefix + "Choice1", "-");
            choices[1] = prefs.getString(questionPrefix + "Choice2", "-");
            choices[2] = prefs.getString(questionPrefix + "Choice3", "-");

            Integer indexOfAnswer = Integer.valueOf(
                    prefs.getString(questionPrefix + "IndexOfAnswer", "0"));

            String[] solution = new String[3];
            solution[0] = prefs.getString(questionPrefix + "SolutionLine1", "-");
            solution[1] = prefs.getString(questionPrefix + "SolutionLine2", "-");
            solution[2] = prefs.getString(questionPrefix + "SolutionLine3", "-");

            Integer elapsedTime = Integer.valueOf(
                    prefs.getString(questionPrefix + "ElapsedTime", "0"));
            Integer score = Integer.valueOf(
                    prefs.getString(questionPrefix + "Score", "0"));
            Integer indexOfAnswered = Integer.valueOf(
                    prefs.getString(questionPrefix + "IndexOfAnswered", "0"));

            Question question;

            if (i >= 0 && i <= 5){
                question = new QuestionTypeA(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
            } else if (i >= 6 && i <= 9){
                question = new QuestionTypeB(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
            } else if (i >= 10 && i <= 13){
                question = new QuestionTypeC(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
            } else {
                question = new QuestionTypeD(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
            }
            question.setIndexOfAnswered(indexOfAnswered);
            questionsList[i] = question;
        }

        return questionsList;
    }

    public static void setGeneratedQuestions(Context context, Integer mod, Question[] questionsList){
        saveQuestions(context, mod, questionsList, "generatedQuestions");
    }

    public static void setLastGameQuestions(Context context, Integer mod, Question[] questionsList,
                                            String playerName, String language, Integer score){
        saveQuestions(context, mod, questionsList, "lastGame");
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.lastGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mod" + mod + "PlayerName", playerName);
        editor.putString("mod" + mod + "Language", language);
        editor.putString("mod" + mod + "Score", String.valueOf(score));
        editor.commit();
    }

    public static void setBestGameQuestions(Context context, Integer mod, Question[] questionsList,
                                            String playerName, String language, Integer score){
        saveQuestions(context, mod, questionsList, "bestGame");
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.bestGame", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mod" + mod + "PlayerName", playerName);
        editor.putString("mod" + mod + "Language", language);
        editor.putString("mod" + mod + "Score", String.valueOf(score));
        editor.commit();
    }

    public static void saveQuestions(Context context, Integer mod, Question[] questionsList, String fileName){

        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String prefix = "mod" + mod;

        for (int i = 0; i < questionsList.length; i++){
            String questionPrefix = prefix + "q" + (i + 1);

            editor.putString(questionPrefix + "Title", questionsList[i].getTitle());
            editor.putString(questionPrefix + "Expression", questionsList[i].getExpression());
            editor.putString(questionPrefix + "Choice1", questionsList[i].getChoices()[0]);
            editor.putString(questionPrefix + "Choice2", questionsList[i].getChoices()[1]);
            editor.putString(questionPrefix + "Choice3", questionsList[i].getChoices()[2]);
            editor.putString(questionPrefix + "IndexOfAnswer",
                    String.valueOf(questionsList[i].getIndexOfAnswer()));
            editor.putString(questionPrefix + "SolutionLine1", questionsList[i].getSolution()[0]);
            editor.putString(questionPrefix + "SolutionLine2", questionsList[i].getSolution()[1]);
            editor.putString(questionPrefix + "SolutionLine3", questionsList[i].getSolution()[2]);
            editor.putString(questionPrefix + "ElapsedTime",
                    String.valueOf(questionsList[i].getElapsedTime()));
            editor.putString(questionPrefix + "Score",
                    String.valueOf(questionsList[i].getScore()));
            editor.putString(questionPrefix + "IndexOfAnswered",
                    String.valueOf(questionsList[i].getIndexOfAnswered()));
        }

        editor.commit();
    }


    public static String getPlayerNameFromQuestions(Context context, Integer mod, String bestOrLast){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        String playerName = prefs.getString("mod" + mod + "PlayerName", "-");
        return playerName;
    }

    public static String getLanguageFromQuestions(Context context, Integer mod, String bestOrLast){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        String language = prefs.getString("mod" + mod + "Language", "HR");
        return language;
    }

    public static Integer getScoreFromQuestions(Context context, Integer mod, String bestOrLast){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        Integer score = Integer.valueOf(prefs.getString("mod" + mod + "Score", "0"));
        return score;
    }

    public static void setPlayerNameToQuestions(Context context, Integer mod, String bestOrLast, String playerName){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mod" + mod + "PlayerName", playerName);
        editor.commit();
    }

    public static void setLanguageToQuestions(Context context, Integer mod, String bestOrLast, String language){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mod" + mod + "Language", language);
        editor.commit();
    }

    public static void setScoreToQuestions(Context context, Integer mod, String bestOrLast, Integer score){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick." + bestOrLast.toLowerCase() + "Game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mod" + mod + "Score", String.valueOf(score));
        editor.commit();
    }

    public static void setIsBestResult(Context context, boolean isBest){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (isBest) {
            editor.putString("isBestResult", "yes");
        } else {
            editor.putString("isBestResult", "no");
        }
        editor.commit();
    }

    public static boolean getIsBestResult(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);

        String data = prefs.getString("isBestResult", "no");
        return !data.equals("no");
    }



    public static void setTitleForResults(Context context, String lastOrBest){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("titleForResults", lastOrBest);
        editor.commit();
    }

    public static String getTitleForResults(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        String data = prefs.getString("titleForResults", "last");
        return data;
    }

    public static Integer getModeForResults(Context context){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        Integer mod = Integer.valueOf(prefs.getString("modeForResults", "0"));
        return mod;
    }

    public static void setModeForResults(Context context, Integer mod){
        SharedPreferences prefs = context.getSharedPreferences
                ("com.spacedancer.andromathick.tempSettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("modeForResults", String.valueOf(mod));
        editor.commit();
    }

    public static void setHelpDialog(Context context, String language){

        final Dialog dialog = new Dialog(context);

        String className = context.getClass().getSimpleName();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.layout_help);

        ImageView screenshot = (ImageView) dialog.findViewById(R.id.helpScreenshotImageView);
        TextView dataText =(TextView) dialog.findViewById(R.id.helpDataTextView);

        switch (className){
            case "MainGameActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot10));
                break;
            case "ResetPasswordActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot09));
                break;
            case "SignupActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot08));
                break;
            case "LoginActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot07));
                break;
            case "SavingsActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot06));
                break;
            case "PlayActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot05));
                break;
            case "ResultsActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot04));
                break;
            case "OptionsActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot03));
                break;
            case "LeaderboardActivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot02));
                break;
            case "Mainctivity":
                screenshot.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.screenshot01));
                break;
        }

        String data = Translation.dataText(className, language);
        dataText.setText(data);

        Button okButton = (Button) dialog.findViewById(R.id.help_okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

        String klasa = context.getClass().getSimpleName();
    }





}
