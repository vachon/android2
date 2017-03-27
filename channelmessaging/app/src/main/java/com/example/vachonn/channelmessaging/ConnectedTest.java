package com.example.vachonn.channelmessaging;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by vachonn on 27/03/2017.
 */
public class ConnectedTest {
        public static boolean isConnectedInternet(Activity activity)
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null)
            {
                NetworkInfo.State networkState = networkInfo.getState();
                if (networkState.compareTo(NetworkInfo.State.CONNECTED) == 0)
                {
                    return true;
                }
                else return false;
            }
            else return false;
        }
}
