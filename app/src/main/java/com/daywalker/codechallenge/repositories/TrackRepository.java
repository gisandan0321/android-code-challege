package com.daywalker.codechallenge.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.daywalker.codechallenge.helpers.Database;
import com.daywalker.codechallenge.models.Track;
import com.daywalker.codechallenge.repositories.interfaces.TrackInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class TrackRepository extends Database implements TrackInterface {

    private static final String TABLE = "track";
    private static final String PRIMARY_KEY = "trackId";
    private static final JSONObject COLUMNS = new JSONObject();

    /**
     * Database Class Constructor
     *
     * @param context Current Activity Context
     */
    protected TrackRepository(Context context) {
        super(context);
        this.setTable(TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            COLUMNS.put("wrapperType", "varchar(255)");
            COLUMNS.put("kind", "varchar(255)");
            COLUMNS.put("collectionId", "int");
            COLUMNS.put("trackId", "int(10)");
            COLUMNS.put("artistName", "varchar(255)");
            COLUMNS.put("collectionName", "varchar(255)");
            COLUMNS.put("trackName", "varchar(255)");
            COLUMNS.put("collectionCensoredName", "varchar(255)");
            COLUMNS.put("trackCensoredName", "varchar(255)");
            COLUMNS.put("collectionArtistId", "int(11)");
            COLUMNS.put("collectionArtistViewUrl", "varchar(255)");
            COLUMNS.put("collectionViewUrl", "varchar(255)");
            COLUMNS.put("trackViewUrl", "varchar(255)");
            COLUMNS.put("previewUrl", "varchar(191)");
            COLUMNS.put("artworkUrl30", "varchar(255)");
            COLUMNS.put("artworkUrl60", "varchar(255)");
            COLUMNS.put("artworkUrl100", "varchar(255)");
            COLUMNS.put("collectionPrice", "float");
            COLUMNS.put("trackPrice", "float");
            COLUMNS.put("collectionHdPrice", "float");
            COLUMNS.put("trackHdPrice", "float");
            COLUMNS.put("releaseDate", "varchar(191)");
            COLUMNS.put("collectionExplicitness", "varchar(191)");
            COLUMNS.put("trackExplicitness", "varchar(191)");
            COLUMNS.put("discCount", "int(11)");
            COLUMNS.put("discNumber", "int(11)");
            COLUMNS.put("trackCount", "int(11)");
            COLUMNS.put("trackNumber", "int(11)");
            COLUMNS.put("trackTimeMillis", "int(11)");
            COLUMNS.put("country", "varchar(20)");
            COLUMNS.put("currency", "varchar(20)");
            COLUMNS.put("primaryGenreName", "varchar(255)");
            COLUMNS.put("contentAdvisoryRating", "varchar(255)");
            COLUMNS.put("longDescription", "text");
            COLUMNS.put("hasITunesExtras", "int(1)");

            StringBuilder createTableQuery = new StringBuilder(
                    "CREATE TABLE IF NOT EXISTS " + TABLE + "(" + PRIMARY_KEY
                            + " INTEGER PRIMARY KEY AUTOINCREMENT, ");

            Iterator<String> iterator = COLUMNS.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    createTableQuery.append(key).append(" ").append(COLUMNS.getString(key));

                    if (COLUMNS.getString(key).equals("DATETIME")) {
                        createTableQuery.append(" NOT NULL DEFAULT(datetime(CURRENT_TIMESTAMP, 'localtime'))");
                    } else {
                        createTableQuery.append(" DEFAULT NULL");
                    }

                    if (iterator.hasNext()) {
                        createTableQuery.append(", ");
                    } else {
                        createTableQuery.append(")");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            db.execSQL(createTableQuery.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(db);
    }

    @Override
    public void insert(Track track) {

    }
}
