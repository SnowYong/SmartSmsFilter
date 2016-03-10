package com.snow.app.smartsmsfilter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.snow.app.smartsmsfilter.model.SmartSmsFilter;

/**
 * Created by Administrator on 2016.03.08.
 */
public class SmartSmsFilterDB {
    private static final String DB_NAME = "SMS_DB";
    private static final int DB_VERSION = 1;

    private static SmartSmsFilterDB smsFilterDB;
    private SQLiteDatabase database;

    private SmartSmsFilterDB(Context context) {
        SmartSmsFilterOpenHelper openHelper = new SmartSmsFilterOpenHelper(context, DB_NAME, null, DB_VERSION);
        database = openHelper.getWritableDatabase();
    }

    public synchronized static SmartSmsFilterDB getInstance(Context context) {
        if (smsFilterDB == null) {
            smsFilterDB = new SmartSmsFilterDB(context);
        }
        return smsFilterDB;
    }

    public void saveSmartSmsFilter(SmartSmsFilter filter) {
        if (filter != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("smsFrom", filter.getSmsFrom());
            contentValues.put("filterState", filter.isFilterState());
            contentValues.put("deleteTimes", filter.getDeleteTimes());
            database.insert("SMSFilter", null, contentValues);
        }
    }

    public SmartSmsFilter loadSmartSmsFilter(String smsFrom) {
        Cursor cursor = null;
        String filterSelection = "where smsFrom = ?";
        String[] filterSelectionArgs = {smsFrom};
        SmartSmsFilter filter = null;
        try {
            cursor = database.query("SMSFilter", null, filterSelection,
                    filterSelectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                filter = new SmartSmsFilter();
                filter.setId(cursor.getInt(cursor.getColumnIndex("id")));
                filter.setSmsFrom(cursor.getString(cursor.getColumnIndex("smsFrom")));
                filter.setFilterState(Boolean.
                        valueOf(cursor.getBlob(cursor.getColumnIndex("filterState")).toString()));
                filter.setDeleteTimes(cursor.getInt(cursor.getColumnIndex("deleteTimes")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return filter;
    }
}
