package com.github.cascal.reverb.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.data.TrackData;

public class PlayerActivity extends SingleFragmentActivity {
    public static final String EXTRA_TRACK_DATA = "com.github.cascal.reverb.player_activity_track_data";

    @Override
    protected Fragment createFragment(Bundle args) {
        TrackData trackData = args.getParcelable(EXTRA_TRACK_DATA);
        return PlayerFragment.newInstance(trackData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }
}
