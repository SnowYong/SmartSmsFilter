package com.snow.app.smartsmsfilter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016.03.08.
 */
public class SmartSmsFilterOpenHelper extends SQLiteOpenHelper {
    private static final String CREATE_SMSDB = "" +
            "create table SMSFilter (" +
            "id integer primary key autoincrement, " +
            "smsFrom text, " +
            "filterState blob, " +
            "deleteTimes integer" +
            "undeleteTimes integer)";


    public SmartSmsFilterOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SMSDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
            default:
                break;
        }
    }
}
