package com.ibm.rqm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final String ARG_SECTION_NUM = "section_num";

    private static final String HOST = "https://10.205.18.163";
    private static final String PORT = "9443";
    private static final String ROOT_URL = HOST + ":" + PORT + "/qm";
    private static final String LOGIN_URL = ROOT_URL + "/authenticated/j_security_check";
    private static final String ADMIN_URL = ROOT_URL + "/admin";
    private static final String RESOURCE_PATH = "/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources";


    //是否已经登陆标志
    static boolean isLogined = true;

    private int sectionNum;

    private OnFragmentInteractionListener mListener;

    private MainActivity myActivity;

     //测试网络连接。
    HttpClient httpClient;
    static  Bitmap bitmap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNum Parameter 1.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance(int sectionNum) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUM, sectionNum);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNum = getArguments().getInt(ARG_SECTION_NUM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button btnDownload = (Button)rootView.findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new onDownloadBtnClickedListener());

        Button btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new onLoginBtnClickedListener());

        EditText editText = (EditText)rootView.findViewById(R.id.resourceName);
     //   editText.getText().toString();
        return rootView;
    }




    class onDownloadBtnClickedListener implements View.OnClickListener{


        String resourceStr;
        String xmlStr;
        String host;
        String projectStr;

        @Override
        public void onClick(View v) {
            if(!isLogined){
                new AlertDialog.Builder(myActivity).setMessage("Please Login first!")
                        .setTitle("Alert!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                return;
            }

            View rootView = myActivity.findViewById(R.id.fragment_main);
            EditText editText = (EditText)rootView.findViewById(R.id.resourceName);
            resourceStr = editText.getText().toString();
            EditText editText1 = (EditText)rootView.findViewById(R.id.host);
            host = editText1.getText().toString();
            EditText editText2 = (EditText)rootView.findViewById(R.id.projectName);
            projectStr = editText2.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Build the resource URL
                        projectStr = "JKE+Banking";
                        resourceStr = "testplan";

                        String urlStr = HOST + ":" + PORT + RESOURCE_PATH  + "/" + resourceStr;

                        HttpGet get = new HttpGet(urlStr);
                        HttpResponse response = httpClient.execute(get);

                        if(response.getStatusLine().getStatusCode() == 200){
                            BufferedInputStream in = new BufferedInputStream(response.getEntity().getContent());
                            StringBuilder strBuilder = new StringBuilder();
                            byte[] buffer = new byte[1024];
                            int bytesRead = 0;
                            while((bytesRead = in.read(buffer)) != -1){
                                String readStr = new String(buffer, 0, bytesRead);
                                strBuilder.append(readStr);
                            }
                            xmlStr = strBuilder.toString();
                        }else{
                            Log.d(TAG, "Download xml failed");
                        }



                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d(TAG, "Connect fail!");
                    }


                }
            }).start();

            TextView xmlText = (TextView)myActivity.findViewById(R.id.fragment_main).findViewById(R.id.xmlText);
            xmlText.setText(xmlStr);
        }

    }

    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
          //还没用到listener, 且MainActivity还没实现这个接口。
          //  mListener = (OnFragmentInteractionListener) activity;

            //在attach主界面的时候，告诉主界面设置相应的标题。

          myActivity = ((MainActivity) activity);
          myActivity.onSectionAttached(
                  getArguments().getInt(ARG_SECTION_NUM));

          httpClient = ((IBMApplication)myActivity.getApplication()).getHttpClient();

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private class onLoginBtnClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpGet get = new HttpGet(ROOT_URL);
                        HttpResponse response = httpClient.execute(get);
                        response.getEntity().consumeContent();


                        HttpPost post = new HttpPost(LOGIN_URL);
                        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                        nvps.add(new BasicNameValuePair("j_username", "jack"));
                        nvps.add(new BasicNameValuePair("j_password", "wangjie123"));
                        post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
                        HttpResponse loginRes = httpClient.execute(post);

                        Log.d(TAG, "Login Response code:" + loginRes.getStatusLine().getStatusCode());

                        get = new HttpGet(ADMIN_URL);
                        response = httpClient.execute(get);

                        if(loginRes.getStatusLine().getStatusCode() == 200){
                            isLogined = true;
                            Log.d(TAG, "Login Success!");
                        }

                    } catch (Exception e) {
                        Log.d(TAG, "Login failed!");
                    }
                }

            }).start();
        }
    }
}
