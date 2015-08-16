package com.training.anton.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Panoramas {
    private int count;
    private boolean has_more;
    @SerializedName("map_location")
    private MapPosition mapPosition;
    private List<PanoramaPhoto> photos;

    @Override
    public String toString() {
        return "Panoramas{" +
                "count=" + count +
                ", has_more=" + has_more +
                ", mapPosition=" + mapPosition +
                ", photos=" + photos +
                '}';
    }

    public List<PanoramaPhoto> getPhotos() {
        return photos;
    }
}
