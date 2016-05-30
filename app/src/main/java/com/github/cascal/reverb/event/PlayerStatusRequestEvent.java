package com.github.cascal.reverb.event;

public class PlayerStatusRequestEvent {
    int flags;

    public PlayerStatusRequestEvent(int flags) {
        this.flags = flags;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}
