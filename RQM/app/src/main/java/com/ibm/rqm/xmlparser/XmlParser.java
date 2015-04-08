package com.ibm.rqm.xmlparser;

import android.content.Context;
import android.util.Xml;

import com.ibm.rqm.xmlparser.Model.Project;
import com.ibm.rqm.xmlparser.Model.Testcase;
import com.ibm.rqm.xmlparser.Model.Testplan;
import com.ibm.rqm.xmlparser.Model.Testsuite;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jack on 2015/4/6 0006.
 */
public class XmlParser {

    XmlPullParser mParser;

    public XmlParser() {
        //init mParse
        mParser = Xml.newPullParser();
    }

    public void test(Context context) throws IOException{
        InputStream is = context.getResources().getAssets().open("projects.xml");

        parseProjectXML(is);
    }

    public ArrayList<Project> parseProjectXML(InputStream inputStream) {
        ArrayList<Project> projectList = null;
        Project project = null;
        try {
            mParser.setInput(inputStream, "UTF-8");
            int eventType = mParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = mParser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        projectList = new ArrayList<Project>();
                        break;
                    case XmlPullParser.START_TAG:
                        if(tagName.equals("project")){
                            project = new Project();
                        }else if(project != null && tagName.equals("identifier")){
                            project.setIdentifier(mParser.nextText());
                        }else if(project != null && tagName.equals("title")){
                            project.setTitle(mParser.nextText());
                        }else if(project != null && tagName.equals("description")){
                            project.setDescription(mParser.nextText());
                        }else if(project != null && tagName.equals("alias")){
                            project.setAlias(mParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(tagName.equals("project")){
                            projectList.add(project);
                            project = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = mParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return projectList;
    }

    public ArrayList<Testcase> parseTestcaseXML(InputStream inputStream){
        ArrayList<Testcase> testcaseList = null;
        Testcase testcase = null;
        try {
            mParser.setInput(inputStream, "UTF-8");
            int eventType = mParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = mParser.getName();
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        testcaseList = new ArrayList<Testcase>();
                        break;
                    case XmlPullParser.START_TAG:
                        if(tagName.equals("testcase")){
                            testcase = new Testcase();
                        }else if(testcase != null && tagName.equals("identifier")){
                            testcase.setIdentifier(mParser.nextText());
                        }else if(testcase != null && tagName.equals("title")){
                            testcase.setTitle(mParser.nextText());
                        }else if(testcase != null && tagName.equals("description")){
                            testcase.setDescription(mParser.nextText());
                        }else if(testcase != null && tagName.equals("projectArea")){
                            testcase.setProjectAreaAlias(mParser.getAttributeValue(0));
                            testcase.setProjectAreaHref(mParser.getAttributeValue(1));
                        }else if(testcase != null && tagName.equals("creationDate")){
                            testcase.setCreationDate(mParser.nextText());
                        }else if(testcase != null && tagName.equals("stylesheet")){
                            testcase.setStylesheet(mParser.nextText());
                        }else if(testcase != null && tagName.equals("webId")){
                            testcase.setWebId(mParser.nextText());
                        }else if(testcase != null && tagName.equals("updated")){
                            testcase.setUpdated(mParser.nextText());
                        }else if(testcase != null && tagName.equals("state")){
                            testcase.setState(mParser.nextText());
                        }else if(testcase != null && tagName.equals("creator")){
                            testcase.setCreatorResource(mParser.getAttributeValue(0));
                            testcase.setCreator(mParser.nextText());
                        }else if(testcase != null && tagName.equals("owner")){
                            testcase.setOwner(mParser.nextText());
                        }else if(testcase != null && tagName.equals("locked")){
                            testcase.setLocked(mParser.nextText());
                        }else if(testcase != null && tagName.equals("weight")){
                            testcase.setWeight(mParser.nextText());
                        }else if(testcase != null && tagName.equals("priority")){
                            testcase.setPriorityResource(mParser.getAttributeValue(0));
                        }else if(testcase != null && tagName.equals("suspect")){
                            testcase.setSuspect(mParser.nextText());
                        }else if(testcase != null && tagName.equals("risk")) {
                            testcase.setRisk(mParser.nextText());
                        }else if(testcase != null && tagName.equals("variables")){
                            testcase.setVariables(mParser.nextText());
                        }else if(testcase != null && tagName.equals("testscript")) {
                            testcase.setTestscriptHref(mParser.getAttributeValue(0));
                        }else if(testcase != null && tagName.equals("template")) {
                            testcase.setTemplateHref(mParser.getAttributeValue(0));
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if(tagName.equals("testcase")){
                            testcaseList.add(testcase);
                            testcase = null;
                        }
                        break;
                    default:
                        break;
                }
                eventType = mParser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return testcaseList;
    }

    public ArrayList<Testplan> parseTestplanXML(InputStream inputStream){
        return null;
    }

    public ArrayList<Testsuite> parseTestsuiteXML(InputStream inputStream){
        return null;
    }
}
