package com.example.vachonn.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.*;

/**
 * Created by vachonn on 20/01/2017.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btValider;

    private EditText edId;
    private EditText edMdp;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btValider = (Button) findViewById(R.id.btValider);
        btValider.setOnClickListener(this);


        edId = (EditText) findViewById(R.id.edId);
        edId.setText("nvach");

        edMdp = (EditText) findViewById(R.id.edMdp);
        edMdp.setText("nicolasvachon");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btValider)
        {
            if (ConnectedTest.isConnectedInternet(LoginActivity.this)) {
                HashMap<String, String> connectInfo = new HashMap<>();
                connectInfo.put("username", edId.getText().toString());
                connectInfo.put("password", edMdp.getText().toString());
                Async Async = new Async(getApplicationContext(), connectInfo, "http://www.raphaelbischof.fr/messaging/?function=connect", 3);
                Async.setOnDownloadCompleteListener(this);
                Async.execute();
            }
            else
                Toast.makeText(getApplicationContext(),"Pas d'internet",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDownloadComplete(String result, int requestCode) {
        Gson gson = new Gson();
        Callback obj = gson.fromJson(result, Callback.class);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accesstoken", obj.accesstoken);
        editor.commit();

        if(obj.code !=200)
            Toast.makeText(getApplicationContext(),"Erreur !",Toast.LENGTH_SHORT).show();
        else {
            Intent myIntent = new Intent(getApplicationContext(), ChannelListActivity.class);
            startActivity(myIntent);
        }
    }
}