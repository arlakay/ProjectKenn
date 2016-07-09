package com.github.cascal.reverb.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.cascal.reverb.Config;
import com.github.cascal.reverb.R;
import com.github.cascal.reverb.adapter.TrackAdapter;
import com.github.cascal.reverb.data.TrackData;
import com.github.cascal.reverb.soundcloud.SoundCloudApi;
import com.github.cascal.reverb.soundcloud.SoundCloudService;
import com.github.cascal.reverb.soundcloud.model.Track;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchFragment extends Fragment {

    private static final String STATE_SHOULD_SHOW_EMPTY = "com.github.cascal.reverb.search_show_empty";
    private static final String STATE_IS_LOADING = "com.github.cascal.reverb.search_is_loading";
    private SoundCloudService soundCloud;

    private ListView listView;
    private View emptyView;
    private View progressBar;
    private TrackAdapter trackAdapter;
    private Callbacks callback;
    private boolean shouldShowEmpty, isLoading;

    public interface Callbacks {
        void onTrackSelected(TrackData trackData);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callbacks)activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() +
                    " must implement the SearchFragment.Callbacks interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        soundCloud = new SoundCloudApi(Config.CLIENT_ID).getService();
        trackAdapter = new TrackAdapter(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, parent, false);

        listView = (ListView) view.findViewById(R.id.list);
        emptyView = view.findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        progressBar = view.findViewById(R.id.progressBar);

        listView.setAdapter(trackAdapter);

        if (savedInstanceState != null) {
            shouldShowEmpty = savedInstanceState.getBoolean(STATE_SHOULD_SHOW_EMPTY);
            isLoading = savedInstanceState.getBoolean(STATE_IS_LOADING);
        }

        if (trackAdapter.getCount() == 0 && shouldShowEmpty) {
            showEmpty();
        } else if (isLoading) {
            showLoading();
        } else {
            showContent();
        }

        methodGabutErsa();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback != null) {
                    Track track = trackAdapter.getItem(position);
                    callback.onTrackSelected(new TrackData(track));
                }
            }
        });
        return view;
    }

    //anjir gabut
    public void methodGabutErsa(){
        String query = "SRMbands";

        showLoading();
        shouldShowEmpty = false;
        isLoading = true;
        soundCloud.searchForTrack("SRMbands", new Callback<List<Track>>() {
            @Override
            public void success(final List<Track> tracks, Response response) {
                isLoading = false;
                Log.d("TAG", "success, found " + tracks.size() + " tracks");
                trackAdapter.swapList(tracks);
                if (tracks.size() == 0) {
                    shouldShowEmpty = true;
                    showEmpty();
                } else {
                    trackAdapter.notifyDataSetChanged();
                    showContent();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                isLoading = false;
                Toast.makeText(getActivity(), "Could not fetch tracks: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SHOULD_SHOW_EMPTY, shouldShowEmpty);
        outState.putBoolean(STATE_IS_LOADING, isLoading);
    }

    private void showContent() {
        listView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showEmpty() {
        listView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showLoading() {
        listView.setVisibility(View.GONE);
        emptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

}
