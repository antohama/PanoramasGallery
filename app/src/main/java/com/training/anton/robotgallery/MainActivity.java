package com.training.anton.robotgallery;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    Fragment fragment = null;
    String fileName = "robo.json";
    private final int DATALIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Genarate data if the app is just run,
        // DATALIMIT specifies the amount of robots,
        // generated data is stored in fileName
        if (savedInstanceState == null) {
            new JSONDataGenerator(this, fileName, DATALIMIT);
        }

        // Initializing grid view and populating it with data
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridViewCustomAdapter(this, getRobotNamesFromJSON()));

        // Showing the robot in fragment on clicking on it in grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Getting roboImage of clicked robot and saving it in Bundle for passing to fragment
                NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.imageViewInCell);
                BitmapDrawable roboImage = (BitmapDrawable) imageView.getDrawable();

                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                roboImage.getBitmap().compress(Bitmap.CompressFormat.PNG, 0, byteStream);
                Bundle extra = new Bundle();
                extra.putByteArray("com.training.anton.robotgallery.ROBOT_IMAGE_IN_BYTE_ARRAY", byteStream.toByteArray());

                // If fragment is already present, remove it, otherwise create and add it to view
                FragmentManager fragmentManager = getFragmentManager();
                if (fragment != null && fragment.isAdded()) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                } else {
                    fragment = new RobotViewFragment();
                    fragment.setArguments(extra);
                    fragmentManager.beginTransaction().add(R.id.main_view, fragment).commit();
                }
            }
        });
    }

    /**
     * Reads the robot names from JSON file and returns them as ArrayList
     */
    private ArrayList<String> getRobotNamesFromJSON() {

        // Presenting JSON file as byteArray
        try {
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            FileInputStream inputJSONContents = openFileInput(fileName);

            int i = inputJSONContents.read();
            while (i != -1) {
                byteArray.write(i);
                i = inputJSONContents.read();
            }
            inputJSONContents.close();

            // Parsing JSON objects from byteArray
            JSONObject jsonObj = new JSONObject(byteArray.toString());
            JSONArray robotsArray = jsonObj.getJSONArray("robots");
            ArrayList<String> robotNamesList = new ArrayList<String>();

            for (int j = 0; j < robotsArray.length(); j++) {
                robotNamesList.add(robotsArray.getJSONObject(j).getString("roboName"));
            }

            return robotNamesList;
        } catch (Exception e) {
            Log.e(e.getMessage(), "Error reading JSON file");
            return null;
        }
    }

    /* Menu is not implemented yet
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_robot_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
