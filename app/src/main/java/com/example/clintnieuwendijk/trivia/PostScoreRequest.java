package com.example.clintnieuwendijk.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostScoreRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    PostScoreRequest.Callback activity;

    PostScoreRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotScore(JSONObject response) throws JSONException;
        void gotScoreError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotScoreError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            activity.gotScore(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getScore(Callback activity, String user, int score){
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String requestURL = "https://ide50-manhut.c9users.io:8080/postscore?username=" + user + "&score=" + Integer.toString(score);
        Log.d("RequestURL = ", requestURL);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(requestURL, null, this, this);
        queue.add(jsonRequest);
    }
}
