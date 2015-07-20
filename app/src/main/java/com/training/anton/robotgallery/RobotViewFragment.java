package com.training.anton.robotgallery;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Fragment which gets attached to main activity to show the robot image
 * after clicking on one in grid view.
 */
public class RobotViewFragment extends Fragment {

    public RobotViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_robot_view, container, false);

        byte[] robotImageInByteArray = getArguments().getByteArray("com.training.anton.robotgallery.ROBOT_IMAGE_IN_BYTE_ARRAY");
        Bitmap robotImage = BitmapFactory.decodeByteArray(robotImageInByteArray, 0, robotImageInByteArray.length);

        ImageView imageView = (ImageView) fragmentView.findViewById(R.id.robo_single_view);
        imageView.setImageBitmap(robotImage);
        return fragmentView;
    }
}
