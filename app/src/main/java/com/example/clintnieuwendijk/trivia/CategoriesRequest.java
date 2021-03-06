package com.example.clintnieuwendijk.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    /*
        A request class for JSON files from the open trivia db
        the class requests all available categories from the database
        it then callbacks to the activity
     */

    Context context;
    Callback activity;

    CategoriesRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotCategories(HashMap<String, Integer> categoriesMap);
        void gotCategoriesError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCategoriesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            HashMap<String, Integer> categoriesMap = new HashMap<>();
            JSONArray triviaCategories = response.getJSONArray("trivia_categories");
            for (int i = 0; i < triviaCategories.length(); i++) {
                JSONObject category = (JSONObject) triviaCategories.get(i);
                categoriesMap.put(category.getString("name"), category.getInt("id"));
            }
            activity.gotCategories(categoriesMap);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getCategories(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String requestURL = "https://opentdb.com/api_category.php";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(requestURL, null, this, this);
        queue.add(jsonRequest);
    }
}
