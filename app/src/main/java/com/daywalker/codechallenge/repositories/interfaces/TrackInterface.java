package com.daywalker.codechallenge.repositories.interfaces;

import com.daywalker.codechallenge.models.Track;

import java.util.ArrayList;

public interface TrackInterface {

    /**
     * Insert All Atracks
     * @param tracks ArrayList
     * @return boolean
     */
    boolean insertAll(ArrayList<Track> tracks);

    /**
     * Get Track
     * @param id int
     * @return Track
     */
    Track getById(int id);


    /**
     * Get Tracks
     * @return ArrayList
     */
    ArrayList<Track> getTracks();
}
