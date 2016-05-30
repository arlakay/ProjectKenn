package com.github.cascal.reverb.view;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.data.TrackData;
import com.github.cascal.reverb.event.PlaybackErrorEvent;
import com.github.cascal.reverb.event.PlaybackUpdateEvent;
import com.github.cascal.reverb.event.PlayerStateChangeEvent;
import com.github.cascal.reverb.event.PlayerStatusRequestEvent;
import com.github.cascal.reverb.service.PlayerService;
import com.github.cascal.reverb.util.StringUtils;
import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;

public class PlayerFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private static final String ARGS_TRACK_DATA = "com.github.cascal.reverb.player_track_data";
    private static final int REWIND_SEC = 5000;
    private static final int FFWD_SEC = 5000;
    private TrackData trackData;
    private SeekBar seekBar;
    private ProgressBar progressBar;
    private ImageButton playPauseButton;
    private TextView seekTimeTextView;
    private boolean isSeeking;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            trackData = args.getParcelable(ARGS_TRACK_DATA);
        }
        PlayerService.sendPlay(getActivity(), trackData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_player, parent, false);
        ImageView artworkImageView = (ImageView) view.findViewById(R.id.artworkImageView);
        TextView trackTitleTextView = (TextView) view.findViewById(R.id.trackTitleTextView);
        TextView artistNameTextView = (TextView) view.findViewById(R.id.artistNameTextView);
        TextView totalTimeTextView = (TextView) view.findViewById(R.id.totalTimeTextView);
        seekTimeTextView = (TextView) view.findViewById(R.id.seekTimeTextView);
        playPauseButton = (ImageButton) view.findViewById(R.id.playPauseImageButton);
        ImageButton stopButton = (ImageButton) view.findViewById(R.id.stopImageButton);
        ImageButton rewindButton = (ImageButton) view.findViewById(R.id.rewindImageButton);
        ImageButton fastForwardButton = (ImageButton) view.findViewById(R.id.fastForwardImageButton);
        seekBar = (SeekBar) view.findViewById(R.id.playerSeekBar);
        progressBar = (ProgressBar) view.findViewById(R.id.playerProgressBar);

        Picasso.with(getActivity())
                .load(trackData.getArtworkUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(artworkImageView);

        artistNameTextView.setText(trackData.getArtistName());
        trackTitleTextView.setText(trackData.getTitle());
        totalTimeTextView.setText(StringUtils.getTimestamp(trackData.getDuration()));

        playPauseButton.setTag(true);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isPlay = (Boolean) view.getTag();
                if (isPlay) {
                    PlayerService.sendPlay(getActivity(), trackData);
                } else {
                    PlayerService.sendPause(getActivity());
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerService.sendStop(getActivity());
            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = Math.max(0, seekBar.getProgress() - REWIND_SEC);
                PlayerService.sendSeek(getActivity(), progress);
            }
        });

        fastForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = Math.min(seekBar.getMax(), seekBar.getProgress() + FFWD_SEC);
                PlayerService.sendSeek(getActivity(), progress);
            }
        });

        seekBar.setEnabled(false);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new PlayerStatusRequestEvent(0));
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(PlaybackUpdateEvent event) {
        Log.d("TAG", "playback update");
        if (!isSeeking) {
            seekBar.setProgress(event.getCurrentPosition());
            seekTimeTextView.setText(StringUtils.getTimestamp(event.getCurrentPosition()));
        }
        seekBar.setEnabled(true);
        seekBar.setSecondaryProgress(event.getBufferedPosition());
        seekBar.setMax(event.getMaxPosition());
    }

    public void onEventMainThread(PlaybackErrorEvent event) {
        resetPlayerControls();
        Toast.makeText(getActivity(),
                "Media playback error has occurred: ERROR " + event.getErrorCode(), Toast.LENGTH_LONG).show();
    }

    public void onEventMainThread(PlayerStateChangeEvent event) {
        PlayerStateChangeEvent.State state = event.getState();
        switch (state) {
            case PREPARING:
                seekBar.setVisibility(View.INVISIBLE);
                seekBar.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                playPauseButton.setEnabled(false);
                break;

            case PLAYING:
                seekBar.setVisibility(View.VISIBLE);
                seekBar.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                playPauseButton.setEnabled(true);
                togglePlayPauseButton(true);
                break;

            case PAUSED:
                playPauseButton.setEnabled(true);
                togglePlayPauseButton(false);
                break;

            case STOPPING:
                resetPlayerControls();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            seekTimeTextView.setText(StringUtils.getTimestamp(progress));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeeking = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeeking = false;
        PlayerService.sendSeek(getActivity(), seekBar.getProgress());
    }

    private void resetPlayerControls() {
        seekBar.setVisibility(View.VISIBLE);
        seekBar.setProgress(0);
        seekBar.setSecondaryProgress(0);
        seekBar.setEnabled(false);
        seekTimeTextView.setText(StringUtils.getTimestamp(0));
        progressBar.setVisibility(View.INVISIBLE);
        togglePlayPauseButton(false);
    }

    private void togglePlayPauseButton(boolean shouldShowPause) {
        playPauseButton.setTag(shouldShowPause);
        togglePlayPauseButton();
    }

    private void togglePlayPauseButton() {
        boolean isPlay = (Boolean)playPauseButton.getTag();
        playPauseButton.setImageResource(isPlay ? R.drawable.ic_pause_black_48dp : R.drawable.ic_play_arrow_black_48dp);
        playPauseButton.setTag(!isPlay);
    }

    public static Fragment newInstance(TrackData data) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_TRACK_DATA, data);
        Fragment fragment = new PlayerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
