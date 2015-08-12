package com.training.anton.panoramasgallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.training.anton.api.model.PanoramaPhoto;
import com.training.anton.api.model.Panoramas;
import com.training.anton.network.NetworkModule;

import java.util.List;

public class PanoramioActivity extends AppCompatActivity {
    private NetworkModule networkModule;
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

        mAdapter = new RecyclerAdapter(new RecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String fullPhotoURL = listPhotos.get(position).getPhotoURL().replace("medium", "medium");
                FullPhotoFragment.create(fullPhotoURL).show(getFragmentManager(), "photofragment");
            }
        });

        networkModule = ((PanoramioApplication) getApplicationContext()).getNetworkModule();
        networkModule.makeRequest(this);
    }

    public void responseSuccess(Panoramas panoramas) {
        listPhotos = panoramas.getPhotos();
        Log.d("Obtained list", listPhotos.toString());
        mAdapter.updateContent(listPhotos);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void showErrorMessage() {
        Snackbar.with(this).text(R.string.network_error_message).type(SnackbarType.MULTI_LINE).duration(Snackbar.SnackbarDuration.LENGTH_INDEFINITE).actionLabel(R.string.try_again).actionListener(new ActionClickListener() {
            @Override
            public void onActionClicked(Snackbar snackbar) {
                networkModule.makeRequest(PanoramioActivity.this);
            }
        }).show(this);
    }
}
