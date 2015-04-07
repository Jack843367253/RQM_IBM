package com.ibm.rqm.xmlparser;

import com.ibm.rqm.xmlparser.Model.Project;
import com.ibm.rqm.xmlparser.Model.Testcase;
import com.ibm.rqm.xmlparser.Model.Testplan;
import com.ibm.rqm.xmlparser.Model.Testsuite;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jack on 2015/4/6 0006.
 */
public class XmlParser {


    XmlPullParserFactory mFactory;
    XmlPullParser mParser;

    public XmlParser() {
        try {
            //init mParser
            mFactory = XmlPullParserFactory.newInstance();
            mParser = mFactory.newPullParser();
        }catch (XmlPullParserException e){
            e.printStackTrace();
        }
    }


    public ArrayList<Project> parseProjectXML(InputStream inputStream){
        return null;
    }

    public ArrayList<Testcase> parseTestcaseXML(InputStream inputStream){
        return null;
    }

    public ArrayList<Testplan> parseTestplanXML(InputStream inputStream){
        return null;
    }

    public ArrayList<Testsuite> parseTestsuiteXML(InputStream inputStream){
        return null;
    }
}
