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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class newGameStartActivity extends AppCompatActivity implements questionRequest.Callback {

    private Boolean fetchingInProgress = false;
    Spinner categorySpinner;
    Spinner difficultySpinner;
    HashMap<String, Integer> categoriesHashmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_start);

        categoriesHashmap = (HashMap<String, Integer>) getIntent().getSerializableExtra("categories");
        ArrayList<String> categories = new ArrayList<>(categoriesHashmap.keySet());

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

        else {
            Toast.makeText(this, "Fetching questions, one moment...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void gotQuestion(ArrayList<ArrayList<String>> questionList) {
        if (questionList.size() > 0) {
            Intent intent = new Intent(newGameStartActivity.this, triviaGameActivity.class);
            Trivia triviaGame = new Trivia(questionList);
            intent.putExtra("triviaQuestions", triviaGame);
            EditText usernameBox = findViewById(R.id.userNameText);
            if (!usernameBox.getText().toString().equals("")){
                intent.putExtra("username", usernameBox.getText().toString());
            }
            else {
                intent.putExtra("username", "none");
            }
            startActivity(intent);
        }
        else {
            gotQuestionError("Something went wrong, going back to the main menu...");
        }
    }

    @Override
    public void gotQuestionError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(newGameStartActivity.this, MainActivity.class));
    }
}
