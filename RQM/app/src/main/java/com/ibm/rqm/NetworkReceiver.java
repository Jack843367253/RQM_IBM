package com.ibm.rqm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by JACK on 2014/11/29.
 */
public class NetworkReceiver extends BroadcastReceiver{

    public static final String TAG = "NetworkReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNetworkDown = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

        if(isNetworkDown){
            Log.d(TAG, "onReceive: no connect");
        }else{
            Log.d(TAG, "onReceive: connected");
        }
    }
}
