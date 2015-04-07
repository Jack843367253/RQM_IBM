package com.ibm.rqm;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.client.HttpClient;

/**
 * Created by JACK on 2014/12/14.
 */
public class IBMApplication extends Application implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = "IBMApplication";

    private AlarmManager mAManager;
    private SharedPreferences mPrefs;
    private boolean serviceRunning;
    private HttpClientHelper httpClientHelper;

    private boolean isLogined;
    private boolean isNotifyOpen;

    public SharedPreferences getPrefs() {
        return mPrefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Application create..");
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mPrefs.registerOnSharedPreferenceChangeListener(this);

        mAManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        isLogined = mPrefs.getBoolean("isLogined", false);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("isLogined")){
            if(!(isLogined = sharedPreferences.getBoolean(key, false))){
                Intent intent = new Intent(this, LoginActivity.class);
                //launch the login Activity. Clear all Activities in the Stack
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }else if(key.equals("user_name")){
            Log.d(TAG, "userName Changed!");
        }else if(key.equals("notifications_enable")){
            if(sharedPreferences.getBoolean(key, false)){
                //enable notification
                mAManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                    getRepeatTimeLength()*60*1000, generatePendingIntent());
                Log.d(TAG, "RQM notification enabled, Frequency:" + getRepeatTimeLength());
            }else {
                mAManager.cancel(generatePendingIntent());
                Log.d(TAG, "RQM notification cancled");
            }
        }

    }

    private PendingIntent generatePendingIntent(){
        Intent intent = new Intent(this, RQMReceiver.class);

        //TODO: setting the data that is going to sent to Receiver.
        //for exmaple:
        //intent.putExtra("city", mPrefs.getString("city", "NoPlace"));

        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        return pIntent;
    }



    private int getRepeatTimeLength(){
        return Integer.parseInt(mPrefs.getString("notification_frequency", "0"));
    }

    public boolean isLogined() {
        return isLogined;
    }

    public boolean isNotifyOpen() {
        isNotifyOpen = mPrefs.getBoolean("notifications_enable", false);
        return isNotifyOpen;
    }

    public synchronized HttpClient getHttpClient() {
        return  httpClientHelper.getHttpClient();
    }
}
