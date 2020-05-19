package com.daywalker.codechallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daywalker.codechallenge.models.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackItemAdapter extends RecyclerView.Adapter<TrackItemAdapter.ViewHolder> {

    private final List<Track> tracks;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_track, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Track track = tracks.get(position);

        // Set item views based on your views and data model
        ImageView artworkView = holder.preview;

        //Loading image using Picasso
        Picasso.get().load(track.getArtworkUrl60()).into(artworkView);

        TextView nameView = holder.name;
        nameView.setText(track.getTrackName());

        TextView genreView = holder.genre;
        genreView.setText(track.getPrimaryGenreName());

        TextView priceView = holder.price;
        priceView.setText(String.valueOf(track.getTrackPrice()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView preview;
        final TextView name;
        final TextView genre;
        final TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            LinearLayout item = itemView.findViewById(R.id.track_item_linear);
            preview = itemView.findViewById(R.id.artwork);
            name = itemView.findViewById(R.id.name);
            genre = itemView.findViewById(R.id.genre);
            price = itemView.findViewById(R.id.price);
        }
    }

    // Pass in the contact array into the constructor
    public TrackItemAdapter(List<Track> items) {
        this.tracks = items;
    }
}
