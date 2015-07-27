package com.training.anton.api.model;

import com.google.gson.annotations.SerializedName;

public class PanoramaPhoto {
    private int height;
    private int width;
    private double latitude;
    private double longitude;

    @SerializedName("photo_file_url")
    private String photoURL;

    @SerializedName("photo_title")
    private String photoTitle;

    public String getPhotoURL() {
        return photoURL;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }
}
