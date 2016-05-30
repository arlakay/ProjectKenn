package com.github.cascal.reverb.soundcloud.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private Integer duration;
    private Integer releaseDay;
    private String permalinkUrl;
    private String genre;
    private String permalink;
    private String purchaseUrl;
    private Integer releaseMonth;
    private String description;
    private String uri;
    private String labelName;
    private String tagList;
    private Integer releaseYear;
    private Integer trackCount;
    private Integer userId;
    private String lastModified;
    private String license;
    private List<Track> tracks = new ArrayList<>();
    private String playlistType;
    private Integer id;
    private Boolean downloadable;
    private String sharing;
    private String createdAt;
    private Integer release;
    private String kind;
    private String title;
    private String purchaseTitle;
    private App createdWith;
    private String artworkUrl;
    private String ean;
    private Boolean streamable;
    private User user;
    private String embeddableBy;
    private Integer labelId;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(Integer releaseDay) {
        this.releaseDay = releaseDay;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public Integer getReleaseMonth() {
        return releaseMonth;
    }

    public void setReleaseMonth(Integer releaseMonth) {
        this.releaseMonth = releaseMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(String playlistType) {
        this.playlistType = playlistType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }

    public String getSharing() {
        return sharing;
    }

    public void setSharing(String sharing) {
        this.sharing = sharing;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRelease() {
        return release;
    }

    public void setRelease(Integer release) {
        this.release = release;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    public void setPurchaseTitle(String purchaseTitle) {
        this.purchaseTitle = purchaseTitle;
    }

    public App getCreatedWith() {
        return createdWith;
    }

    public void setCreatedWith(App createdWith) {
        this.createdWith = createdWith;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        this.artworkUrl = artworkUrl;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public Boolean getStreamable() {
        return streamable;
    }

    public void setStreamable(Boolean streamable) {
        this.streamable = streamable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmbeddableBy() {
        return embeddableBy;
    }

    public void setEmbeddableBy(String embeddableBy) {
        this.embeddableBy = embeddableBy;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}
