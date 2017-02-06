package com.example.vachonn.channelmessaging;

import android.content.Context;
import android.os.AsyncTask;

import java.net.*;
import java.io.*;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by vachonn on 20/01/2017.
 */
public class Async extends AsyncTask<Long,Integer, String> {

    private Context Context;
    public int requestCode;
    private String url;
    private HashMap<String, String> connectInfo = new HashMap<>();

    public ArrayList<OnDownloadCompleteListener> listeners = new ArrayList<>();

    public Async(Context myContext, HashMap connectInfo, String myUrl, int myRequestCode)
    {
        this.Context = myContext;
        this.connectInfo = connectInfo;
        this.url = myUrl;
        this.requestCode = myRequestCode;
    }

    @Override protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

    @Override protected String doInBackground(Long... params)
    {
        return performPostCall(url, connectInfo);
    }

    @Override protected void onPostExecute(String result)
    {
        for(OnDownloadCompleteListener listener : listeners)
        {
            listener.onDownloadComplete(result, requestCode);
        }
    }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        listeners.add(listener);
    }
}


