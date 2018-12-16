package com.example.clintnieuwendijk.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class questionRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    Context context;
    Callback activity;
    private String difficulty, category;
    int amount;

    questionRequest(Context context, String category, String difficulty, int amount) {
        this.context = context;
        this.category = category;
        this.difficulty = difficulty;
        this.amount = amount;
    }

    public interface Callback {
        void gotQuestion(ArrayList<ArrayList<String>> answerList);
        void gotQuestionError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<ArrayList<String>> answerList = new ArrayList<>();
        try {
            JSONArray questionJSON = (JSONArray) response.get("results");
            if (response.length() == 0) {
                activity.gotQuestionError("Array size is 0");
            }
            for (int i = 0; i < questionJSON.length(); i++) {
                ArrayList<String> answers = new ArrayList<>();
                JSONObject fullQuestion = questionJSON.getJSONObject(i);

                answers.add(fullQuestion.getString("question"));
                answers.add(fullQuestion.getString("correct_answer"));

                JSONArray wrongAnswers = fullQuestion.getJSONArray("incorrect_answers");
                for (int j = 0; j < wrongAnswers.length(); j++) {
                    answers.add(wrongAnswers.getString(j));
                }
                answerList.add(answers);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Answerlist", answerList.toString());
        activity.gotQuestion(answerList);
    }

    void getQuestions(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String requestURL = "https://opentdb.com/api.php?amount=" + Integer.toString(amount) + "&category=" + category + "&difficulty=" + difficulty + "&type=multiple";
        Log.d("Request", requestURL);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(requestURL, null, this, this);
        queue.add(jsonRequest);
    }
}
