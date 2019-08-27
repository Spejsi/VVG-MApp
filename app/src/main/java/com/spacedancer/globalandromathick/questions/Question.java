package com.spacedancer.globalandromathick.questions;

public abstract class Question {
    String title;
    Integer questionNumber = 0;
    String expression;
    String[] choices;
    Integer indexOfAnswer;
    String[] solution;

    Integer elapsedTime = 0;
    Integer score = 0;
    Integer indexOfAnswered = 0;

    public Question() {
        super();
    }

    public Question(String title, Integer questionNumber, String expression, String[] choices, Integer indexOfAnswer, String[] solution, Integer elapsedTime, Integer score) {
        this.questionNumber = questionNumber;
        this.title = title;
        this.expression = expression;
        this.choices = choices;
        this.indexOfAnswer = indexOfAnswer;
        this.solution = solution;
        this.elapsedTime = elapsedTime;
        this.score = score;
    }

    public Integer getQuestionNumber() { return questionNumber; }

    public void setQuestionNumber(Integer questionNumber) { this.questionNumber = questionNumber; }

    public String getTitle() { return title; }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public Integer getIndexOfAnswer() {
        return indexOfAnswer;
    }

    public void setIndexOfAnswer(Integer indexOfAnswer) {
        this.indexOfAnswer = indexOfAnswer;
    }

    public String[] getSolution() {
        return solution;
    }

    public void setSolution(String[] solution) {
        this.solution = solution;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIndexOfAnswered() { return indexOfAnswered; }

    public void setIndexOfAnswered(Integer indexOfAnswered) { this.indexOfAnswered = indexOfAnswered; }

    public abstract void generateData(String language);

    public static String[] shuffleAndResizeArray(String rez1, String rez2, String rez3, String rez4, String rez) {

        String[] array = new String[] {rez1, rez2, rez3, rez4};

        for (int i = 0; i < array.length; i++) {
            int index = (int) (Math.random() * array.length);
            String a = array[index];
            array[index] = array[i];
            array[i] = a;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].equals("")) {
                array[i] = array[i+1];
                array[i+1] = "";
            }
        }

        String[] newArray = new String[3];
        newArray[0] = array[0];
        newArray[1] = array[1];
        newArray[2] = array[2];

        int indexOfRes = (int) (Math.random() * newArray.length);
        newArray[indexOfRes] = rez;

        return newArray;
    }

    public static int getIndexOfResult(String[] choices, String rez) {
        int index = -1;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i].equals(rez)) index = i;
        }
        return index;
    }

    public static char changeOperator(char operator) {
        if (operator == '+') operator = '-';
        else operator = '+';
        return operator;
    }
}