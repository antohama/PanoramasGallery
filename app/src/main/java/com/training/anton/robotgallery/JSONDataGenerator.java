package com.training.anton.robotgallery;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

/**
 * Created by Anton on 7/20/2015.
 */
public class JSONDataGenerator {

    /**
     * Creates JSON file in internal storage which contains robots names list
     */
    public JSONDataGenerator(Context ctx, String fileName, int limit) {
        JSONObject obj = new JSONObject();
        try {
            JSONArray robotsArray = new JSONArray();
            for (int i = 0; i < limit; i++) {
                JSONObject roboObj = new JSONObject();
                roboObj.put("roboName", getRoboname());
                robotsArray.put(roboObj);
            }
            obj.put("robots", robotsArray);

            // Save the file in internal storage
            FileOutputStream stream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            stream.write(obj.toString().getBytes());
            stream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Random names generator
    private String getRoboname() {
        return "Robo_" + (new Random()).nextInt(Integer.MAX_VALUE);
    }
}
