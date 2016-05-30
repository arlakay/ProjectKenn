package com.github.cascal.reverb.event;

public class PlayerStateChangeEvent {
    public enum State {
        PREPARING,
        PLAYING,
        PAUSED,
        STOPPING
    }

    private State state;

    public PlayerStateChangeEvent(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
