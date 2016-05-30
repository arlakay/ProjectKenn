package com.github.cascal.reverb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.github.cascal.reverb.data.TrackData;

public class TrackDetailActivity extends SingleFragmentActivity implements TrackDetailFragment.Callbacks {
    public static final String EXTRA_TRACK_DATA = "com.github.cascal.reverb.detail_activity_track_data";
    @Override
    protected Fragment createFragment(Bundle args) {
        TrackData data = args.getParcelable(EXTRA_TRACK_DATA);
        return TrackDetailFragment.newInstance(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPlayTrack(TrackData data) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_TRACK_DATA, data);
        startActivity(intent);
    }
}
