package com.github.cascal.reverb.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.cascal.reverb.OnFragmentInteractionListener;
import com.github.cascal.reverb.R;
import com.github.cascal.reverb.data.TrackData;

public class SearchActivity extends SingleFragmentActivity implements SearchFragment.Callbacks, OnFragmentInteractionListener {
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private int mCurrentSelectedPosition;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    @Override
    protected Fragment createFragment(Bundle args) {
        return new SearchFragment();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.playFAB);
        fab.setVisibility(View.GONE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .commit();
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_playlist:
                        mDrawerLayout.closeDrawers();
                        if (savedInstanceState == null) {
                            SearchFragment searchFragment = new SearchFragment();
                            Bundle arguments = new Bundle();
                            searchFragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                                    .replace(R.id.fragment_container, searchFragment, "searchfragment")
//                                    .addToBackStack("searchfragment")
                                    .commit();
                        }
                        mCurrentSelectedPosition = 0;
                        return true;
                    case R.id.navigation_item_our_artist:
                        mDrawerLayout.closeDrawers();
                        if (savedInstanceState == null) {
                            OurArtistFragment ourArtistFragment = new OurArtistFragment();
                            Bundle arguments = new Bundle();
                            ourArtistFragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                                    .replace(R.id.fragment_container, ourArtistFragment, "ourartistfragment")
//                                    .addToBackStack("ourartistfragment")
                                    .commit();
                        }
                        mCurrentSelectedPosition = 1;
                        return true;
                    case R.id.navigation_item_srm_news:
                        mDrawerLayout.closeDrawers();
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                                    .replace(R.id.fragment_container, new SRMNewsFragment(), "srmnewsfragment")
//                                    .addToBackStack("srmnewsfragment")
                                    .commit();
                        }
                        mCurrentSelectedPosition = 2;
                        return true;
                    case R.id.navigation_item_event_schedule:
                        mDrawerLayout.closeDrawers();
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                                    .replace(R.id.fragment_container, new EventScheduleFragment(), "eventschedulefragment")
//                                    .addToBackStack("eventschedulefragment")
                                    .commit();
                        }
                        mCurrentSelectedPosition = 3;
                        return true;
                    case R.id.navigation_item_bookings:
                        mDrawerLayout.closeDrawers();
                        if (savedInstanceState == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.activityslidein, R.anim.activityslideinout, R.anim.activityslideoutpop, R.anim.activityslideout)
                                    .replace(R.id.fragment_container, new BookingFragment(), "bookingfragment")
//                                    .addToBackStack("bookingfragment")
                                    .commit();
                        }
                        mCurrentSelectedPosition = 4;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, 0);
        Menu menu = navigationView.getMenu();
        menu.getItem(mCurrentSelectedPosition).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
