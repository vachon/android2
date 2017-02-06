package com.example.vachonn.channelmessaging;

/**
 * Created by vachonn on 06/02/2017.
 */
public class Callback {

    private String response;
    public final int code;
    public final String accesstoken;

    public Callback(String response, int code, String accesstoken) {
        this.response = response;
        this.code = code;
        this.accesstoken = accesstoken;
    }
}