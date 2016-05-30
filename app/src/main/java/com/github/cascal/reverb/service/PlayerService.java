package com.github.cascal.reverb.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.github.cascal.reverb.data.TrackData;
import com.github.cascal.reverb.event.PlaybackErrorEvent;
import com.github.cascal.reverb.event.PlaybackUpdateEvent;
import com.github.cascal.reverb.event.PlayerStateChangeEvent;
import com.github.cascal.reverb.event.PlayerStatusRequestEvent;
import com.github.cascal.reverb.view.PlayerActivity;
import com.github.cascal.reverb.view.TrackDetailActivity;

import java.io.IOException;

import de.greenrobot.event.EventBus;

public class PlayerService extends Service implements MediaPlayer.OnPreparedListener,  MediaPlayer.OnErrorListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    private enum PlaybackCommand {
        PLAY,
        PAUSE,
        RESUME,
        STOP,
        SEEK
    }

    private static final String EXTRA_PLAYBACK_COMMAND = "extra_playback_cmd";
    private static final String EXTRA_TRACK_DATA = "track_data";
    private static final String EXTRA_SEEK_TO = "seek_to";
    private static final int ERROR_PREPARE_FAILED = 37;
    private static final int NOTIFICATION_ID = 1;
    private MediaPlayer mediaPlayer;
    private TrackData trackData;
    private PlayerStateChangeEvent.State state;
    private PlaybackUpdateTask updateTask;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private String currStream;
    private volatile int bufferedPercent;
    private volatile boolean isPreparing;

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
        EventBus.getDefault().register(this);
        notificationBuilder = new NotificationCompat.Builder(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int extra = intent.getIntExtra(EXTRA_PLAYBACK_COMMAND, 0);
        if (extra < 0 || extra >= PlaybackCommand.values().length) extra = 0;
        PlaybackCommand cmd = PlaybackCommand.values()[extra];
        switch (cmd) {
            case PLAY:
                trackData = intent.getParcelableExtra(EXTRA_TRACK_DATA);
                if (currStream == null || !currStream.equals(trackData.getStreamUrl())) {
                    currStream = trackData.getStreamUrl();
                    preparePlayback(currStream);
                } else {
                    startPlayback();
                }
                break;

            case PAUSE:
                pausePlayback();
                notificationBuilder.setSmallIcon(android.R.drawable.ic_media_pause)
                        .setContentTitle("Paused");
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                break;

            case RESUME:
                startPlayback();
                break;

            case STOP:
                stopSelf();
                break;

            case SEEK:
                seekTo(intent.getIntExtra(EXTRA_SEEK_TO, 0));
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Cannot bind to PlayerService");
    }

    @Override
    public void onDestroy() {
        stopPlayback();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        isPreparing = false;
        startPlayback();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Log.d("TAG", "onError");
        EventBus.getDefault().post(new PlaybackErrorEvent(what));
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        bufferedPercent = percent;
    }

    public void onEvent(PlayerStatusRequestEvent event) {
        broadcastStateChange();
    }

    private void preparePlayback(String streamUrl) {
        Log.d("TAG", "streaming: " + streamUrl);
        if (mediaPlayer == null || streamUrl == null) return;
        stopPlayback(false);
        mediaPlayer.reset();
        try {
            isPreparing = true;
            mediaPlayer.setDataSource(streamUrl);
            mediaPlayer.prepareAsync();
            setState(PlayerStateChangeEvent.State.PREPARING);
        } catch (IOException ioe) {
            isPreparing = false;
            mediaPlayer.reset();
            EventBus.getDefault().post(new PlaybackErrorEvent(ERROR_PREPARE_FAILED));
        }
    }

    private void startPlayback() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !isPreparing) {
            bufferedPercent = 0;
            mediaPlayer.start();
            if (updateTask != null)
                updateTask.cancel(true);
            updateTask = new PlaybackUpdateTask();
            updateTask.execute();
            showForegroundNotification();
            setState(PlayerStateChangeEvent.State.PLAYING);
        }
    }

    private void pausePlayback() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            setState(PlayerStateChangeEvent.State.PAUSED);
        }
    }

    private void stopPlayback() {
        stopPlayback(true);
    }

    private void stopPlayback(boolean shouldRelease) {
        hideForegroundNotification();
        bufferedPercent = 0;
        if (updateTask != null)
            updateTask.cancel(true);
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            if (shouldRelease) {
                mediaPlayer.release();
            }
        }
        setState(PlayerStateChangeEvent.State.STOPPING);
    }

    private void seekTo(int progress) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(progress);
        }
    }

    private void setState(PlayerStateChangeEvent.State state) {
        this.state = state;
        broadcastStateChange();
    }

    private void broadcastStateChange() {
        EventBus.getDefault().post(new PlayerStateChangeEvent(state));
    }

    private void showForegroundNotification() {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(PlayerActivity.EXTRA_TRACK_DATA, trackData);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(PlayerActivity.class);
        taskStackBuilder.addNextIntent(intent);
        taskStackBuilder.editIntentAt(1).putExtra(TrackDetailActivity.EXTRA_TRACK_DATA, trackData);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        notificationBuilder.setContentTitle(trackData.getTitle())
                .setContentText(trackData.getArtistName())
                .setSmallIcon(android.R.drawable.ic_media_play)  // PLAY
                .setContentIntent(pendingIntent)
                .setProgress(trackData.getDuration(), 0, false);
        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    private void hideForegroundNotification() {
        stopForeground(true);
    }

    public static void sendPlay(Context context, TrackData trackData) {
        sendStartCommand(context, PlaybackCommand.PLAY, trackData);
    }

    public static void sendPause(Context context) {
        sendStartCommand(context, PlaybackCommand.PAUSE, null);
    }

    public static void sendStop(Context context) {
        sendStartCommand(context, PlaybackCommand.STOP, null);
    }

    public static void sendSeek(Context context, int progress) {
        sendStartCommand(context, PlaybackCommand.SEEK, progress);
    }

    public static void sendStartCommand(Context context, PlaybackCommand cmd, Object extra) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.putExtra(EXTRA_PLAYBACK_COMMAND, cmd.ordinal());
        if (extra != null) {
            switch (cmd) {
                case PLAY:
                    intent.putExtra(EXTRA_TRACK_DATA, (TrackData) extra);
                    break;
                case SEEK:
                    intent.putExtra(EXTRA_SEEK_TO, (Integer)extra);
                    break;
            }
        }
        context.startService(intent);
    }

    private class PlaybackUpdateTask extends AsyncTask<Void, Void, Void> {
        @Override
        public Void doInBackground(Void... params) {
            while (!isCancelled()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    // Ignored
                }
                if (!isPreparing) {
                    int currPos = mediaPlayer.getCurrentPosition();
                    int maxPos = mediaPlayer.getDuration();
                    int buffPos = bufferedPercent * maxPos;
                    EventBus.getDefault().post(new PlaybackUpdateEvent(trackData, currPos, buffPos, maxPos));
                    notificationBuilder.setProgress(maxPos, currPos, false);
                    notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                }
            }
            return null;
        }
    }
}
