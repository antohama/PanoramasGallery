package com.training.anton.panoramasgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.training.anton.api.ApiInterface;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.api.model.Panoramas;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private List<PanoramaPhoto> listPhotos;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new RecyclerAdapter(Collections.EMPTY_LIST, new ItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                String fullPhotoURL = listPhotos.get(position).getPhotoURL().replace("medium", "medium");
                FullPhotoFragment.create(fullPhotoURL).show(getFragmentManager(), "photofragment");
            }
        });

        fetchPanoramas();
    }

    private void fetchPanoramas() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ApiInterface.BASE_URL).build();
        ApiInterface.ApiPanoramioService api = restAdapter.create(ApiInterface.ApiPanoramioService.class);

        api.getPanoramas("full", "medium", 0, 100, ApiInterface.LONG - 0.005, ApiInterface.LAT - 0.005, ApiInterface.LONG + 0.005, ApiInterface.LAT + 0.005, new Callback<Panoramas>() {
            @Override
            public void success(Panoramas panoramas, Response response) {
                listPhotos = panoramas.getPhotos();
                mAdapter.updateContent(listPhotos);

                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Snackbar.with(MainActivity.this).text(R.string.network_error_message).type(SnackbarType.MULTI_LINE).duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE).actionLabel(R.string.try_again).actionListener(new ActionClickListener() {
                    @Override
                    public void onActionClicked(Snackbar snackbar) {
                        fetchPanoramas();
                    }
                }).show(MainActivity.this);
            }
        });
    }
}
