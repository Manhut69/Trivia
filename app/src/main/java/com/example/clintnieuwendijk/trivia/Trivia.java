package com.example.clintnieuwendijk.trivia;

import android.util.Log;

import org.json.JSONArray;

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
        Log.e("Questions", answers.toString());
    }

    public String getQuestion() {
        return questions.get(currentQuestionCount);
    }

    public int getCurrentQuestionCount() {
        return currentQuestionCount;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getAnswers(){
        return answers.get(currentQuestionCount);
    }

    public boolean gotoNextQuestion() {
        currentQuestionCount++;
        if (currentQuestionCount < questions.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addScore() {
            score++;
    }

    public void postScore() {

    }
}
