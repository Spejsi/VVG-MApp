package com.spacedancer.globalandromathick.questions;

import com.spacedancer.globalandromathick.utilities.Translation;

public class QuestionTypeC extends Question {

    int a;
    int b;
    int c;
    int d;
    char leftOperator;
    char rightOperator;
    int difficulty;

    public QuestionTypeC(String title, Integer questionNumber, String expression, String[] choices, Integer indexOfAnswer, String[] solution, Integer elapsedTime, Integer score) {
        super(title, questionNumber, expression, choices, indexOfAnswer, solution, elapsedTime, score);
    }

    public QuestionTypeC(String language, Integer questionNumber, int difficulty, char operator1, char operator2) {
        super();

        this.questionNumber = questionNumber;
        this.difficulty = difficulty;
        this.leftOperator = operator1;
        this.rightOperator = operator2;

        generateData(language);
    }

    @Override
    public void generateData(String language) {

        this.title = Translation.translate(language, "Usporedi strane:");

        int limit = 0;
        int raspon = 0;

        String rez = "";

        String re1 = "";
        String re2 = "";
        String re3 = "";

        if (this.difficulty == 0) { limit=9;	raspon=2; }
        if (this.difficulty == 1) { limit=99;	raspon=20; }
        if (this.difficulty == 2) {limit=999;	raspon=200; }

        int leftSide = 0;
        int rightSide = 0;

        String leftExpression = "";
        String rightExpression = "";

        boolean gre = true;
        while (gre) {

            this.a = (int) (Math.random() * limit) + 1;
            this.b = (int) (Math.random() * limit) + 1;
            this.c = (int) (Math.random() * limit) + 1;
            this.d = (int) (Math.random() * limit) + 1;

            if (this.leftOperator == '+') {
                leftSide = this.a + this.b;
            } else {
                leftSide = this.a - this.b;
            }

            if (this.rightOperator == '+') {
                rightSide = this.c + this.d;
            } else {
                rightSide = this.c - this.d;
            }

            leftExpression = this.a + " " + this.leftOperator + " " + this.b;
            rightExpression = this.c + " " + this.rightOperator + " " + this.d;

            if ((leftSide >= 0 && rightSide >= 0) && (Math.abs(leftSide - rightSide) <= raspon)) gre = false;
            if (leftExpression.equals(rightExpression)) gre = true;

        }

        if (leftSide > rightSide) {
            rez = leftExpression + " > " + rightExpression;
        } else if (leftSide < rightSide) {
            rez = leftExpression + " < " + rightExpression;
        } else {
            rez = leftExpression + " = " + rightExpression;
        }

        re1 = leftExpression + " > " + rightExpression;
        re2 = leftExpression + " = " + rightExpression;
        re3 = leftExpression + " < " + rightExpression;

        this.expression = leftExpression + " " + Translation.translate(language, " usporedi s ") +
                " " + rightExpression;
        this.solution = new String[3];
        this.solution = new String[] {
                leftExpression + " = " + leftSide,
                rightExpression + " = " + rightSide,
                rez
        };

        this.choices = new String[] { re1, re2, re3 };
        this.indexOfAnswer = getIndexOfResult(this.choices, rez);
    }
}
