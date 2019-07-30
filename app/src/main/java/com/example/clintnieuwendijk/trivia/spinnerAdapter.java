package com.example.clintnieuwendijk.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class spinnerAdapter extends ArrayAdapter<String> {

    /*
        A simple spinner adapter for the new game activity.
        It binds the view for the requested categories
     */

    ArrayList<String> entries;

    public spinnerAdapter(Context context, int resource, ArrayList<String> entries) {
        super(context, resource);
        this.entries = entries;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_spinner_adapter, parent, false);
        }


        TextView spinnerEntry = convertView.findViewById(R.id.spinnerEntry);
        spinnerEntry.setText(entries.get(position));

        return convertView;
    }
}
