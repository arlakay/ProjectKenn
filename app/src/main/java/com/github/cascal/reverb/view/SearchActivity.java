package com.github.cascal.reverb.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.data.TrackData;

public class SearchActivity extends SingleFragmentActivity implements SearchFragment.Callbacks {
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private int mCurrentSelectedPosition;

    @Override
    protected Fragment createFragment(Bundle args) {
        return new SearchFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_apply_idea:
                        //Intent intent = new Intent(SearchActivity.this, SubmitIdeStepOne.class);
                        //startActivity(intent);
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_article:
                        //Intent intent2 = new Intent(SearchActivity.this, ScrollableTabsActivity.class);
                        //startActivity(intent2);
                        mCurrentSelectedPosition = 1;
                        return true;
                    default:
                        return true;
                }
            }
        });

    }

    @Override
    public void onTrackSelected(TrackData trackData) {
        Intent intent = new Intent(this, TrackDetailActivity.class);
        intent.putExtra(TrackDetailActivity.EXTRA_TRACK_DATA, trackData);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
