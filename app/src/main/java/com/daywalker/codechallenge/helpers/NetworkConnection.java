package com.daywalker.codechallenge.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {


    // Current Activity Context
    private final Context context;

    /**
     * Network Constructor
     *
     * @param context Current Activity Context
     */
    public NetworkConnection(Context context) {
        this.context = context;
    }

    /**
     * Check if Device is connected to the internet
     *
     * @return if the device is connected it return true and false if not
     */
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }

        return false;
    }
}
