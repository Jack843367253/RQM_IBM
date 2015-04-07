package com.ibm.rqm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MyInfo extends Activity {


    private SharedPreferences mPrefs;
    private Button mLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mPrefs = ((IBMApplication)getApplication()).getPrefs();
        mLogoutBtn = (Button)findViewById(R.id.logout_Button);
        mLogoutBtn.setOnClickListener(new OnLogoutClickListener());
    }

    private class OnLogoutClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            mPrefs.edit().putBoolean("isLogined", false).commit();
            Toast.makeText(MyInfo.this, "Logout Success!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

}
