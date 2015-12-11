package com.example.administrator.tencent_mvp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//记得最后要关闭数据库，关闭游标

/**
 * Created by Administrator on 2015/10/16.
 */
//用于用户资料的数据库
public class UserDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "DEVICES.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "devices_table";
    public final static String DEVICES_ID = "devices_id";
    public final static String DEVICES_MAC = "devices_mac";
    public final static String DEVICES_IP = "devices_ip";
    public final static String DEVICES_PORT = "devices_port";
    public final static String DEVICES_NAME = "devices_name";

    public UserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + DEVICES_ID
                + " INTEGER primary key autoincrement, " + DEVICES_MAC + " text, "+ DEVICES_IP +
                " text, "+ DEVICES_PORT + " text, "+ DEVICES_NAME +" text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select_cursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //增加操作
    public long insert(String mac,String ip,String port,String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
/* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(DEVICES_MAC, mac);
        cv.put(DEVICES_IP, ip);
        cv.put(DEVICES_PORT, port);
        cv.put(DEVICES_NAME, name);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }
    //删除操作
    public void delete(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = DEVICES_NAME + " = ?";
        String[] whereValue ={ name };
        db.delete(TABLE_NAME, where, whereValue);
    }
    //修改操作
    public void update(int id, String mac,String ip,String port,String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = DEVICES_ID + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(DEVICES_MAC, mac);
        cv.put(DEVICES_IP, ip);
        cv.put(DEVICES_PORT, port);
        cv.put(DEVICES_NAME, name);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
