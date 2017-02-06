package com.example.vachonn.channelmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



/**
 * Created by vachonn on 06/02/2017.
 */
public class ChannelAdapter extends ArrayAdapter<Channel> {

    TextView textView;
    TextView textView2;
    public ChannelAdapter(Context context, int resource, int textViewResourceId, List<Channel> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        textView = (TextView) rowView.findViewById(R.id.txtList);
        textView.setText(getItem(position).name);

        textView2 = (TextView) rowView.findViewById(R.id.txtList2);
        textView2.setText("Nombre d'utilisateurs connectés : "+ getItem(position).connectedusers);

        return rowView;
    }

}

