package com.example.amber.smartapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amber on 2019/5/26.
 */
public class MyDatabaseOpenHlper extends SQLiteOpenHelper{
    private static final String db_name = "Info.db"; // 数据库名称
    private static final int version = 1; // 数据库版本


    //构造方法
    public MyDatabaseOpenHlper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //没有数据库打印日记
        Log.i("Log","没有数据库,创建数据库");
        String CREATE_INFO = "create table Info(id integer primary key autoincrement,account text,password text)";

        sqLiteDatabase.execSQL(CREATE_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("updateLog","数据库更新了！");

    }

}
