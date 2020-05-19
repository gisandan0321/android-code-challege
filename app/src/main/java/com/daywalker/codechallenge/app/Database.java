package com.daywalker.codechallenge.app;

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
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Insert Multiple Data
     *
     * @param  values table data
     */
    public void insertBatch(JSONArray values) {
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
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * Insert Data to Specific Table
     * @return true if successfully inserted and false if not
     */
    public long insert(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(this.table, null, values);
    }

    /**
     * Update Data
     *
     * @param values column names and values
     * @param column where column
     * @param id unique record identifier
     * @return true if successfully updated and false if not
     */
    public boolean update(ContentValues values, String column, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(this.table, values, column + " = " + id, null) == 1;
    }


    /**
     * Delete Table Record
     * @param column where column
     * @param id unique record identifier
     */
    public void delete(String column, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.table, column + " = " + id, null);
    }

    /**
     * Custom Query with Search Param
     *
     * @param query String
     * @param values String array
     * @return cursor Cursor
     */
    public Cursor rawQuery(String query, String[] values) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, values);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Fetch Records
     *
     * @param columns array of columns you want to fetch
     * @param keys String
     * @param values String array
     * @return return cursor
     */
    public Cursor fetch(String[] columns, String keys, String[] values) {
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