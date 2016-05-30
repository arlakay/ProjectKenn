package com.github.cascal.reverb.event;

import com.github.cascal.reverb.data.TrackData;

public class PlaybackUpdateEvent {

    private TrackData trackData;
    private int currentPosition;
    private int bufferedPosition;
    private int maxPosition;

    public PlaybackUpdateEvent(TrackData trackData, int currentPosition, int bufferedPosition, int maxPosition) {
        this.trackData = trackData;
        this.currentPosition = currentPosition;
        this.bufferedPosition = bufferedPosition;
        this.maxPosition = maxPosition;
    }

    public TrackData getTrackData() {
        return trackData;
    }

    public void setTrackData(TrackData trackData) {
        this.trackData = trackData;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getBufferedPosition() {
        return bufferedPosition;
    }

    public void setBufferedPosition(int bufferedPosition) {
        this.bufferedPosition = bufferedPosition;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(int maxPosition) {
        this.maxPosition = maxPosition;
    }
}
