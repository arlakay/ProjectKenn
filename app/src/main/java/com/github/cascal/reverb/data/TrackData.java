package com.github.cascal.reverb.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.cascal.reverb.Config;
import com.github.cascal.reverb.soundcloud.model.Track;
import com.github.cascal.reverb.soundcloud.model.User;

public class TrackData implements Parcelable {

    private int trackId;
    private int duration;
    private String artworkUrl;
    private String artistName;
    private String title;
    private String streamUrl;
    private int[] counts = new int[4];

    public TrackData(Track track) {
        trackId = track.getId();
        duration = track.getDuration();
        String smallArtworkUrl = track.getArtworkUrl();
        artworkUrl = smallArtworkUrl != null ? smallArtworkUrl.replace("large", "t500x500") : null;
        User artist = track.getUser();
        artistName = (artist != null && artist.getUsername() != null) ? artist.getUsername() : "Unknown";
        title = track.getTitle();
        streamUrl = track.getStreamUrl();
        if (streamUrl != null)
            streamUrl = streamUrl.replace("https", "http") + "?client_id=" + Config.CLIENT_ID;
        counts[0] = track.getPlaybackCount();
        counts[1] = track.getFavoritingsCount();
        counts[2] = track.getLikesCount();
        counts[3] = track.getCommentCount();
    }

    public TrackData(Parcel in) {
        trackId = in.readInt();
        duration = in.readInt();
        artworkUrl = in.readString();
        artistName = in.readString();
        title = in.readString();
        streamUrl = in.readString();
        in.readIntArray(counts);
    }

    public static final Parcelable.Creator<TrackData> CREATOR = new Parcelable.Creator<TrackData>() {
        @Override
        public TrackData[] newArray(int size) {
            return new TrackData[size];
        }

        @Override
        public TrackData createFromParcel(Parcel parcel) {
            return new TrackData(parcel);
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(trackId);
        out.writeInt(duration);
        out.writeString(artworkUrl);
        out.writeString(artistName);
        out.writeString(title);
        out.writeString(streamUrl);
        out.writeIntArray(counts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int[] getCounts() {
        return counts;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

    public int getPlaybackCount() {
        return counts[0];
    }

    public int getFavoritesCount() {
        return counts[1];
    }

    public int getLikesCount() {
        return counts[2];
    }

    public int getCommentsCount() {
        return counts[3];
    }
}
