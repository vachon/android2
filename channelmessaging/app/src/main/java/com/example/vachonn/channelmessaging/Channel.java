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
public class Channel {

    public final int channelID;
    public final String name;
    public final int connectedusers;

    public Channel(int channelID, String name, int connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }
}

