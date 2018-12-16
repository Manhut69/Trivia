package com.example.clintnieuwendijk.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {

    private ArrayList<String> questions;
    private ArrayList<ArrayList<String>> answers;
    private int currentQuestionCount = 0;
    private int score = 0;

    public Trivia(ArrayList<ArrayList<String>> questionsAndAnswers) {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        for (int i = 0; i < questionsAndAnswers.size(); i++) {
            questions.add(questionsAndAnswers.get(i).get(0));
            ArrayList<String> answerArray = new ArrayList<>();
            for (int j = 1; j < questionsAndAnswers.get(i).size(); j++) {
                answerArray.add(questionsAndAnswers.get(i).get(j));
            }
            answers.add(answerArray);
        }
    }

    String getQuestion() {
        return questions.get(currentQuestionCount);
    }

    int getCurrentQuestionCount() {
        return currentQuestionCount;
    }

    int getScore() {
        return score;
    }

    ArrayList<String> getAnswers(){
        return answers.get(currentQuestionCount);
    }

    boolean gotoNextQuestion() {
        currentQuestionCount++;
        return currentQuestionCount < questions.size();
    }

    void addScore() {
            score++;
    }

}
