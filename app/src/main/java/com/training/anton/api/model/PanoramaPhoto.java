package com.training.anton.api.model;

import com.google.gson.annotations.SerializedName;

public class PanoramaPhoto {
    @Override
    public String toString() {
        return "PanoramaPhoto{" +
                "photoId=" + photoId +
                ", height=" + height +
                ", width=" + width +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", photoURL='" + photoURL + '\'' +
                ", photoTitle='" + photoTitle + '\'' +
                '}';
    }

    @SerializedName("photo_id")
    private int photoId;
    private int height;
    private int width;
    private double latitude;
    private double longitude;

    @SerializedName("photo_file_url")
    private String photoURL;

    @SerializedName("photo_title")
    private String photoTitle;

    public int getPhotoId() {
        return photoId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
