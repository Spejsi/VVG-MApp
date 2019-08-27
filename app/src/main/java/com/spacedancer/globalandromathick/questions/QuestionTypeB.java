package com.spacedancer.globalandromathick.questions;

import com.spacedancer.globalandromathick.utilities.Translation;

public class QuestionTypeB extends Question {

    int a;
    int b;
    int indexOfUnknown;
    int difficulty;

    public QuestionTypeB(String title, Integer questionNumber, String expression, String[] choices, Integer indexOfAnswer, String[] solution, Integer elapsedTime, Integer score) {
        super(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
    }

    public QuestionTypeB(String language, Integer questionNumber, int difficulty, int indexOfUnknown) {
        super();

        this.questionNumber = questionNumber;
        this.difficulty = difficulty;
        this.indexOfUnknown = indexOfUnknown;

        generateData(language);
    }

    @Override
    public void generateData(String language) {

        this.title = Translation.translate(language, "Izračunaj:");

        int limit = 0;
        int limitManji = 0;	int limitVeci = 0;

        int rez = 0;

        int re1 = 0;	int re2 = 0;
        int re3 = 0;	int re4 = 0;

        if (this.indexOfUnknown == 2) {
            if (this.difficulty == 0) {limit=9;		limitManji=2;	limitVeci=10; }
            if (this.difficulty == 1) {limit=99;	limitManji=5;	limitVeci=10; }
            if (this.difficulty == 2) {limit=999;	limitManji=5;	limitVeci=10; }
        } else {
            if (this.difficulty == 0) {limit=9;		limitManji=1;	limitVeci=2; }
            if (this.difficulty == 1) {limit=99;	limitManji=1;	limitVeci=2; }
            if (this.difficulty == 2) {limit=999;	limitManji=1;	limitVeci=2; }
        }

        boolean gre = true;
        while (gre) {

            int xa = (int) (Math.random() * (limit - 1)) + 2;
            int xb = (int) (Math.random() * (limit - 1)) + 2;
            int xc = xa * xb;

            if ( this.difficulty == 0) gre = false;
            if ( this.difficulty == 1 && xa <= 10 && xc >= limit && xc <= limit * 10 ) gre = false;
            if ( this.difficulty == 2 && xa >= 10 && xc >= limit && xc <= limit * 10 ) gre = false;

            if (this.indexOfUnknown == 0) { rez = xa;	this.a = xb;	this.b = xc; } // ?*a=b
            if (this.indexOfUnknown == 1) { rez = xb;	this.a = xa;	this.b = xc; } // a*?=b
            if (this.indexOfUnknown == 2) { rez = xc;	this.a = xa;	this.b = xb; } // a*b=?
        }

        re1 = rez + limitManji;	re2 = rez - limitManji;
        re3 = rez + limitVeci;	re4 = rez - limitVeci;

        /*
        if (re1 < 0) re1 = 0;
        if (re2 < 0) re2 = 0;
        if (re3 < 0) re3 = 0;
        if (re4 < 0) re4 = 0;
        */

        this.solution = new String[3];

        if (this.indexOfUnknown == 0) {
            this.expression = "? * " + this.a + " = " + this.b;
            this.solution = new String[] {
                    "x * " + this.a + " = " + this.b,
                    Translation.translate(language, "Sve podijelimo sa ") + this.a + ":",
                    "x = "+ this.b +" : "+ this.a +" => x = "+ rez
            };
        }

        if (this.indexOfUnknown == 1) {
            this.expression = this.a + " * ? = " + this.b;
            this.solution = new String[] {
                    this.a + " * x = " + this.b,
                    Translation.translate(language, "Sve podijelimo sa ") + this.a + ":",
                    "x = "+ this.b +" : "+ this.a +" => x = "+ rez
            };
        }

        if (this.indexOfUnknown == 2) {
            this.expression = this.a + " * " + this.b +" = ?";
            this.solution = new String[] {
                    Translation.translate(language, "Jednostavnim računanjem"),
                    Translation.translate(language, "dolazimo do rješenja:"),
                    "x = " + this.a + " * " + this.b + " => x = " + rez
            };
        }

        this.choices = shuffleAndResizeArray(String.valueOf(re1), String.valueOf(re2), String.valueOf(re3), String.valueOf(re4), String.valueOf(rez));
        this.indexOfAnswer = getIndexOfResult(this.choices, String.valueOf(rez));
    }
}
