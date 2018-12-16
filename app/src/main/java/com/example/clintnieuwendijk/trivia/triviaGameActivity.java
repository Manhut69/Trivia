package com.example.clintnieuwendijk.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class triviaGameActivity extends AppCompatActivity implements PostScoreRequest.Callback {

    Trivia trivia;
    TextView questionCount, scoreCount, questionText;
    Button answer0, answer1, answer2, answer3;
    ArrayList<Button> buttonList;
    String rightAnswer;
    ArrayList<String> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        questionCount = findViewById(R.id.questionCount);
        scoreCount = findViewById(R.id.scoreCount);
        questionText = findViewById(R.id.questionText);
        answer0 = findViewById(R.id.answer0);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);

        buttonList = new ArrayList<>(Arrays.asList(answer0, answer1, answer2, answer3));
        trivia = (Trivia) getIntent().getSerializableExtra("triviaQuestions");

        questionCount.setText(String.format("Question number: %s", String.valueOf(trivia.getCurrentQuestionCount())));
        scoreCount.setText(String.format("Score: %s", String.valueOf(trivia.getScore())));
        questionText.setText(trivia.getQuestion());
        answers = trivia.getAnswers();
        rightAnswer = answers.get(0);
        Collections.shuffle(answers);
        for (int i = 0; i < 4; i++) {
            Button btn = buttonList.get(i);
            btn.setText(answers.get(i));
        }
    }

    public void answerButtonClick(View view){
        Button btn = (Button) view;
        String answer = btn.getText().toString();
        if (answer.equals(rightAnswer)) {
            trivia.addScore();
        }
        if (trivia.gotoNextQuestion()) {
            questionCount.setText(String.format("Question number: %s", String.valueOf(trivia.getCurrentQuestionCount())));
            scoreCount.setText(String.format("Score: %s", String.valueOf(trivia.getScore())));
            questionText.setText(trivia.getQuestion());
            answers = trivia.getAnswers();
            rightAnswer = answers.get(0);
            Collections.shuffle(answers);
            for (int i = 0; i < 4; i++) {
                btn = buttonList.get(i);
                btn.setText(answers.get(i));
            }
        }
        else {
            postScore(trivia.getScore());
        }
    }

    public void postScore(int score){
        String username = getIntent().getStringExtra("username");
        if (!username.equals("none")) {
            PostScoreRequest x = new PostScoreRequest(this);
            x.getScore(this, username, score);
        }
    }


    @Override
    public void gotScore(JSONObject response) {
        Log.d("Response get", response.toString());
        Intent intent = new Intent(triviaGameActivity.this, MainActivity.class);
        intent.putExtra("Score", trivia.getScore());
        startActivity(intent);
    }

    @Override
    public void gotScoreError(String message) {
        Log.d("Got error: ", message);
    }
}