package com.example.vachonn.channelmessaging.Fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vachonn.channelmessaging.Async;
import com.example.vachonn.channelmessaging.ConnectedTest;
import com.example.vachonn.channelmessaging.MessageAEnvoyer;
import com.example.vachonn.channelmessaging.MessageAdapter;
import com.example.vachonn.channelmessaging.Messages;
import com.example.vachonn.channelmessaging.OnDownloadCompleteListener;
import com.example.vachonn.channelmessaging.R;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by vachonn on 22/03/2017.
 */
public class MessageFragment extends Fragment implements OnDownloadCompleteListener, View.OnClickListener{



    public int id=-1;
    public Handler handler = new Handler();
    public static final String PREFS_NAME = "MyPrefsFile";
    public ListView listViewMessage;
    public Button valider;
    public EditText messageAEnvoyer;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.message_activity_fragment, container);

        listViewMessage = (ListView) v.findViewById(R.id.listViewMessages);
        valider = (Button) v.findViewById(R.id.valider);
        messageAEnvoyer = (EditText) v.findViewById(R.id.editTextMessage);
        valider.setOnClickListener((View.OnClickListener) this);

        return v;
    }

    @Override
    public void onDownloadComplete(String result, int requestCode) {
        if (ConnectedTest.isConnectedInternet(getActivity())) {
            if (getActivity() != null) {
                Gson gson = new Gson();
                if (requestCode == 2) {
                    Messages messages = gson.fromJson(result, Messages.class);
                    listViewMessage.setAdapter(new MessageAdapter(getActivity().getApplicationContext(), R.layout.row_message, messages.messages));
                } else if (requestCode == 1) {
                    MessageAEnvoyer messageEnvoye = gson.fromJson(result, MessageAEnvoyer.class);
                    if (messageEnvoye.code == 200)
                        Toast.makeText(getActivity(), "Message envoye", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Message non envoye", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.valider)
        {
            if (ConnectedTest.isConnectedInternet(getActivity())) {
                HashMap<String, String> connectInfo = new HashMap<>();
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                connectInfo.put("accesstoken", settings.getString("accesstoken", ""));
                connectInfo.put("channelid", String.valueOf(id));
                connectInfo.put("message", messageAEnvoyer.getText().toString());
                Async login = new Async(getActivity().getApplicationContext(), connectInfo, "http://www.raphaelbischof.fr/messaging/?function=sendmessage", 1);
                login.setOnDownloadCompleteListener(this);
                login.execute();
                messageAEnvoyer.setText("");
            }
        }
    }




}
