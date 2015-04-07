package com.ibm.rqm;

import android.content.SharedPreferences;

import com.ibm.rqm.xmlparser.XmlFetcher;
import com.ibm.rqm.xmlparser.XmlParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * Created by Jack on 2015/4/6 0006.
 *
 * Auto fetch and parse xml, then insert into database.
 */
public class AutoUpdater {
    private IBMApplication mApp;
    private SharedPreferences mPrefs;

    private String mCurProjectAlias;
    XmlFetcher mFetcher;
    XmlParser mParser;

    public AutoUpdater(IBMApplication app) {
        mApp = app;
        mPrefs = mApp.getPrefs();

        String host = mPrefs.getString("host", null);
        int port = mPrefs.getInt("port", 0);
        mCurProjectAlias = mPrefs.getString("currentProjectAlias", null);
        mFetcher = new XmlFetcher(mApp.getHttpClient(), host, port);
        mParser = new XmlParser();
    }

    public void startUpdate(){
        for(int i = 0; i < RQMConstant.SIZE; ++i){
            String xmlStr = "";
            if(i == RQMConstant.PROJECTS){
                xmlStr = mFetcher.fetchXmlByName("all", i);
            }else{
                xmlStr = mFetcher.fetchXmlByName(mCurProjectAlias, i);
            }

            InputStream is= new ByteArrayInputStream(xmlStr.getBytes());

            //parse xml
            //TODO: Need methods to insert ArrayList into Database;
            switch (i){
                case RQMConstant.PROJECTS:
                    //return the ArrayList
                    mParser.parseProjectXML(is); break;
                case RQMConstant.TEST_CASE:
                    mParser.parseTestcaseXML(is); break;
                case RQMConstant.TEST_PLAN:
                    mParser.parseTestplanXML(is); break;
                case RQMConstant.TEST_SUITE:
                    mParser.parseTestsuiteXML(is); break;

            }


        }
    }


}

