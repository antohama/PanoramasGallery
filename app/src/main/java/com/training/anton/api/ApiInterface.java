package com.training.anton.api;

import com.training.anton.api.model.Panoramas;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Anton on 7/24/2015.
 */
public class ApiInterface {
    public static final String BASE_URL = "http://www.panoramio.com";
    //Chicago
    public static double lat = 41.985844;
    public static double longt = -87.655063;

    /* Lviv
    public static double lat = 49.839311;
    public static double longt = 24.026070;
    */

    public interface ApiPanoramioService {
        @GET("/map/get_panoramas.php")
        public void getPanoramas(@Query("set") String set, @Query("size") String size, @Query("from") int from, @Query("to") int to, @Query("minx") double minX, @Query("miny") double minY, @Query("maxx") double maxX, @Query("maxy") double maxY, Callback<Panoramas> callback);
    }
}
