package com.example.clintnieuwendijk.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class highScoreActivity extends AppCompatActivity implements PostScoreRequest.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        PostScoreRequest x = new PostScoreRequest(this);
        x.getScore(this, "user", -1);
    }

    @Override
    public void gotScore(JSONObject response) throws JSONException {
        JSONArray highScoreJSONArray = response.getJSONArray("database");
        ArrayList<highScores> highScoreList = new ArrayList<>();
        for (int i = 0; i < highScoreJSONArray.length(); i++) {
            JSONObject userHighScore = highScoreJSONArray.getJSONObject(i);
            String userName = userHighScore.getString("user");
            String highScore = userHighScore.getString("highscore");
            highScoreList.add(new highScores(userName, highScore));
        }

        highScoreAdapter highScoresArrayAdapter = new highScoreAdapter(this, R.layout.activity_highscore_adapter, highScoreList);
        ListView lv = findViewById(R.id.highScoreListView);
        lv.setAdapter(highScoresArrayAdapter);
    }

    @Override
    public void gotScoreError(String message) {
        Toast.makeText(this, "Error retrieving highscores, please try again later", Toast.LENGTH_SHORT).show();
        Log.e("Error: ", message);
    }
}
