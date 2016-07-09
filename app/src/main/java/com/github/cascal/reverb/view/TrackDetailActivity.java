package com.github.cascal.reverb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.data.TrackData;

public class TrackDetailActivity extends SingleFragmentActivity implements TrackDetailFragment.Callbacks {
    public static final String EXTRA_TRACK_DATA = "com.github.cascal.reverb.detail_track_data";
    private TrackDetailFragment.Callbacks callback;
    private TrackData data;

    @Override
    protected Fragment createFragment(Bundle args) {
        data = args.getParcelable(EXTRA_TRACK_DATA);
        return TrackDetailFragment.newInstance(data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("data", String.valueOf(data));

        FloatingActionButton playButton = (FloatingActionButton) findViewById(R.id.playFAB);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayTrack(data);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlayTrack(TrackData data) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_TRACK_DATA, data);
        startActivity(intent);
    }
}
