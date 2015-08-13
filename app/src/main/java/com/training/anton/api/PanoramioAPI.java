package com.training.anton.api;

import com.training.anton.api.model.Panoramas;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface PanoramioAPI {

    @GET("/map/get_panoramas.php")
    void getPanoramas(@Query("set") String set, @Query("size") String size, @Query("from") int from, @Query("to") int to, @Query("minx") double minX, @Query("miny") double minY, @Query("maxx") double maxX, @Query("maxy") double maxY, Callback<Panoramas> callback);
}
