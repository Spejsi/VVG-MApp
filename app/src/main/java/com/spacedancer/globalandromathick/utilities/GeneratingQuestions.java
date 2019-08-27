package com.spacedancer.globalandromathick.utilities;

import com.spacedancer.globalandromathick.questions.Question;
import com.spacedancer.globalandromathick.questions.QuestionTypeA;
import com.spacedancer.globalandromathick.questions.QuestionTypeB;
import com.spacedancer.globalandromathick.questions.QuestionTypeC;
import com.spacedancer.globalandromathick.questions.QuestionTypeD;

public class GeneratingQuestions {

    public static Question[] generate(String language, int difficulty) {

        Question[] allQuestions = new Question[] {
                new QuestionTypeA(language, 1, difficulty, '+', 2),
                new QuestionTypeA(language, 2, difficulty, '-', 2),
                new QuestionTypeA(language, 3, difficulty, '+', 1),
                new QuestionTypeA(language, 4, difficulty, '-', 0),
                new QuestionTypeA(language, 5, difficulty, '-', 1),
                new QuestionTypeA(language, 6, difficulty, '+', 0),

                new QuestionTypeB(language, 7, difficulty, 2),
                new QuestionTypeB(language, 8, difficulty, 2),
                new QuestionTypeB(language, 9, difficulty, 1),
                new QuestionTypeB(language, 10, difficulty, 0),

                new QuestionTypeC(language, 11, difficulty, '+', '+'),
                new QuestionTypeC(language, 12, difficulty, '+', '-'),
                new QuestionTypeC(language, 13, difficulty, '-', '+'),
                new QuestionTypeC(language, 14, difficulty, '-', '-'),

                new QuestionTypeD(language, 15, difficulty, '+', '-'),
                new QuestionTypeD(language, 16, difficulty, '-', '+'),
                new QuestionTypeD(language, 17, difficulty, '-', '-'),
                new QuestionTypeD(language, 18, difficulty, '+', '-'),
                new QuestionTypeD(language, 19, difficulty, '-', '+'),
                new QuestionTypeD(language, 20, difficulty, '-', '-')
        };
        return allQuestions;
    }
}
