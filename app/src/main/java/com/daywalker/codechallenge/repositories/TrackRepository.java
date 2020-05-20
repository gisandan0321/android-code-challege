package com.daywalker.codechallenge.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.daywalker.codechallenge.helpers.Database;
import com.daywalker.codechallenge.models.Track;
import com.daywalker.codechallenge.repositories.interfaces.TrackInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackRepository extends Database implements TrackInterface {

    private static final String TABLE = "track";
    private static final String PRIMARY_KEY = "id";
    private static final JSONObject COLUMNS = new JSONObject();

    /**
     * Database Class Constructor
     *
     * @param context Current Activity Context
     */
    public TrackRepository(Context context) {
        super(context);
        this.setTable(TABLE);

        try {
            COLUMNS.put("trackId", "int(11)");
            COLUMNS.put("wrapperType", "varchar(255)");
            COLUMNS.put("kind", "varchar(255)");
            COLUMNS.put("collectionId", "int(11)");
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
            COLUMNS.put("trackRentalPrice", "float");
            COLUMNS.put("trackHdRentalPrice", "float");
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
            COLUMNS.put("shortDescription", "text");
            COLUMNS.put("longDescription", "text");
            COLUMNS.put("hasITunesExtras", "int(1)");
        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(db);
    }

    @Override
    public boolean insertAll(ArrayList<Track> tracks) {
        JSONArray values = new JSONArray();

        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            JSONObject item = new JSONObject();

            try {
                item.put("trackId", track.getTrackId());
                item.put("wrapperType", track.getWrapperType());
                item.put("kind", track.getKind());
                item.put("collectionId", track.getCollectionId());
                item.put("artistName", track.getArtistName());
                item.put("collectionName", track.getCollectionName());
                item.put("trackName", track.getTrackName());
                item.put("collectionCensoredName", track.getCollectionCensoredName());
                item.put("trackCensoredName", track.getTrackCensoredName());
                item.put("collectionArtistId", track.getCollectionArtistId());
                item.put("collectionArtistViewUrl", track.getCollectionArtistViewUrl());
                item.put("collectionViewUrl", track.getCollectionViewUrl());
                item.put("trackViewUrl", track.getTrackViewUrl());
                item.put("previewUrl", track.getPreviewUrl());
                item.put("artworkUrl30", track.getArtworkUrl30());
                item.put("artworkUrl60", track.getArtworkUrl60());
                item.put("artworkUrl100", track.getArtworkUrl100());
                item.put("collectionPrice", track.getCollectionPrice());
                item.put("trackPrice", track.getTrackPrice());
                item.put("trackRentalPrice", track.getTrackRentalPrice());
                item.put("trackHdRentalPrice", track.getTrackHdRentalPrice());
                item.put("collectionHdPrice", track.getCollectionHdPrice());
                item.put("trackHdPrice", track.getTrackHdPrice());
                item.put("releaseDate", track.getReleaseDate());
                item.put("collectionExplicitness", track.getCollectionExplicitness());
                item.put("trackExplicitness", track.getTrackExplicitness());
                item.put("discCount", track.getDiscCount());
                item.put("discNumber", track.getDiscNumber());
                item.put("trackCount", track.getTrackCount());
                item.put("trackNumber", track.getTrackNumber());
                item.put("trackTimeMillis", track.getTrackTimeMillis());
                item.put("country", track.getCountry());
                item.put("currency", track.getCurrency());
                item.put("primaryGenreName", track.getPrimaryGenreName());
                item.put("contentAdvisoryRating", track.getContentAdvisoryRating());
                item.put("shortDescription", track.getShortDescription());
                item.put("longDescription", track.getLongDescription());
                item.put("hasITunesExtras", track.hasItunes());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            values.put(item);
        }
        return this.insertBatch(values);
    }

    @Override
    public Track getById(int id) {
        Track track = null;
        Iterator<String> columnsIterator = COLUMNS.keys();
        String[] columns = new String[COLUMNS.length()];
        String[] values = {String.valueOf(id)};

        int index = 0;
        while (columnsIterator.hasNext()) {
            columns[index] = columnsIterator.next();
            index++;
        }

        Cursor cursor = this.fetch(columns, "trackId = ?", values);

        if (cursor.moveToFirst()) {
            int iTunesExtra = cursor.getInt(cursor.getColumnIndex("hasITunesExtras"));
            track = new Track(cursor.getInt(cursor.getColumnIndex("trackId")))
                    .setTrackName(cursor.getString(cursor.getColumnIndex("trackName")))
                    .setKind(cursor.getString(cursor.getColumnIndex("kind")))
                    .setWrapperType(cursor.getString(cursor.getColumnIndex("wrapperType")))
                    .setCollectionId(cursor.getInt(cursor.getColumnIndex("collectionId")))
                    .setCollectionName(cursor.getString(cursor.getColumnIndex("collectionName")))
                    .setArtistName(cursor.getString(cursor.getColumnIndex("artistName")))
                    .setCollectionCensoredName(cursor.getString(cursor.getColumnIndex("collectionCensoredName")))
                    .setTrackCensoredName(cursor.getString(cursor.getColumnIndex("trackCensoredName")))
                    .setCollectionArtistId(cursor.getInt(cursor.getColumnIndex("collectionArtistId")))
                    .setCollectionArtistViewUrl(cursor.getString(cursor.getColumnIndex("collectionArtistViewUrl")))
                    .setCollectionViewUrl(cursor.getString(cursor.getColumnIndex("collectionViewUrl")))
                    .setTrackViewUrl(cursor.getString(cursor.getColumnIndex("trackViewUrl")))
                    .setPreviewUrl(cursor.getString(cursor.getColumnIndex("previewUrl")))
                    .setArtworkUrl30(cursor.getString(cursor.getColumnIndex("artworkUrl30")))
                    .setArtworkUrl60(cursor.getString(cursor.getColumnIndex("artworkUrl60")))
                    .setArtworkUrl100(cursor.getString(cursor.getColumnIndex("artworkUrl100")))
                    .setCollectionPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("collectionPrice"))))
                    .setTrackPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackPrice"))))
                    .setCollectionHdPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("collectionHdPrice"))))
                    .setTrackHdPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackHdPrice"))))
                    .setTrackRentalPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackRentalPrice"))))
                    .setTrackHdRentalPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackHdRentalPrice"))))
                    .setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")))
                    .setCollectionExplicitness(cursor.getString(cursor.getColumnIndex("collectionExplicitness")))
                    .setTrackExplicitness(cursor.getString(cursor.getColumnIndex("trackExplicitness")))
                    .setDiscCount(cursor.getInt(cursor.getColumnIndex("discCount")))
                    .setDiscNumber(cursor.getInt(cursor.getColumnIndex("discNumber")))
                    .setTrackCount(cursor.getInt(cursor.getColumnIndex("trackCount")))
                    .setTrackNumber(cursor.getInt(cursor.getColumnIndex("trackNumber")))
                    .setTrackTimeMillis(cursor.getInt(cursor.getColumnIndex("trackTimeMillis")))
                    .setCountry(cursor.getString(cursor.getColumnIndex("country")))
                    .setCurrency(cursor.getString(cursor.getColumnIndex("currency")))
                    .setPrimaryGenreName(cursor.getString(cursor.getColumnIndex("primaryGenreName")))
                    .setContentAdvisoryRating(cursor.getString(cursor.getColumnIndex("contentAdvisoryRating")))
                    .setShortDescription(cursor.getString(cursor.getColumnIndex("shortDescription")))
                    .setLongDescription(cursor.getString(cursor.getColumnIndex("longDescription")))
                    .setHasITunesExtras(iTunesExtra == 1);
        }
        cursor.close();
        return track;
    }

    @Override
    public ArrayList<Track> getTracks() {
        ArrayList<Track> tracks = new ArrayList<>();
        Iterator<String> columnsIterator = COLUMNS.keys();
        String[] columns = new String[COLUMNS.length()];

        int index = 0;
        while (columnsIterator.hasNext()) {
            columns[index] = columnsIterator.next();
            index++;
        }

        Cursor cursor = this.fetch(columns, "", null);

        if (cursor.moveToFirst()) {
            do {
                int iTunesExtra = cursor.getInt(cursor.getColumnIndex("hasITunesExtras"));
                Track track = new Track(cursor.getInt(cursor.getColumnIndex("trackId")))
                        .setTrackName(cursor.getString(cursor.getColumnIndex("trackName")))
                        .setKind(cursor.getString(cursor.getColumnIndex("kind")))
                        .setWrapperType(cursor.getString(cursor.getColumnIndex("wrapperType")))
                        .setCollectionId(cursor.getInt(cursor.getColumnIndex("collectionId")))
                        .setCollectionName(cursor.getString(cursor.getColumnIndex("collectionName")))
                        .setArtistName(cursor.getString(cursor.getColumnIndex("artistName")))
                        .setCollectionCensoredName(cursor.getString(cursor.getColumnIndex("collectionCensoredName")))
                        .setTrackCensoredName(cursor.getString(cursor.getColumnIndex("trackCensoredName")))
                        .setCollectionArtistId(cursor.getInt(cursor.getColumnIndex("collectionArtistId")))
                        .setCollectionArtistViewUrl(cursor.getString(cursor.getColumnIndex("collectionArtistViewUrl")))
                        .setCollectionViewUrl(cursor.getString(cursor.getColumnIndex("collectionViewUrl")))
                        .setTrackViewUrl(cursor.getString(cursor.getColumnIndex("trackViewUrl")))
                        .setPreviewUrl(cursor.getString(cursor.getColumnIndex("previewUrl")))
                        .setArtworkUrl30(cursor.getString(cursor.getColumnIndex("artworkUrl30")))
                        .setArtworkUrl60(cursor.getString(cursor.getColumnIndex("artworkUrl60")))
                        .setArtworkUrl100(cursor.getString(cursor.getColumnIndex("artworkUrl100")))
                        .setCollectionPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("collectionPrice"))))
                        .setTrackPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackPrice"))))
                        .setCollectionHdPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("collectionHdPrice"))))
                        .setTrackHdPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackHdPrice"))))
                        .setTrackRentalPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackRentalPrice"))))
                        .setTrackHdRentalPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("trackHdRentalPrice"))))
                        .setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")))
                        .setCollectionExplicitness(cursor.getString(cursor.getColumnIndex("collectionExplicitness")))
                        .setTrackExplicitness(cursor.getString(cursor.getColumnIndex("trackExplicitness")))
                        .setDiscCount(cursor.getInt(cursor.getColumnIndex("discCount")))
                        .setDiscNumber(cursor.getInt(cursor.getColumnIndex("discNumber")))
                        .setTrackCount(cursor.getInt(cursor.getColumnIndex("trackCount")))
                        .setTrackNumber(cursor.getInt(cursor.getColumnIndex("trackNumber")))
                        .setTrackTimeMillis(cursor.getInt(cursor.getColumnIndex("trackTimeMillis")))
                        .setCountry(cursor.getString(cursor.getColumnIndex("country")))
                        .setCurrency(cursor.getString(cursor.getColumnIndex("currency")))
                        .setPrimaryGenreName(cursor.getString(cursor.getColumnIndex("primaryGenreName")))
                        .setContentAdvisoryRating(cursor.getString(cursor.getColumnIndex("contentAdvisoryRating")))
                        .setShortDescription(cursor.getString(cursor.getColumnIndex("shortDescription")))
                        .setLongDescription(cursor.getString(cursor.getColumnIndex("longDescription")))
                        .setHasITunesExtras(iTunesExtra == 1);

                tracks.add(track);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tracks;
    }
}
