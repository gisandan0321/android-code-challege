package com.daywalker.codechallenge;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.daywalker.codechallenge.app.Http;
import com.daywalker.codechallenge.app.NetworkConnection;
import com.daywalker.codechallenge.models.Track;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class ItemActivity extends AppCompatActivity {

    private int trackId;
    private Track track;

    ActionBar actionBar;

    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        trackId = (bundle != null) ? bundle.getInt("id") : -1;

        connected = new NetworkConnection(getApplicationContext()).isConnected();

        getTrackDetails();
    }

    /**
     * On Option Item Selected
     * @param item MenuItem
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    // Get Track Details
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

                    if (actionBar != null) {
                        actionBar.setTitle(track.getTrackName());
                    }

                    showDetails();
                }
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        });

        http.execute(Http.API_URL + "/lookup?");
    }

    private void showDetails() {
        ImageView artwork = findViewById(R.id.artwork);

        if (connected) {
            Picasso.get().load(track.getArtworkUrl100()).into(artwork);
        } else {
            artwork.setImageResource(R.drawable.image_default);
        }

        TextView name = findViewById(R.id.name);
        name.setText(track.getTrackName());

        TextView subTitle = findViewById(R.id.subTitle);
        subTitle.setText(track.getContentAdvisoryRating());

        ImageView hdTag = findViewById(R.id.hd);
        hdTag.setVisibility((track.getTrackHdPrice()) > 0 ? View.VISIBLE : View.GONE);

        TextView genre = findViewById(R.id.genre);
        genre.setText(track.getPrimaryGenreName());

        TextView duration = findViewById(R.id.duration);
        String hours   = String.valueOf((track.getTrackTimeMillis() / (1000*60*60)) % 24);
        String minutes = String.valueOf((track.getTrackTimeMillis() / (1000*60)) % 60);
        String totalDuration = hours + " Hours and " + minutes + " Minutes";
        duration.setText(totalDuration);

        TextView year = findViewById(R.id.year);
        year.setText(formatDate(track.getReleaseDate()));

        TextView description = findViewById(R.id.description);
        description.setText(track.getLongDescription());

        TextView priceView = findViewById(R.id.price);
        float priceValue = (track.getTrackHdPrice() > 0) ?
                track.getTrackHdPrice() : track.getTrackPrice();

        String price = String.valueOf(priceValue);
        priceView.setText(String.format("%s %s", price, track.getCurrency()));

        VideoView trailer = findViewById(R.id.trailer);

        ImageView trailerPlaceholder = findViewById(R.id.videoPlaceholder);
        MediaController mediaController = new MediaController(this);
        trailer.setMediaController(mediaController);

        if (connected) {
            trailer.setVisibility(View.VISIBLE);
            trailerPlaceholder.setVisibility(View.GONE);
            trailer.setVideoURI(Uri.parse(track.getPreviewUrl()));
            trailer.setOnPreparedListener(mp -> {
                trailer.start();
                mediaController.show(0);
            });
        }
    }

    /**
     * Format Date
     * @param date String
     * @return String
     */
    public static String formatDate(String date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = null;
        try {
            today = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert today != null;
        return dateFormat.format(today);
    }
}
