package com.ibm.rqm;

/**
 * Created by Jack on 2015/4/6 0006.
 */
public class RQMConstant {
    public static final int PROJECTS = 0;
    public static final int TEST_CASE = 1;
    public static final int TEST_PLAN = 2;
    public static final int TEST_SUITE = 3;

    public static final int SIZE = 4;
    private static int type;


    public static String toString(int _type){
        RQMConstant.type = _type;
        String str = "";
        switch (type){
            case PROJECTS: str = "projects"; break;
            case TEST_CASE: str = "testcase"; break;
            case TEST_PLAN: str = "testplan"; break;
            case TEST_SUITE: str = "testsuite"; break;
            default:
                break;
        }
        return str;
    }

}

