package com.example.vachonn.channelmessaging;

/**
 * Created by vachonn on 06/02/2017.
 */
public class Message {

    public final int userID;
    public final String username;
    public final String message;
    public final String date;
    public final String imageUrl;

    public Message(int userID, String username, String message, String date, String imageUrl) {
        this.userID = userID;
        this.username = username;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
    }
}

