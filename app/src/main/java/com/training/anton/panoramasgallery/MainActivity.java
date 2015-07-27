package com.training.anton.panoramasgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.training.anton.api.ApiInterface;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.api.model.Panoramas;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private List<PanoramaPhoto> listPhotos;
    private GridView gridView;
    private GridViewCustomAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewCustomAdapter(this);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);

        fetchPanoramas();
    }

    private void fetchPanoramas() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiInterface.BASE_URL).build();
        ApiInterface.ApiPanoramioService api = restAdapter.create(ApiInterface.ApiPanoramioService.class);

        api.getPanoramas("public", "small", 0, 100, ApiInterface.LONG - 0.005, ApiInterface.LAT - 0.005, ApiInterface.LONG + 0.005, ApiInterface.LAT + 0.005, new Callback<Panoramas>() {
            @Override
            public void success(Panoramas pano, Response response) {
                listPhotos = pano.getPhotos();
                gridAdapter.updateList(listPhotos);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fullPhotoURL = listPhotos.get(position).getPhotoURL().replace("small", "medium");
        FullPhotoFragment.create(fullPhotoURL).show(getFragmentManager(), "photofragment");
    }
}
