package com.training.anton.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PanoramaPhoto implements Parcelable {
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
    @SerializedName("owner_name")
    private String photoAuthor;
    @SerializedName("upload_date")
    private String uploadDate;

    protected PanoramaPhoto(Parcel in) {
        photoId = in.readInt();
        height = in.readInt();
        width = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        photoURL = in.readString();
        photoTitle = in.readString();
        photoAuthor = in.readString();
        uploadDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photoId);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(photoURL);
        dest.writeString(photoTitle);
        dest.writeString(photoAuthor);
        dest.writeString(uploadDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PanoramaPhoto> CREATOR = new Creator<PanoramaPhoto>() {
        @Override
        public PanoramaPhoto createFromParcel(Parcel in) {
            return new PanoramaPhoto(in);
        }

        @Override
        public PanoramaPhoto[] newArray(int size) {
            return new PanoramaPhoto[size];
        }
    };

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
                ", photoAuthor='" + photoAuthor + '\'' +
                ", uploadDate='" + uploadDate + '\'' +
                '}';
    }

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

    public String getPhotoAuthor() {
        return photoAuthor;
    }

    public String getUploadDate() {
        return uploadDate;
    }
}
