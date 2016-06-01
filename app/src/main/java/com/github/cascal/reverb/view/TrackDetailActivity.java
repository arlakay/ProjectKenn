package com.github.cascal.reverb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.github.cascal.reverb.R;
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
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }

    @Override
    public void onPlayTrack(TrackData data) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_TRACK_DATA, data);
        startActivity(intent);
    }
}
