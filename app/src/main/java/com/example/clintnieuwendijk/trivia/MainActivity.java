package com.example.clintnieuwendijk.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    private boolean categoriesRequested = false;
    private HashMap<String, Integer> categoriesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CategoriesRequest x = new CategoriesRequest(this);
        x.getCategories(this);
    }

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

    public void highScoreClick(View view) {
        startActivity(new Intent(MainActivity.this, highScoreActivity.class));
    }

    @Override
    public boolean gotCategories(HashMap<String, Integer> categoriesMap) {
        this.categoriesMap = categoriesMap;
        categoriesRequested = true;
        return true;
    }

    @Override
    public void gotCategoriesError(String message) {
        Log.e("Error requesting categories", message);
    }
}
