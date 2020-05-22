package com.daywalker.codechallenge.activities.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daywalker.codechallenge.R;
import com.daywalker.codechallenge.activities.main.MainActivity;
import com.daywalker.codechallenge.helpers.NetworkConnection;
import com.daywalker.codechallenge.models.Track;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrackItemAdapter extends RecyclerView.Adapter<TrackItemAdapter.ViewHolder> {

    private final List<Track> tracks;
    private Context mContext;
    private boolean connected;

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

        holder.item.setOnClickListener(v -> {
            if (mContext instanceof MainActivity) {
                ((MainActivity) mContext).openTrack(track.getTrackId());
            }
        });

        // Set item views based on your views and data model
        ImageView artworkView = holder.preview;

        if (connected) {
            Picasso.get()
                    .load(track.getArtworkUrl60())
                    .placeholder(R.drawable.image_default)
                    .error(R.drawable.image_default)
                    .into(artworkView);

        } else {
            artworkView.setImageResource(R.drawable.image_default);
        }

        TextView nameView = holder.name;
        nameView.setText(track.getTrackName());

        TextView genreView = holder.genre;
        genreView.setText(track.getPrimaryGenreName());

        TextView priceView = holder.price;
        float priceValue = (track.getTrackHdPrice() > 0) ?
                track.getTrackHdPrice() : track.getTrackPrice();

        String price = String.valueOf(priceValue);
        priceView.setText(String.format("%s %s", price, track.getCurrency()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout item;
        final ImageView preview;
        final TextView name;
        final TextView genre;
        final TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            // Your holder should contain a member variable
            // for any view that will be set as you render a row
            item = itemView.findViewById(R.id.track_item_linear);
            preview = itemView.findViewById(R.id.artwork);
            name = itemView.findViewById(R.id.name);
            genre = itemView.findViewById(R.id.genre);
            price = itemView.findViewById(R.id.price);
        }
    }

    // Pass in the contact array into the constructor
    public TrackItemAdapter(Context context, List<Track> items) {
        this.tracks = items;
        mContext = context;
        connected = new NetworkConnection(mContext).isConnected();
    }
}
