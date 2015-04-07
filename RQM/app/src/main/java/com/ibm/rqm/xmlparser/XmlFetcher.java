package com.ibm.rqm.xmlparser;

import android.util.Log;

import com.ibm.rqm.RQMConstant;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by Jack on 2015/4/6 0006.
 */
public class XmlFetcher {

    private static final String TAG = "XmlFethcer";

    private static final String URL_CONTEXT = "qm";
    private static final String RESOURCE_PATH = "/" + URL_CONTEXT + "/service/com.ibm.rqm.integration.service.IIntegrationService/resources/";
    private String mHost;
    private int mPort;

    private HttpClient mHttpClient;
    public XmlFetcher(HttpClient httpClient, String host, int port) {
        mHttpClient = httpClient;
        mHost = host;
        mPort = port;
    }

    /*
    * obtain the xml String identified by projectAlias and sourceName.
     * if projectAlias = all, fetch all corresponding resource
     * if failed return null;
    * */
    public String fetchXmlByName(String projectAlias, int resourceName){

        String urlStr = "";
        if(projectAlias.equals("all")){
            urlStr = mHost + ":" + mPort + RESOURCE_PATH + RQMConstant.toString(resourceName);
        }else{
            urlStr = mHost + ":" + mPort + RESOURCE_PATH + projectAlias + "/" + RQMConstant.toString(resourceName);
        }

        return downloadXML(urlStr);
    }


    //TODO: start a new thread.
    private String downloadXML(String urlStr){
        String xmlStr;
        try{
            HttpGet get = new HttpGet(urlStr);
            HttpResponse response = mHttpClient.execute(get);

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
                return xmlStr;
            }else{
                Log.d(TAG, "Download xml failed");
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.d(TAG, "Download xml failed: " + urlStr);
        }
        return null;
    }
}
