package com.training.anton.api;

import com.training.anton.api.model.Panoramas;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public class ApiInterface {
    public static final String BASE_URL = "http://www.panoramio.com";
    //Chicago
    public static double LAT = 41.985844;
    public static double LONG = -87.655063;

    public interface ApiPanoramioService {
        @GET("/map/get_panoramas.php")
        public void getPanoramas(@Query("set") String set, @Query("size") String size, @Query("from") int from, @Query("to") int to, @Query("minx") double minX, @Query("miny") double minY, @Query("maxx") double maxX, @Query("maxy") double maxY, Callback<Panoramas> callback);
    }
}
