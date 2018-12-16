package com.example.clintnieuwendijk.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class newGameStartActivity extends AppCompatActivity implements questionRequest.Callback {

    private ArrayList<ArrayList<String>> questionList;
    private Boolean fetchingInProgress = false;
    private Boolean questionsRequested = false;
    Spinner categorySpinner;
    Spinner difficultySpinner;
    HashMap<String, Integer> categoriesHashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_start);

        ArrayList<String> categories = new ArrayList<>();
        categoriesHashmap = (HashMap<String, Integer>) getIntent().getSerializableExtra("categories");
        for (String key : categoriesHashmap.keySet()) {
            categories.add(key);
        }

        categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        ArrayList<String> difficulties = new ArrayList<>(Arrays.asList("easy", "medium", "hard"));
        difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficulties);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

    }

    public void startGameActivity(View view) {
        if (!fetchingInProgress) {
            TextView numQuestions = findViewById(R.id.numQuestions);
            int numberOfQuestions = 10;
            try {
                numberOfQuestions = Integer.parseInt(numQuestions.getText().toString());
            }
            catch(Exception e) {
                Log.d("Error: ", e.getMessage());
            }
            String categoryId = Integer.toString(categoriesHashmap.get(categorySpinner.getSelectedItem().toString()));
            questionRequest x = new questionRequest(this, categoryId, difficultySpinner.getSelectedItem().toString(), numberOfQuestions);
            x.getQuestions(this);
            fetchingInProgress = true;
        }

        if (questionsRequested) {
            Intent intent = new Intent(newGameStartActivity.this, triviaGameActivity.class);
            Trivia triviaGame = new Trivia(questionList);
            intent.putExtra("triviaQuestions", triviaGame);
            EditText usernameBox = findViewById(R.id.userNameText);
            if (usernameBox.getText().toString() != ""){
                intent.putExtra("username", usernameBox.getText().toString());
            }
            else {
                intent.putExtra("username", "none");
            }
            startActivity(intent);

        }

        else {
            Toast.makeText(this, "Fetching questions, one moment...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean gotQuestion(ArrayList<ArrayList<String>> questionList) {
        this.questionList = questionList;
        if (questionList.size() > 0) {
            questionsRequested = true;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void gotQuestionError(String message) {
        Log.d("Something", "WentWrong");
    }
}
