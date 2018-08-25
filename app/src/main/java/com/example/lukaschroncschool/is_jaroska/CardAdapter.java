package com.example.lukaschroncschool.is_jaroska;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CardAdapter extends ArrayAdapter<Card> {
    public CardAdapter(Context context, ArrayList<Card> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Card card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bulletin_list_item, parent, false);
        }
        // Lookup view for data population
        TextView header = (TextView) convertView.findViewById(R.id.card_header);
        TextView body = (TextView) convertView.findViewById(R.id.card_body);
        TextView footer = (TextView) convertView.findViewById(R.id.card_footer);
        // Populate the data into the template view using the data object
        header.setText(card.header);
        body.setText(card.body);
        footer.setText(card.footer);
        // Return the completed view to render on screen
        return convertView;
    }

}
