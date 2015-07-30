package com.training.anton.panoramasgallery;

import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
    private MainActivity mMainActivity;
    private RecyclerView mRecyclerView;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mMainActivity = getActivity();
        mRecyclerView = (RecyclerView) mMainActivity.findViewById(R.id.recycler_view);
    }

    public void testPreconditions(){
        assertNotNull("Activity is null", mMainActivity);
        assertNotNull("RecyclerView is null", mRecyclerView);
        ViewAsserts.assertOnScreen(mMainActivity.getWindow().getDecorView(), mRecyclerView);
        assertEquals("RecyclerView has children", 0, mRecyclerView.getChildCount());
    }

    public void testMainActivity_title() {
        assertEquals(getActivity().getString(R.string.app_name), mMainActivity.getTitle());
    }


}
