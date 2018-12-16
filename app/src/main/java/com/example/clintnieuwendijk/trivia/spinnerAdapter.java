package com.example.clintnieuwendijk.trivia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class spinnerAdapter extends ArrayAdapter<String> {

    ArrayList<String> entries;

    public spinnerAdapter(Context context, int resource, ArrayList<String> entries) {
        super(context, resource);
        this.entries = entries;
    }

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