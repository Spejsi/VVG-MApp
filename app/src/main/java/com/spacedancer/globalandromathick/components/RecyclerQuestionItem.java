package com.spacedancer.globalandromathick.components;

import com.spacedancer.globalandromathick.R;
import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.utilities.Translation;

public class RecyclerQuestionItem {
    private String questionNumber;
    private String questionTitle;
    private String expression;
    private String choicesTitle;
    private String choice1;
    private String choice2;
    private String choice3;

    private int imageCW;
    private String time;
    private String score;
    private String answeredTitle;
    private String choiceAnswered;

    private String solutionTitle;
    private String solutionLine1;
    private String solutionLine2;
    private String solutionLine3;

    public RecyclerQuestionItem( String language, Question question ) {

        this.questionNumber =
                Translation.translate(language, "Pitanje: ") +
                        question.getQuestionNumber() + "/20";
        this.questionTitle = Translation.translate(language, "Izračunaj:");
        this.expression = question.getExpression();

        this.choicesTitle = Translation.translate(language, "Ponuđeni odgovori:");
        this.choice1 = question.getChoices()[0];
        this.choice2 = question.getChoices()[1];
        this.choice3 = question.getChoices()[2];

        String sekunde = "";
        int sekcij = question.getElapsedTime() / 1000;

        if (sekcij < 10) {
            sekunde = "0" + sekcij;
        } else {
            sekunde = String.valueOf(sekcij);
        }
        int sekdec = question.getElapsedTime() % 1000;

        sekunde += ".";
        if (sekdec < 100) { sekunde += "0"; }
        if (sekdec < 10) { sekunde += "0"; }
        sekunde += String.valueOf(sekdec);

        this.time = Translation.translate(language, "Vrijeme: ") + sekunde;
        this.score = Translation.translate(language, "Bodova: ") +
                question.getScore();
        this.answeredTitle = Translation.translate(language, "Odgovoreno:");
        this.choiceAnswered = question.getChoices()[question.getIndexOfAnswered()];
        this.solutionTitle = Translation.translate(language, "Rješenje:");
        this.solutionLine1 = question.getSolution()[0];
        this.solutionLine2 = question.getSolution()[1];
        this.solutionLine3 = question.getSolution()[2];

        if (this.choiceAnswered.equals(question.getChoices()[question.getIndexOfAnswer()])){
            this.imageCW = R.mipmap.checkyes;
        } else {
            this.imageCW= R.mipmap.checkno;
        }
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getChoicesTitle() {
        return choicesTitle;
    }

    public void setChoicesTitle(String choicesTitle) {
        this.choicesTitle = choicesTitle;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public int getImageCW() {
        return imageCW;
    }

    public void setImageCW(int imageCW) {
        this.imageCW = imageCW;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAnsweredTitle() {
        return answeredTitle;
    }

    public void setAnsweredTitle(String answeredTitle) {
        this.answeredTitle = answeredTitle;
    }

    public String getChoiceAnswered() {
        return choiceAnswered;
    }

    public void setChoiceAnswered(String choiceAnswered) {
        this.choiceAnswered = choiceAnswered;
    }

    public String getSolutionTitle() {
        return solutionTitle;
    }

    public void setSolutionTitle(String solutionTitle) {
        this.solutionTitle = solutionTitle;
    }

    public String getSolutionLine1() {
        return solutionLine1;
    }

    public void setSolutionLine1(String solutionLine1) {
        this.solutionLine1 = solutionLine1;
    }

    public String getSolutionLine2() {
        return solutionLine2;
    }

    public void setSolutionLine2(String solutionLine2) {
        this.solutionLine2 = solutionLine2;
    }

    public String getSolutionLine3() {
        return solutionLine3;
    }

    public void setSolutionLine3(String solutionLine3) {
        this.solutionLine3 = solutionLine3;
    }
}
