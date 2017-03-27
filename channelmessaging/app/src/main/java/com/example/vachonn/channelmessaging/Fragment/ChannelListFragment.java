package com.example.vachonn.channelmessaging.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vachonn.channelmessaging.ConnectedTest;
import com.example.vachonn.channelmessaging.MessageActivity;
import com.google.gson.Gson;

import java.util.HashMap;

import com.example.vachonn.channelmessaging.OnDownloadCompleteListener;


import com.example.vachonn.channelmessaging.Async;
import com.example.vachonn.channelmessaging.ChannelAdapter;
import com.example.vachonn.channelmessaging.ChannelListActivity;
import com.example.vachonn.channelmessaging.Channels;
import com.example.vachonn.channelmessaging.OnDownloadCompleteListener;
import com.example.vachonn.channelmessaging.R;


/**
 * Created by vachonn on 17/03/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener, AdapterView.OnItemClickListener{

    ListView listView;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Channels ch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.channel_list_activity_fragment, container);
        listView = (ListView) v.findViewById(R.id.listView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        if (ConnectedTest.isConnectedInternet(getActivity())) {
            super.onActivityCreated(savedInstanceState);
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            HashMap<String, String> infoConnexion = new HashMap<>();
            infoConnexion.put("accesstoken", settings.getString("accesstoken", ""));
            Async login = new Async(getActivity(), infoConnexion, "http://www.raphaelbischof.fr/messaging/?function=getchannels", 0);
            login.setOnDownloadCompleteListener(this);
            login.execute();
        }
    }


    @Override
    public void onDownloadComplete(String result, int requestCode) {
        Gson gson = new Gson();
        ch = gson.fromJson(result, Channels.class);

        listView.setAdapter(new ChannelAdapter(getActivity(), R.layout.channel_activity_activity ,R.layout.rowlayout, ch.channels));
        listView.setOnItemClickListener((ChannelListActivity)getActivity());


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(),MessageActivity.class);
        myIntent.putExtra("id", ch.channels.get(position).channelID);
        startActivity(myIntent);
    }
}
