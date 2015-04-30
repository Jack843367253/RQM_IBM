package com.ibm.rqm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{

    private static final String TAG = "LoginActivity";
    private SharedPreferences mPrefs;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;
    private EditText mHostView;
    private EditText mPortView;
    private View mProgressView;
    private View mLoginFormView;
    private ImageView mAvatarView;
    private Bitmap userAvatar;

    //for Login test
    private static final String HOST = "https://10.110.210.103";
    private static final int PORT = 9443;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);

        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.userName);

        mPasswordView = (EditText) findViewById(R.id.password);
        mAvatarView = (ImageView) findViewById(R.id.avatar);
        mAvatarView.setImageResource(R.drawable.avatar);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mHostView = (EditText) findViewById(R.id.host);
        mPortView = (EditText) findViewById(R.id.port);

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mPrefs = ((IBMApplication)getApplication()).getPrefs();
        if(mPrefs.getBoolean("isLogined", false)){

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            String userName = mPrefs.getString("username", null);
            String password = mPrefs.getString("password", null);
            String host = mPrefs.getString("host", null);
            Integer port = mPrefs.getInt("port", 0);

            //autosave
            mUserNameView.setText(userName);
            mPasswordView.setText(password);
            mHostView.setText(host);
            mPortView.setText(port.toString());

            showProgress(true);

            //UserLoginTask进行登录。
            mAuthTask = new UserLoginTask(userName, password, HOST, PORT);
            mAuthTask.execute((Void) null);
        }

    }


    //press twice to eixt
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Log.d(TAG, "Back button pressed!");
        if((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            Toast.makeText(this, "Press back twice to exit.", Toast.LENGTH_SHORT).show();
        }else {
            finish();
            System.exit(0);
        }

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid userName, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);
        mHostView.setError(null);
        mPortView.setError(null);

        // Store values at the time of the login attempt.
        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String host = mHostView.getText().toString();
        String portStr = mPortView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        //check for a valid host and port
        if(TextUtils.isEmpty(host)){
            mHostView.setError(getString(R.string.error_field_required));
            focusView = mHostView;
            cancel = true;
        }

        if(TextUtils.isEmpty(portStr)){
            mPortView.setError(getString(R.string.error_field_required));
            focusView = mPortView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            //mAuthTask = new userLoginTask(userName, password, host, Integer.parseInt(portStr));
            mAuthTask = new UserLoginTask(userName, password, HOST, PORT);
            mAuthTask.execute((Void) null);

        }
    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUserName;
        private final String mPassword;
        private final String mHost;
        private final int mPort;

        private final String ROOT_URL;
        private final String LOGIN_URL;

        private HttpClient httpClient;
        private final int LOGIN_SUCCESS = 1;
        private final int FAIL_ERROR = -1;
        private final int FAIL_CONNECTION = -2;

        UserLoginTask(String userName, String password, String host, int port) {
            mUserName = userName;
            mPassword = password;
            mHost = host;
            mPort = port;
            ROOT_URL = HOST + ":" + PORT + "/qm";
            LOGIN_URL = ROOT_URL + "/authenticated/j_security_check";
            httpClient = ((IBMApplication)getApplication()).getHttpClient();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            // attempt authentication against a network service.
          //Has tested !!
          /*  try {
                HttpGet get = new HttpGet(ROOT_URL);
                HttpResponse response = httpClient.execute(get);
                response.getEntity().consumeContent();

                HttpPost post = new HttpPost(LOGIN_URL);
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("j_username", mUserName));
                nvps.add(new BasicNameValuePair("j_password", mPassword));
                post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                HttpResponse loginRes = httpClient.execute(post);
                Log.d(TAG, "Login Response code:" + loginRes.getStatusLine().getStatusCode());

                Header header = loginRes.getFirstHeader("X-com-ibm-team-repository-web-auth-msg");

                if(header != null && (header.getValue().equals( "authfailed") || header.getValue().equals("authrequired"))){
                    Log.d(TAG, "Login Failed, username or password error");
                    return FAIL_ERROR;
                }


                if (loginRes.getStatusLine().getStatusCode() == 200) {
                    Log.d(TAG, "Login Success!");
                    return LOGIN_SUCCESS;
                }
            }catch (IOException e){
                Log.d(TAG, "Login Failed, https connection failed");
                e.printStackTrace();
                return FAIL_CONNECTION;
            }
*/



            //Only For test!
           if(mUserName.equals("test") && mPassword.equals("test")){
                return LOGIN_SUCCESS;
            }
            return FAIL_CONNECTION;
        }

        @Override
        protected void onPostExecute(final Integer code) {
            mAuthTask = null;
            showProgress(false);

            if (LOGIN_SUCCESS == code) {
                //change the value of isLogined! and start the MainActivity!
                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean("isLogined", true);
                editor.putString("username", mUserName);
                editor.putString("password", mPassword);
                editor.putString("host", mHost);
                editor.putInt("port", mPort);
                editor.commit();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                if(FAIL_ERROR == code){
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }else if(FAIL_CONNECTION == code){
                    Toast.makeText(LoginActivity.this, getString(R.string.error_connection_failed),
                            Toast.LENGTH_SHORT).show();
                    mHostView.requestFocus();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



