package com.github.cascal.reverb.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.cascal.reverb.Config;
import com.github.cascal.reverb.R;
import com.github.cascal.reverb.adapter.CommentAdapter;
import com.github.cascal.reverb.data.TrackData;
import com.github.cascal.reverb.soundcloud.SoundCloudApi;
import com.github.cascal.reverb.soundcloud.SoundCloudService;
import com.github.cascal.reverb.soundcloud.model.Comment;
import com.github.cascal.reverb.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TrackDetailFragment extends Fragment {
    private static final String ARGS_TRACK_DATA = "com.github.cascal.reverb.detail_track_data";
    private ListView listView;
    private View progressBar;
    private View emptyFooterView;
    private CommentAdapter adapter;
    private TrackData trackData;
    private Callbacks callback;

    public interface Callbacks {
        void onPlayTrack(TrackData data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callbacks)activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() +
                    " must implement the TrackDetailFragment.Callbacks interface");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Bundle args = getArguments();
        if (args != null) {
            trackData = args.getParcelable(ARGS_TRACK_DATA);
        }
        adapter = new CommentAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_track_detail, parent, false);
        listView = (ListView) view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.progressBar);
        emptyFooterView = inflater.inflate(R.layout.footer_track_detail, listView, false);
        listView.addFooterView(emptyFooterView);
        showLoading();

        View headerView = inflater.inflate(R.layout.header_track_detail, listView, false);
        ImageView artworkImageView = (ImageView) headerView.findViewById(R.id.artworkImageView);
        TextView playCountTextView = (TextView) headerView.findViewById(R.id.playCountTextView);
        TextView favoriteCountTextView = (TextView) headerView.findViewById(R.id.favoriteCountTextView);
        TextView likeCountTextView = (TextView) headerView.findViewById(R.id.likeCountTextView);
        TextView commentCountTextView = (TextView) headerView.findViewById(R.id.commentCountTextView);
        TextView artistNameTextView = (TextView) headerView.findViewById(R.id.artistNameTextView);
        TextView trackNameTextView = (TextView) headerView.findViewById(R.id.trackNameTextView);

        listView.addHeaderView(headerView, null, false);

        listView.setAdapter(adapter);

        Picasso.with(getActivity())
                .load(trackData.getArtworkUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(artworkImageView);

        playCountTextView.setText(StringUtils.intToStringWithCommas(trackData.getPlaybackCount()));
        favoriteCountTextView.setText(StringUtils.intToStringWithCommas(trackData.getFavoritesCount()));
        likeCountTextView.setText(StringUtils.intToStringWithCommas(trackData.getLikesCount()));
        commentCountTextView.setText(StringUtils.intToStringWithCommas(trackData.getCommentsCount()));

        artistNameTextView.setText(trackData.getArtistName());
        trackNameTextView.setText(trackData.getTitle());

//        FloatingActionButton playButton = (FloatingActionButton) view.findViewById(R.id.playFAB);
//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback != null)
//                    callback.onPlayTrack(trackData);
//            }
//        });

        Log.d("fragmentdata", String.valueOf(trackData));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SoundCloudApi soundCloudApi = new SoundCloudApi(Config.CLIENT_ID);
        SoundCloudService soundCloud = soundCloudApi.getService();
        soundCloud.getTrackComments(trackData.getTrackId(), new Callback<List<Comment>>() {
            @Override
            public void success(List<Comment> comments, Response response) {
                adapter.swapList(comments);
                showContent();
            }

            @Override
            public void failure(RetrofitError error) {
                adapter.swapList(null);
                showContent();
                Toast.makeText(getActivity(),
                        "Unable to load comments: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showContent() {
        listView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        emptyFooterView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private void showLoading() {
        listView.setVisibility(View.INVISIBLE);
        emptyFooterView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public static Fragment newInstance(TrackData data) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_TRACK_DATA, data);
        Fragment fragment = new TrackDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
