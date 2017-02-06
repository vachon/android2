package com.example.vachonn.channelmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by vachonn on 06/02/2017.
 */
public class MessageActivity extends AppCompatActivity implements OnDownloadCompleteListener, View.OnClickListener{

    public ListView listView;
    public Button btEnvoyer;
    public EditText edMessage;
    public static final String PREFS_NAME = "MyPrefsFile";
    public int id;
    public Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_message_activity);

        edMessage = (EditText) findViewById(R.id.edMessage);

        listView = (ListView) findViewById(R.id.ltMessage);

        btEnvoyer = (Button) findViewById(R.id.btEnvoyer);
        btEnvoyer.setOnClickListener(this);

        id = getIntent().getIntExtra("id",0);

        Runnable r = new Runnable() {
            public void run() {
                HashMap<String, String> connectInfo = new HashMap<>();

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                connectInfo.put("accesstoken", settings.getString("accesstoken",""));
                connectInfo.put("channelid", String.valueOf(id));

                Async async = new Async(getApplicationContext(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=getmessages",1);
                async.setOnDownloadCompleteListener(MessageActivity.this);
                async.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);

    }

    public void onDownloadComplete(String result, int requestCode) {
        Gson gson = new Gson();
        if(requestCode == 1) {
            Messages obj = gson.fromJson(result, Messages.class);
            listView.setAdapter(new MessageAdapter(getApplicationContext(), R.layout.channel_message_activity, obj.messages));
        }else {
            MessageAEnvoyer obj = gson.fromJson(result, MessageAEnvoyer.class);
            if(obj.code == 200) {
                Toast.makeText(getApplicationContext(),"Message envoyé !",Toast.LENGTH_SHORT).show();
                edMessage.setText("");
            }
            else{
                Toast.makeText(getApplicationContext(),"Erreur !",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btEnvoyer)
        {
            HashMap<String, String> connectInfo = new HashMap<>();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            connectInfo.put("accesstoken", settings.getString("accesstoken",""));
            connectInfo.put("channelid", String.valueOf(id) );
            connectInfo.put("message", edMessage.getText().toString());
            Async Async = new Async(getApplicationContext(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=sendmessage",2);
            Async.setOnDownloadCompleteListener(this);
            Async.execute();
        }
    }
}

