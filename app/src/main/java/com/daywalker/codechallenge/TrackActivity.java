package com.daywalker.codechallenge;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.daywalker.codechallenge.app.Http;
import com.daywalker.codechallenge.models.Track;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    private int trackId;
    private Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Bundle bundle = getIntent().getExtras();
        trackId = (bundle != null) ? bundle.getInt("id") : -1;

        getTrackDetails();
    }

    private void getTrackDetails() {
        Http http = new Http();

        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading...", true);

        List<NameValuePair> params = new LinkedList<>();
        params.add(new BasicNameValuePair("id", String.valueOf(trackId)));

        http.request(Http.GET, URLEncodedUtils.format(params, "utf-8"));
        http.setListener(response -> {
            try {
                JSONObject data = new JSONObject(response);

                JSONArray tracksList = data.getJSONArray("results");
                if (tracksList.length() > 0) {
                    JSONObject item = tracksList.getJSONObject(0);
                    track = new Track(item.getInt("trackId"))
                            .setTrackName(item.getString("trackName"))
                            .setKind(item.getString("kind"))
                            .setWrapperType(item.getString("wrapperType"))
                            .setCollectionId(item.getInt("collectionId"))
                            .setArtistName(item.getString("artistName"))
                            .setCollectionCensoredName(item.getString("collectionCensoredName"))
                            .setTrackCensoredName(item.getString("trackCensoredName"))
                            .setCollectionArtistId(item.getInt("collectionArtistId"))
                            .setCollectionArtistViewUrl(item.getString("collectionArtistViewUrl"))
                            .setCollectionViewUrl(item.getString("collectionViewUrl"))
                            .setTrackViewUrl(item.getString("trackViewUrl"))
                            .setPreviewUrl(item.getString("previewUrl"))
                            .setArtworkUrl30(item.getString("artworkUrl30"))
                            .setArtworkUrl60(item.getString("artworkUrl60"))
                            .setArtworkUrl100(item.getString("artworkUrl100"))
                            .setCollectionPrice(Float.parseFloat(item.getString("collectionPrice")))
                            .setTrackPrice(Float.parseFloat(item.getString("trackPrice")))
                            .setCollectionHdPrice(Float.parseFloat(item.getString("collectionHdPrice")))
                            .setTrackHdPrice(Float.parseFloat(item.getString("trackHdPrice")))
                            .setReleaseDate(item.getString("releaseDate"))
                            .setCollectionExplicitness(item.getString("collectionExplicitness"))
                            .setTrackExplicitness(item.getString("trackExplicitness"))
                            .setDiscCount(item.getInt("discCount"))
                            .setDiscNumber(item.getInt("discNumber"))
                            .setTrackCount(item.getInt("trackCount"))
                            .setTrackNumber(item.getInt("trackNumber"))
                            .setTrackTimeMillis(item.getInt("trackTimeMillis"))
                            .setCountry(item.getString("country"))
                            .setCurrency(item.getString("currency"))
                            .setPrimaryGenreName(item.getString("primaryGenreName"))
                            .setContentAdvisoryRating(item.getString("contentAdvisoryRating"))
                            .setLongDescription(item.getString("longDescription"))
                            .setHasITunesExtras(item.getBoolean("hasITunesExtras"));
                }
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        });

        http.execute(Http.API_URL + "/lookup?");
    }
}
