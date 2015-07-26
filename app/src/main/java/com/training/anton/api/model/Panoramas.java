package com.training.anton.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anton on 7/24/2015.
 */
public class Panoramas {

    private int count;

    private boolean has_more;

    @SerializedName("map_location")
    private MapPosition mapPosition;

    private List<PanoramaPhoto> photos;

    public List<PanoramaPhoto> getPhotos() {
        return photos;
    }
}
