package com.daywalker.codechallenge.activities.item;

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

import com.daywalker.codechallenge.R;
import com.daywalker.codechallenge.helpers.Http;
import com.daywalker.codechallenge.helpers.NetworkConnection;
import com.daywalker.codechallenge.models.Track;
import com.daywalker.codechallenge.repositories.TrackRepository;
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
import java.util.concurrent.CompletableFuture;

public class ItemActivity extends AppCompatActivity {

    private int trackId;
    private Track track;

    private TrackRepository trackRepository;

    ActionBar actionBar;

    boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        trackRepository = new TrackRepository(this);

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
        if (item.getItemId() == 16908332) { // Specifically check if back button pressed
            finish();
        }
        return true;
    }

    // Get Track Details
    private void getTrackDetails() {
        ProgressDialog dialog = ProgressDialog.show(this, "",
                "Loading...", true);

        if (connected) {
            Http http = new Http();

            List<NameValuePair> params = new LinkedList<>();
            params.add(new BasicNameValuePair("id", String.valueOf(trackId)));

            http.request(Http.GET, URLEncodedUtils.format(params, "utf-8"));
            http.setListener(response -> {
                try {
                    JSONObject data = new JSONObject(response);

                    JSONArray tracksList = data.getJSONArray("results");
                    if (tracksList.length() > 0) {
                        JSONObject item = tracksList.getJSONObject(0);

                        int collectionId = item.has("collectionId") ? item.getInt("collectionId") : 0;
                        String collectionName = item.has("collectionName") ? item.getString("collectionName") : "";
                        String collectionCensoredName = item.has("collectionCensoredName") ? item.getString("collectionCensoredName") : "";

                        int collectionArtistId = item.has("collectionArtistId") ? item.getInt("collectionArtistId") : 0;

                        String collectionArtistViewUrl = item.has("collectionArtistViewUrl") ? item.getString("collectionArtistViewUrl") : "";
                        String collectionViewUrl = item.has("collectionViewUrl") ? item.getString("collectionViewUrl") : "";

                        float collectionHdPrice = item.has("collectionHdPrice") ?Float.parseFloat(item.getString("collectionHdPrice")) : 0;
                        float trackHdPrice = item.has("collectionHdPrice") ? Float.parseFloat(item.getString("trackHdPrice")) : 0;

                        float trackRentalPrice = item.has("trackRentalPrice") ? Float.parseFloat(item.getString("trackRentalPrice")) : 0;
                        float trackHdRentalPrice = item.has("trackHdRentalPrice") ? Float.parseFloat(item.getString("trackHdRentalPrice")) : 0;

                        int discCount = item.has("discCount") ? item.getInt("discCount") : 0;
                        int discNumber = item.has("discNumber") ? item.getInt("discNumber") : 0;
                        int trackCount = item.has("trackCount") ? item.getInt("trackCount") : 0;
                        int trackNumber = item.has("trackNumber") ? item.getInt("trackNumber") : 0;

                        String shortDescription = item.has("shortDescription") ? item.getString("shortDescription") : "";
                        boolean hasITunesExtra = item.has("hasITunesExtras") && item.getBoolean("hasITunesExtras");

                        track = new Track(item.getInt("trackId"))
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
        } else {
            CompletableFuture.runAsync(() -> {
                track = trackRepository.getById(trackId);
                dialog.dismiss();
                showDetails();
            });
        }
    }

    private void showDetails() {
        ImageView artwork = findViewById(R.id.artwork);

        if (connected) {
            Picasso.get()
                    .load(track.getArtworkUrl100())
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(artwork);
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
