package com.daywalker.codechallenge.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Extends SQLiteOpenHelper
 *
 * Created by
 * User: dan
 * Date: 05/019/2020
 * Time: 11:53 PM
 */
public abstract class Database extends SQLiteOpenHelper {

    // Table Name
    private String table;

    // Database
    private static final String DATABASE_NAME = "code_challenge.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Database Class Constructor
     *
     * @param context Current Activity Context
     */
    protected Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Set Table Name
     * @param table String table name
     */
    protected void setTable(String table) {
        this.table = table;
    }

    /**
     * Insert Multiple Data
     *
     * @param  values table data
     * @return boolean
     */
    protected boolean insertBatch(JSONArray values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            for (int i = 0; i < values.length(); i++) {
                JSONObject data = values.getJSONObject(i);
                Iterator<String> iterator = data.keys();
                ContentValues tableData = new ContentValues();

                while (iterator.hasNext()) {
                    String key = iterator.next();
                    tableData.put(key, data.getString(key));
                }
                db.insert(this.table, null, tableData);
            }

            db.setTransactionSuccessful();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Fetch Records
     *
     * @param columns array of columns you want to fetch
     * @param keys String
     * @param values String array
     * @return return cursor
     */
    protected Cursor fetch(String[] columns, String keys, String[] values) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(this.table, columns, keys, values, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Truncate Table
     */
    public void truncate() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + this.table;
        db.execSQL(query);
    }
}