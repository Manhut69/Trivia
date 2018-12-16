package com.example.clintnieuwendijk.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.clintnieuwendijk.trivia.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class highScoreAdapter extends ArrayAdapter<highScores> {

    private ArrayList<highScores> highScoreList;

    highScoreAdapter(Context context, int resource, ArrayList<highScores> highScoreList) {
        super(context, resource, highScoreList);
        this.highScoreList = highScoreList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_highscore_adapter, parent, false);
        }

        TextView userNameView = convertView.findViewById(R.id.userNameText);
        TextView highScoreView = convertView.findViewById(R.id.highScoreTextView);

        highScores highScore = highScoreList.get(position);
        String username = highScore.getUsername();
        Log.d("Username", username);
        userNameView.setText(username);
        highScoreView.setText(highScore.getHighscore());

        return convertView;
    }
}