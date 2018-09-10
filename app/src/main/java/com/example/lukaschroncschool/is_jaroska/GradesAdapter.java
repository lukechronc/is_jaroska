package com.example.lukaschroncschool.is_jaroska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GradesAdapter extends ArrayAdapter<Card> {
    public GradesAdapter(Context context, ArrayList<Card> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Card card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grades_layout, parent, false);
        }
        // Lookup view for data population
        TextView subject = (TextView) convertView.findViewById(R.id.subject);
        TextView teacher = (TextView) convertView.findViewById(R.id.teacher);
        TextView grades = (TextView) convertView.findViewById(R.id.grades);
        // Populate the data into the template view using the data object
        subject.setText(card.header);
        teacher.setText(card.body);
        grades.setText(card.footer);
        // Return the completed view to render on screen
        return convertView;
    }

}