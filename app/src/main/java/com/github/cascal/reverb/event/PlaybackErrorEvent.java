package com.github.cascal.reverb.event;

public class PlaybackErrorEvent {
    private int errorCode;

    public PlaybackErrorEvent(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
