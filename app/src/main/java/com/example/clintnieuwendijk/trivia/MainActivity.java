/*
    A trivia game app that works with open trivia database
    By Clint Nieuwendijk
 */

package com.example.clintnieuwendijk.trivia;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    private boolean categoriesRequested = false;
    private HashMap<String, Integer> categoriesMap;

    // initialize app, request categories
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoriesRequest x = new CategoriesRequest(this);
        x.getCategories(this);
        if (getIntent().getIntExtra("score", -1) != -1) {
            Snackbar.make(findViewById(R.id.snackBarParent), "Your final score was: " + Integer.toString(getIntent().getIntExtra("score", -1)), Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    // go to new game
    public void newGameClick(View view){
        if (categoriesRequested) {
            Intent intent = new Intent(MainActivity.this, newGameStartActivity.class);
            intent.putExtra("categories", categoriesMap);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Fetching categories, one moment...", Toast.LENGTH_LONG).show();
        }
    }

    // view high scores
    public void highScoreClick(View view) {
        startActivity(new Intent(MainActivity.this, highScoreActivity.class));
    }

    // callback for fetching categories
    @Override
    public void gotCategories(HashMap<String, Integer> categoriesMap) {
        this.categoriesMap = categoriesMap;
        categoriesRequested = true;
    }

    // callback for failing to fetch categories
    @Override
    public void gotCategoriesError(String message) {
        Log.e("Error requesting categories", message);
    }
}
