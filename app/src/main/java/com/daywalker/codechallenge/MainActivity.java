package com.daywalker.codechallenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.daywalker.codechallenge.app.Http;
import com.daywalker.codechallenge.app.NetworkConnection;
import com.daywalker.codechallenge.models.Track;

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

    RecyclerView transactionItemsView;
    TrackItemAdapter trackItemAdapter;

    ArrayList<Track> tracks = new ArrayList<>();

    int resultCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transactionItemsView = findViewById(R.id.tracks);

        trackItemAdapter = new TrackItemAdapter(this, tracks);
        transactionItemsView.setAdapter(trackItemAdapter);
        transactionItemsView.setLayoutManager(new LinearLayoutManager(this));

        // Check Network Connectivity
        boolean connected = new NetworkConnection(getApplicationContext()).isConnected();

        if (connected) {
            getItems();
        } else {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
            adBuilder.setTitle("")
                    .setIcon(R.drawable.ic_signal_wifi_off_grey_900_24dp)
                    .setMessage(R.string.ERR_NO_INTERNET_CONNECTION)
                    .setPositiveButton(R.string.BUTTON_TRY_AGAIN, null)
                    .show();
        }

        getItems();
    }

    /**
     * Get Items From API
     */
    private void getItems() {
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

                JSONArray tracksList = data.getJSONArray("results");
                for (int i = 0; i < tracksList.length(); i++) {
                    JSONObject item = tracksList.getJSONObject(i);
                    Track track = new Track(item.getInt("trackId"))
                            .setTrackName(item.getString("trackName"))
                            .setTrackPrice(Float.parseFloat(item.getString("trackPrice")))
                            .setPrimaryGenreName(item.getString("primaryGenreName"))
                            .setArtworkUrl60(item.getString("artworkUrl60"));

                    tracks.add(track);
                }

                trackItemAdapter.notifyDataSetChanged();
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        });

        http.execute(Http.API_URL + "/search?");
    }

    /**
     * Open Track ID
     * @param trackId int
     */
    public void openTrack(int trackId) {
        Intent intent = new Intent(this, TrackActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("id", trackId);
        intent.putExtras(bundle);

        startActivityForResult(intent, TRACK_CODE);
    }
}
