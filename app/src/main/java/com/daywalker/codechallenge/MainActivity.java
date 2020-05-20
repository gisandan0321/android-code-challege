package com.daywalker.codechallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daywalker.codechallenge.app.Http;
import com.daywalker.codechallenge.app.NetworkConnection;
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

public class MainActivity extends AppCompatActivity {

    private static final int TRACK_CODE = 1010;
    private static final String REMOTE = "server";
    private static final String LOCAL = "local";

    RecyclerView transactionItemsView;
    TrackItemAdapter trackItemAdapter;
    TextView emptyText;
    ArrayList<Track> tracks = new ArrayList<>();

    int resultCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyText = findViewById(R.id.empty);
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
        }

        getItems(LOCAL);
    }

    /**
     * Get Items From API
     * @aparam from String
     */
    private void getItems(String from) {
        if (from.equals(REMOTE)) {
            Http http = new Http();

            ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Loading...", true);

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
                            Track track = new Track(item.getInt("trackId"))
                                    .setTrackName(item.getString("trackName"))
                                    .setTrackPrice(Float.parseFloat(item.getString("trackPrice")))
                                    .setTrackHdPrice(Float.parseFloat(item.getString("trackHdPrice")))
                                    .setPrimaryGenreName(item.getString("primaryGenreName"))
                                    .setCurrency(item.getString("currency"))
                                    .setArtworkUrl60(item.getString("artworkUrl60"));

                            tracks.add(track);
                        }

//                        TrackRepository trackRepository = new TrackRepository(this);
//                        transactionItemModel.insertBatch(items);

                        trackItemAdapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            });

            http.execute(Http.API_URL + "/search?");
        } else {
            // Get Local Items Here
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
