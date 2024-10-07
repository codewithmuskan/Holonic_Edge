package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends ArrayAdapter<String> {
    private ArrayList<String> messages;
    private Context context;

    public ChatAdapter(Context context, ArrayList<String> messages) {
        super(context, 0, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the message for this position
        String message = getItem(position);

        // Check if an existing view is being reused, otherwise inflate a new view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Lookup view for data population
        TextView tvMessage = convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        tvMessage.setText(message);

        // Return the completed view to render on screen
        return convertView;
    }
}
