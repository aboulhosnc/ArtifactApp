package com.example.chady.artifactapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by michaelpoblacion1 on 4/29/18.
 */

public class EventListAdapter extends ArrayAdapter<Events> {
    private Context mcontext;
    int mResource;

    public EventListAdapter(Context context, int resource, ArrayList<Events> objects){
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        String name = getItem(position).getName();
        String location = getItem(position).getLocation();

        Events event = new Events();
        event.setDate(date); event.setTime(time); event.setLocation(location); event.setName(name);

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.textViewDate);
        TextView tvTime = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvLocation = (TextView) convertView.findViewById(R.id.textViewLocation);

        tvDate.setText(date); tvTime.setText(time);
        tvName.setText(name); tvLocation.setText(location);

        return convertView;
    }
}
