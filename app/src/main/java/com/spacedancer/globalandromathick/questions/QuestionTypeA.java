package com.spacedancer.globalandromathick.questions;

import com.spacedancer.globalandromathick.utilities.Translation;

public class QuestionTypeA extends Question {

    int a;
    int b;
    int indexOfUnknown;
    char operator;
    int difficulty;

    public QuestionTypeA(String title, Integer questionNumber, String expression, String[] choices, Integer indexOfAnswer, String[] solution, Integer elapsedTime, Integer score) {
        super(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
    }

    public QuestionTypeA(String language, Integer questionNumber, int difficulty, char operator, int indexOfUnknown) {
        super();

        this.questionNumber = questionNumber;
        this.difficulty = difficulty;
        this.operator = operator;
        this.indexOfUnknown = indexOfUnknown;

        generateData(language);
    }

    @Override
    public void generateData(String language) {

        this.title = Translation.translate(language, "Izračunaj:");

        int limit = 0;
        int limitManji = 0;	int limitVeci = 0;
        int rasponManji = 0;	int rasponVeci = 0;

        int rez = 0;

        int re1 = 0;	int re2 = 0;
        int re3 = 0;	int re4 = 0;

        if (this.difficulty == 0) {limit=9;		limitManji=1;	limitVeci=2;	rasponManji=1;	rasponVeci=20;		}
        if (this.difficulty == 1) {limit=99;	limitManji=1;	limitVeci=10;	rasponManji=1;	rasponVeci=100;		}
        if (this.difficulty == 2) {limit=999;	limitManji=1;	limitVeci=10; 	rasponManji=1;	rasponVeci=1000;	}

        boolean gre = true;
        while (gre) {

            this.a = (int) (Math.random() * limit) + 1;
            this.b = (int) (Math.random() * limit) + 1;

            if (this.operator == '-') {
                if (this.indexOfUnknown == 0) rez = this.a + this.b;	// ?-a=b
                if (this.indexOfUnknown == 1) rez = this.a - this.b;	// a-?=b
                if (this.indexOfUnknown == 2) rez = this.a - this.b;	// a-b=?
            } else {
                if (this.indexOfUnknown == 0) rez = this.b - this.a;	// ?+a=b
                if (this.indexOfUnknown == 1) rez = this.b - this.a;	// a+?=b
                if (this.indexOfUnknown == 2) rez = this.a + this.b;	// a+b=?
            }

            if ((rez>=rasponManji)&&(rez<=rasponVeci)) gre = false;
            if ((this.difficulty == 1) && ((this.a < 10) || (this.b < 10))) gre = true;
            if ((this.difficulty == 1) && (rez < 10)) gre = true;
            if ((this.difficulty == 2) && ((rez < 100) || (this.a < 100)||(this.b < 100))) gre = true;
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
            this.expression = "? " + this.operator + " " + this.a + " = " + this.b;

            if (this.operator == '-') {
                this.solution = new String[] {
                        "x - " + this.a + " = " + this.b,
                        Translation.translate(language, "Prebacimo ") + this.a + Translation.translate(language, " desno:"),
                        "x = " + this.b + " + " + this.a + " => x = " + rez
                };
            } else {
                this.solution = new String[] {
                        "x + " + this.a + " = " + this.b,
                        Translation.translate(language, "Prebacimo ") + this.a + Translation.translate(language, " desno:"),
                        "x = " + this.b + " - " + this.a + " => x = " + rez
                };
            }
        }

        if (this.indexOfUnknown == 1) {
            this.expression = this.a + " " + this.operator + " ? = " + this.b;

            if (this.operator == '-') {
                this.solution = new String[] {
                        this.a + " - x = " + this.b,
                        Translation.translate(language, "x prebacimo desno, ") + this.b + Translation.translate(language, " lijevo:"),
                        this.a + " - " + this.b +" = x => x = " + rez
                };
            } else {
                this.solution = new String[] {
                        this.a + " + x = " + this.b,
                        Translation.translate(language, "Prebacimo ") + this.a + Translation.translate(language, " desno:"),
                        "x = " + this.b + " - " + this.a + " => x = " + rez
                };
            }
        }

        if (this.indexOfUnknown == 2) {
            this.expression = this.a + " " + this.operator + " " + this.b +" = ?";
            this.solution = new String[] {
                    Translation.translate(language, "Jednostavnim računanjem"),
                    Translation.translate(language, "dolazimo do rješenja:"),
                    "x = " + this.a + " " + this.operator + " " + this.b + " => x = " + rez
            };
        }

        this.choices = shuffleAndResizeArray(String.valueOf(re1), String.valueOf(re2), String.valueOf(re3), String.valueOf(re4), String.valueOf(rez));
        this.indexOfAnswer = getIndexOfResult(this.choices, String.valueOf(rez));
    }
}