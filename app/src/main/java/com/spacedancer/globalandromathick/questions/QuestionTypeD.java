package com.spacedancer.globalandromathick.questions;

import com.spacedancer.globalandromathick.utilities.Translation;

public class QuestionTypeD extends Question {

    int a;
    int b;
    int c;
    char operatorOutside;
    char operatorInside;
    int difficulty;

    public QuestionTypeD(String title, Integer questionNumber, String expression, String[] choices, Integer indexOfAnswer, String[] solution, Integer elapsedTime, Integer score) {
        super(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
    }

    public QuestionTypeD(String language, Integer questionNumber, int difficulty, char operatorOutside, char operatorInside) {
        super();

        this.questionNumber = questionNumber;
        this.difficulty = difficulty;
        this.operatorOutside = operatorOutside;
        this.operatorInside = operatorInside;

        generateData(language);
    }

    @Override
    public void generateData(String language) {

        this.title = Translation.translate(language, "Izračunaj:");

        int limit = 0;
        int limitManji = 0;		int limitVeci = 0;
        int rasponManji = 0;	int rasponVeci = 0;

        int rez = 0;

        int re1 = 0;	int re2 = 0;
        int re3 = 0;	int re4 = 0;

        int zagrada = 0;

        if (this.difficulty == 0) {limit=9;		limitManji=1;	limitVeci=2;	rasponManji=1;	rasponVeci=30;		}
        if (this.difficulty == 1) {limit=99;	limitManji=2;	limitVeci=10;	rasponManji=1;	rasponVeci=150;		}
        if (this.difficulty == 2) {limit=999;	limitManji=2;	limitVeci=10; 	rasponManji=1;	rasponVeci=1000;	}

        boolean gre = true;
        while (gre) {

            this.a = (int) (Math.random() * limit) + 1;
            this.b = (int) (Math.random() * limit) + 1;
            this.c = (int) (Math.random() * limit) + 1;

            if (this.operatorInside == '+') {
                zagrada = this.b + this.c;
            } else {
                zagrada = this.b - this.c;
            }

            if (this.operatorOutside == '+') {
                rez = this.a + zagrada;
            } else {
                rez = this.a - zagrada;
            }

            if ((rez>=rasponManji)&&(rez<=rasponVeci)) gre = false;
            if ((this.difficulty == 1)&&((rez<10)||(this.a<10)||(this.b<10)||(this.c<10))) gre=true;
            if ((this.difficulty == 2)&&((rez<100)||(this.a<100)||(this.b<100)||(this.c<100))) gre=true;
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

        this.expression = this.a + " " + this.operatorOutside + " ( " + this.b + " " + this.operatorInside + " " + this.c + " ) = ?";

        this.solution = new String[] {
                "x - " + this.a + " = " + this.b,
                Translation.translate(language, "Prebacimo ") + this.a + Translation.translate(language, " desno:"),
                "x = " + this.b + " + " + this.a + " => x = " + rez
        };

        if (this.operatorOutside == '+') {

            if (zagrada < 0){
                this.solution = new String[] {
                        this.a + " + ( " + this.b + " " + this.operatorInside + " " + this.c + " ) =",
                        this.a + " + ( " + zagrada + " ) =",
                        this.a + " - " + Math.abs(zagrada) + " = " + (this.a + zagrada)
                };
            } else {
                this.solution = new String[] {
                        this.a + " + ( " + this.b + " " + this.operatorInside + " " + this.c + " ) =",
                        this.a + " + ( " + zagrada + " ) =",
                        this.a + " + " + zagrada + " = " + (this.a + zagrada)
                };
            }

        } else {
            if (zagrada < 0){
                this.solution = new String[] {
                        this.a + " - ( " + this.b + " " + this.operatorInside + " " + this.c + " ) =",
                        this.a + " - ( " + zagrada + " ) =",
                        this.a + " + " + Math.abs(zagrada) + " = " + (this.a - zagrada)
                };
            } else {
                this.solution = new String[] {
                        this.a + " - ( " + this.b + " " + this.operatorInside + " " + this.c + " ) =",
                        this.a + " - ( " + zagrada + " ) =",
                        this.a + " - " + zagrada + " = " + (this.a - zagrada)
                };
            }
        }

        this.choices = shuffleAndResizeArray(String.valueOf(re1), String.valueOf(re2), String.valueOf(re3), String.valueOf(re4), String.valueOf(rez));
        this.indexOfAnswer = getIndexOfResult(this.choices, String.valueOf(rez));
    }
}