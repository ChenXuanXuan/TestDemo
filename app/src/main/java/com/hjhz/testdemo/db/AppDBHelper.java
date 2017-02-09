package com.hjhz.testdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 陈宣宣 on 2016/1/11.
 */
public class AppDBHelper extends SQLiteOpenHelper {

    private static AppDBHelper db;
    public final static byte[] _writeLock = new byte[0];

    private AppDBHelper(Context context) {
        super(context, "myapp.db", null, 1);

    }

    /**
     * 得到数据库的实例
     *
     * @param context
     * @return
     */
    public static synchronized AppDBHelper getDbInstance(Context context) {
        if (db == null) {
            db = new AppDBHelper(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建已读文章表
        db.execSQL("create table if not exists readed_article(id integer PRIMARY KEY)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 创建亲情键详情
        try {
            db.execSQL("create table if not exists readed_article(id integer PRIMARY KEY)");
        } catch (Exception ex) {

        }
    }

}