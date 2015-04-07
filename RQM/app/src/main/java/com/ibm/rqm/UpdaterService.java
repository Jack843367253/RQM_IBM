package com.ibm.rqm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.apache.http.client.HttpClient;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdaterService extends IntentService {

    private static final String TAG = "UpdaterService";
    private static final String URL_CONTEXT = "qm";
    private static final String RESOURCE_PATH = "/" + URL_CONTEXT + "/service/com.ibm.rqm.integration.service.IIntegrationService/resources";
    private String mHost;
    private int mPort;

    private SharedPreferences mPrefs;
    private IBMApplication mApp;
    private HttpClient mHttpClient;

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.ibm.rqm.action.FOO";
    private static final String ACTION_BAZ = "com.ibm.rqm.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.ibm.rqm.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.ibm.rqm.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdaterService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UpdaterService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = (IBMApplication)getApplication();
        mPrefs = mApp.getPrefs();
        mHttpClient = mApp.getHttpClient();

        mHost = mPrefs.getString("host", null);
        mPort = mPrefs.getInt("port", 0);
    }

    public UpdaterService() {
        super("UpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    //TODO:refactor, just for test! think about the dataflow.
    //need to build the database!
    public void fetchAndUpdateProjects(){


        //TODO parse the xml and update the database.

    }


}
