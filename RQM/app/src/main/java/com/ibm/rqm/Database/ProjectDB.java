package com.ibm.rqm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ibm.rqm.xmlparser.Model.Project;

import java.util.ArrayList;

/**
 * Created by Pampas Eagle on 2015/4/5.
 */
public class ProjectDB {
    public static final String KEY_ROWNUM = "num";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ALIAS = "summry";

    private static final String TAG = "ProjectDB";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    static final String SQLITE_TABLE = "ProjectTable";

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context,CommDB.DATABASE_NAME,null,CommDB.DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }

        public boolean deleteDatabase(Context context)
        {
            return context.deleteDatabase(SQLITE_TABLE);
        }
    }

    //构造函数
    public ProjectDB(Context ctx)
    {
        this.mCtx = ctx;

    }
    //打开操作
    public ProjectDB open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mCtx);
      //  mDbHelper.deleteDatabase(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    //关闭操作
    public void close()
    {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    //创建Project表的字段,返回新添记录的行号
    public long insertProject(ArrayList<Project> projectListView){
        long createResult = 0;
        for(int i=0;i<projectListView.size();i++){
            ContentValues initialValues = new ContentValues();
          //  initialValues.put(KEY_ROWNUM,projectListView.get(i).getRowNum());
            initialValues.put(KEY_ID,projectListView.get(i).getIdentifier());
            initialValues.put(KEY_TITLE,projectListView.get(i).getTitle());
            initialValues.put(KEY_DESCRIPTION,projectListView.get(i).getDescription());
            initialValues.put(KEY_ALIAS,projectListView.get(i).getAlias());
            try {
                mDb.insert(SQLITE_TABLE, null, initialValues);
            } catch (Exception e) {
                // TODO: handle exception
            }
            createResult++;
        }
        return createResult;
    }

    //根据名称删除表中的数据
    public boolean deleteTicketById(String id){
        int isDelete;
        String[] tId;
        tId = new String[]{id};
        isDelete = mDb.delete(SQLITE_TABLE,KEY_ID+"=?",tId);
        Log.e("deleteTicket", "isDelete:" + isDelete + "||" + "ticketID=" + id);
        return isDelete > 0;
    }

    public void deleteProject(String str)
    {
        mDb.execSQL(str);
    }

    //获取表中的所有字段
    public ArrayList<Project>fetchAll(){
        ArrayList<Project> projectList = new ArrayList<Project>();
        Cursor mCursor = null;
        mCursor = mDb.query(SQLITE_TABLE,new String[]{KEY_ROWNUM,KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_ALIAS},null,null,null,null,null);
        if (mCursor.moveToFirst()) {
            do {
                Project pr = new Project();
                pr.setIdentifier(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_ID)));
                pr.setTitle(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_TITLE)));
                pr.setDescription(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_DESCRIPTION)));
                pr.setAlias(mCursor.getString(mCursor
                        .getColumnIndexOrThrow(KEY_ALIAS)));
                projectList.add(pr);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        return projectList;
    }
}