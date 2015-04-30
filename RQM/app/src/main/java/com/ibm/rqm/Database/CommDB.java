package com.ibm.rqm.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pampas Eagle on 2015/4/8.
 */
public class CommDB {
    public static final String DATABASE_NAME = "myDatabase";
    public static final int DATABASE_VERSION = 1;

    //创建该数据库下Project表的语句
    private static final String CREATE_TABLE_Project =
            "CREATE TABLE IF NOT EXISTS " + ProjectDB.SQLITE_TABLE + " (" +
                    ProjectDB.KEY_ROWNUM + " integer PRIMARY KEY autoincrement," +
                    ProjectDB.KEY_ID + "," +
                    ProjectDB.KEY_TITLE + "," +
                    ProjectDB.KEY_DESCRIPTION + "," +
                    ProjectDB.KEY_ALIAS + "," +
                    " UNIQUE (" + ProjectDB.KEY_ID +"));";
    private static final String DELETE_TABLE_Project =
            "Drop TABLE IF EXISTS" + CREATE_TABLE_Project;

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public CommDB(Context ctx)
    {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        public void onDelete(SQLiteDatabase db)
        {
            db.execSQL(DELETE_TABLE_Project);//删除已存在的Project表
        }

        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_Project);//创建Project表
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion)
        {
            // Adding any table mods to this guy here
        }

    }

    public CommDB open() throws SQLException
    {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        this.DBHelper.close();
    }

}
