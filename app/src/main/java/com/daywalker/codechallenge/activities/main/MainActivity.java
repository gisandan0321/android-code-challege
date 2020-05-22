package com.daywalker.codechallenge.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daywalker.codechallenge.R;
import com.daywalker.codechallenge.activities.item.ItemActivity;
import com.daywalker.codechallenge.activities.main.adapters.TrackItemAdapter;
import com.daywalker.codechallenge.helpers.Http;
import com.daywalker.codechallenge.helpers.NetworkConnection;
import com.daywalker.codechallenge.models.Track;
import com.daywalker.codechallenge.repositories.TrackRepository;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {

    private static final int TRACK_CODE = 1010;
    private static final String REMOTE = "server";
    private static final String LOCAL = "local";

    private RecyclerView transactionItemsView;
    private TrackItemAdapter trackItemAdapter;
    private TextView emptyText;
    private ArrayList<Track> tracks = new ArrayList<>();
    private TrackRepository trackRepository;

    int resultCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyText = findViewById(R.id.empty);
        trackRepository = new TrackRepository(this);

        transactionItemsView = findViewById(R.id.tracks);

        trackItemAdapter = new TrackItemAdapter(this, tracks);
        transactionItemsView.setAdapter(trackItemAdapter);
        transactionItemsView.setLayoutManager(new LinearLayoutManager(this));

        // Check Network Connectivity
        boolean connected = new NetworkConnection(getApplicationContext()).isConnected();

        if (connected) {
            getItems(REMOTE);
        } else {
            Toast.makeText(this, R.string.ERR_NO_INTERNET_CONNECTION,
                    Toast.LENGTH_LONG).show();
            getItems(LOCAL);
        }

    }

    /**
     * Get Items From API
     * @param from String
     */
    private void getItems(String from) {
        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading...", true);

        if (from.equals(REMOTE)) {
            Http http = new Http();

            List<NameValuePair> params = new LinkedList<>();
            params.add(new BasicNameValuePair("term", "star"));
            params.add(new BasicNameValuePair("country", "au"));
            params.add(new BasicNameValuePair("media", "movie"));
            params.add(new BasicNameValuePair("all", ""));

            http.request(Http.GET, URLEncodedUtils.format(params, "utf-8"));
            http.setListener(response -> {
                try {
                    JSONObject data = new JSONObject(response);
                    resultCount = data.getInt("resultCount");
                    if (resultCount == 0) {
                        emptyText.setVisibility(View.VISIBLE);
                        transactionItemsView.setVisibility(View.GONE);
                    } else {
                        JSONArray tracksList = data.getJSONArray("results");
                        for (int i = 0; i < tracksList.length(); i++) {
                            JSONObject item = tracksList.getJSONObject(i);

                            int collectionId = item.has("collectionId") ? item.getInt("collectionId") : 0;
                            String collectionName = item.has("collectionName") ? item.getString("collectionName") : "";
                            String collectionCensoredName = item.has("collectionCensoredName") ? item.getString("collectionCensoredName") : "";

                            int collectionArtistId = item.has("collectionArtistId") ? item.getInt("collectionArtistId") : 0;

                            String collectionArtistViewUrl = item.has("collectionArtistViewUrl") ? item.getString("collectionArtistViewUrl") : "";
                            String collectionViewUrl = item.has("collectionViewUrl") ? item.getString("collectionViewUrl") : "";

                            float collectionHdPrice = item.has("collectionHdPrice") ? Float.parseFloat(item.getString("collectionHdPrice")) : 0;
                            float trackHdPrice = item.has("collectionHdPrice") ? Float.parseFloat(item.getString("trackHdPrice")) : 0;

                            float trackRentalPrice = item.has("trackRentalPrice") ? Float.parseFloat(item.getString("trackRentalPrice")) : 0;
                            float trackHdRentalPrice = item.has("trackHdRentalPrice") ? Float.parseFloat(item.getString("trackHdRentalPrice")) : 0;

                            int discCount = item.has("discCount") ? item.getInt("discCount") : 0;
                            int discNumber = item.has("discNumber") ? item.getInt("discNumber") : 0;
                            int trackCount = item.has("trackCount") ? item.getInt("trackCount") : 0;
                            int trackNumber = item.has("trackNumber") ? item.getInt("trackNumber") : 0;

                            String shortDescription = item.has("shortDescription") ? item.getString("shortDescription") : "";
                            boolean hasITunesExtra = item.has("hasITunesExtras") && item.getBoolean("hasITunesExtras");

                            Track track = new Track(item.getInt("trackId"))
                                    .setTrackName(item.getString("trackName"))
                                    .setKind(item.getString("kind"))
                                    .setWrapperType(item.getString("wrapperType"))
                                    .setCollectionId(collectionId)
                                    .setCollectionName(collectionName)
                                    .setArtistName(item.getString("artistName"))
                                    .setCollectionCensoredName(collectionCensoredName)
                                    .setTrackCensoredName(item.getString("trackCensoredName"))
                                    .setCollectionArtistId(collectionArtistId)
                                    .setCollectionArtistViewUrl(collectionArtistViewUrl)
                                    .setCollectionViewUrl(collectionViewUrl)
                                    .setTrackViewUrl(item.getString("trackViewUrl"))
                                    .setPreviewUrl(item.getString("previewUrl"))
                                    .setArtworkUrl30(item.getString("artworkUrl30"))
                                    .setArtworkUrl60(item.getString("artworkUrl60"))
                                    .setArtworkUrl100(item.getString("artworkUrl100"))
                                    .setCollectionPrice(Float.parseFloat(item.getString("collectionPrice")))
                                    .setTrackPrice(Float.parseFloat(item.getString("trackPrice")))
                                    .setCollectionHdPrice(collectionHdPrice)
                                    .setTrackHdPrice(trackHdPrice)
                                    .setTrackRentalPrice(trackRentalPrice)
                                    .setTrackHdRentalPrice(trackHdRentalPrice)
                                    .setReleaseDate(item.getString("releaseDate"))
                                    .setCollectionExplicitness(item.getString("collectionExplicitness"))
                                    .setTrackExplicitness(item.getString("trackExplicitness"))
                                    .setDiscCount(discCount)
                                    .setDiscNumber(discNumber)
                                    .setTrackCount(trackCount)
                                    .setTrackNumber(trackNumber)
                                    .setTrackTimeMillis(item.getInt("trackTimeMillis"))
                                    .setCountry(item.getString("country"))
                                    .setCurrency(item.getString("currency"))
                                    .setPrimaryGenreName(item.getString("primaryGenreName"))
                                    .setContentAdvisoryRating(item.getString("contentAdvisoryRating"))
                                    .setShortDescription(shortDescription)
                                    .setLongDescription(item.getString("longDescription"))
                                    .setHasITunesExtras(hasITunesExtra);

                            tracks.add(track);
                        }
                        trackItemAdapter.notifyDataSetChanged();
                        insertTracks();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            });

            http.execute(Http.API_URL + "/search?");
        } else {
            CompletableFuture.runAsync(() -> {
                tracks = trackRepository.getTracks();
                trackItemAdapter = new TrackItemAdapter(this, tracks);
                transactionItemsView.setAdapter(trackItemAdapter);
                transactionItemsView.setLayoutManager(new LinearLayoutManager(this));
                dialog.dismiss();
            });
        }
    }

    // Insert All Tracks
    private void insertTracks() {
        trackRepository.truncate();
        boolean inserted = trackRepository.insertAll(tracks);
        if (!inserted) {
            Toast.makeText(this, R.string.ERR_CANT_STORE_DATA,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Open Track ID
     * @param trackId int
     */
    public void openTrack(int trackId) {
        Intent intent = new Intent(this, ItemActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", trackId);
        intent.putExtras(bundle);

        startActivityForResult(intent, TRACK_CODE);
    }
}
