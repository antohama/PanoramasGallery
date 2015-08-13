package com.training.anton.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Panoramas {
    @Override
    public String toString() {
        return "Panoramas{" +
                "count=" + count +
                ", has_more=" + has_more +
                ", mapPosition=" + mapPosition +
                ", photos=" + photos +
                '}';
    }

    private int count;

    private boolean has_more;

    @SerializedName("map_location")
    private MapPosition mapPosition;

    private List<PanoramaPhoto> photos;

    public List<PanoramaPhoto> getPhotos() {
        return photos;
    }
}
