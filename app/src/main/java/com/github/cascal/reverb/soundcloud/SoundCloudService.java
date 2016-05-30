package com.github.cascal.reverb.soundcloud;

import com.github.cascal.reverb.soundcloud.model.App;
import com.github.cascal.reverb.soundcloud.model.Comment;
import com.github.cascal.reverb.soundcloud.model.Group;
import com.github.cascal.reverb.soundcloud.model.Playlist;
import com.github.cascal.reverb.soundcloud.model.Track;
import com.github.cascal.reverb.soundcloud.model.User;
import com.github.cascal.reverb.soundcloud.model.WebProfile;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface SoundCloudService {

    /**********
     *  Users
     **********/

    /**
     * Search for a list of users that match a given query
     *
     * @param query The query to search against
     * @return The profile information of the user
     */
    @GET("/users")
    List<User> searchForUser(@Query("q") String query);

    /**
     * Asynchronously search for a list of users that match a given query
     *
     * @param query The query to search against
     * @param callback Callback method
     */
    @GET("/users")
    void searchForUser(@Query("q") String query, Callback<List<User>> callback);

    /**
     * Get a user's profile information
     *
     * @param userId The user's Id
     * @return The profile information of the user
     */
    @GET("/users/{id}")
    User getUser(@Path("id") int userId);

    /**
     * Asynchronously get a user's profile information
     *
     * @param userId   The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}")
    void getUser(@Path("id") int userId, Callback<User> callback);

    /**
     * Get a list of tracks belonging to a user
     *
     * @param userId The user's Id
     * @return A list of tracks belonging to the user
     */
    @GET("/users/{id}/tracks")
    List<Track> getUsersTracks(@Path("id") int userId);

    /**
     * Asynchronously get a list of tracks belonging to a user
     *
     * @param userId   The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/tracks")
    void getUsersTracks(@Path("id") int userId, Callback<List<Track>> callback);

    /**
     * Get a list of playlists (sets) created by a user
     *
     * @param userId The user's Id
     * @return A list of playlists (sets) created by the user
     */
    @GET("/users/{id}/playlists")
    List<Playlist> getUsersPlaylists(@Path("id") int userId);

    /**
     * Asynchronously get a list of playlists (sets) created by a user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/playlists")
    void getUsersPlaylists(@Path("id") int userId, Callback<List<Playlist>> callback);

    /**
     * Get a list of users who are followed by the user
     *
     * @param userId The user's Id
     * @return A list of users who are followed by the user
     */
    @GET("/users/{id}/followings")
    List<User> getFollowedUsers(@Path("id") int userId);

    /**
     * Asynchronously get a list of users who are followed by the user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/followings")
    void getFollowedUsers(@Path("id") int userId, Callback<List<User>> callback);

    /**
     * Get a user who is followed by the user
     *
     * @param userId The user's Id
     * @param followedUserId The followed user's Id
     * @return The user followed by the user
     */
    @GET("/users/{id}/followings/{followedUserId}")
    User getFollowedUser(@Path("id") int userId, @Path("followedUserId") int followedUserId);

    /**
     * Asynchronously get a user who is followed by the user
     *
     * @param userId The user's Id
     * @param followedUserId The followed user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/followings/{followedUserId}")
    void getFollowedUser(@Path("id") int userId, @Path("followedUserId") int followedUserId, Callback<User> callback);

    /**
     * Get a list of users who are following the user
     *
     * @param userId The user's Id
     * @return A list of users following the user
     */
    @GET("/users/{id}/followers")
    List<User> getFollowers(@Path("id") int userId);

    /**
     * Asynchronously get a list of users who are following the user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/followers")
    void getFollowers(@Path("id") int userId, Callback<List<User>> callback);

    /**
     * Get a user who is following the user
     *
     * @param userId The user's Id
     * @param followerId The following user's Id
     * @return The user who is following the user
     */
    @GET(" /users/{id}/followers/{followerId}")
    User getFollower(@Path("id") int userId, @Path("followerId") int followerId);

    /**
     * Asynchronously get a user who is following the user
     *
     * @param userId The user's Id
     * @param followerId The following user's Id
     * @param callback Callback method
     */
    @GET(" /users/{id}/followers/{followerId}")
    void getFollower(@Path("id") int userId, @Path("followerId") int followerId, Callback<User> callback);

    /**
     * Get a list of comments from a user
     *
     * @param userId The user's Id
     * @return A list of comments from the user
     */
    @GET("/users/{id}/comments")
    List<Comment> getUsersComments(@Path("id") int userId);

    /**
     * Asynchronously get a list of comments from a user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/comments")
    void getUsersComments(@Path("id") int userId, Callback<List<Comment>> callback);

    /**
     * Get a list of tracks favorited by a user
     *
     * @param userId The user's Id
     * @return A list of tracks favorited by the user
     */
    @GET("/users/{id}/favorites")
    List<Track> getUsersFavoriteTracks(@Path("id") int userId);

    /**
     * Asynchronously get a list of tracks favorited by a user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/favorites")
    void getUsersFavoriteTracks(@Path("id") int userId, Callback<List<Track>> callback);

    /**
     * Get a track favorited by a user
     *
     * @param userId The user's Id
     * @param trackId The track Id
     * @return The track favorited by the user
     */
    @GET("/users/{id}/favorites/{trackId}")
    Track getUsersFavoritedTrack(@Path("id") int userId, @Path("trackId") int trackId);

    /**
     * Asynchronously get a track favorited by a user
     *
     * @param userId The user's Id
     * @param trackId The track Id
     * @param callback Callback method
     */
    @GET("/users/{id}/favorites/{trackId}")
    void getUsersFavoritedTrack(@Path("id") int userId, @Path("trackId") int trackId, Callback<Track> callback);

    /**
     * Get a list of groups joined by a user
     *
     * @param userId The user's Id
     * @return A list of groups joined by the user
     */
    @GET("/users/{id}/groups")
    List<Group> getUsersGroups(@Path("id") int userId);

    /**
     * Asynchronously get a list of groups joined by a user
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/groups")
    void getUsersGroups(@Path("id") int userId, Callback<List<Group>> callback);

    /**
     * Get a list of a user's web profiles
     *
     * @param userId The user's Id
     * @return A list of the user's web profiles
     */
    @GET("/users/{id}/web-profiles")
    List<WebProfile> getUsersWebProfiles(@Path("id") int userId);

    /**
     * Asynchronously get a list of a user's web profiles
     *
     * @param userId The user's Id
     * @param callback Callback method
     */
    @GET("/users/{id}/web-profiles")
    void getUsersWebProfiles(@Path("id") int userId, Callback<List<WebProfile>> callback);


    /***********
     *  Tracks
     ***********/

    /**
     * Search for a track that matches the given query
     *
     * @param query The query to search against
     * @return A list of tracks that match the query
     */
    @GET("/tracks")
    List<Track> searchForTrack(@Query("q") String query);

    /**
     * Asynchronously search for a track that matches the given query
     *
     * @param query The query to search against
     * @param callback Callback method
     */
    @GET("/tracks")
    void searchForTrack(@Query("q") String query, Callback<List<Track>> callback);

    /**
     * Search for a track using custom query options
     *
     * @param options Key-value pairs representing the query parameters to use
     * @return A list of tracks that match the given parameters
     */
    @GET("/tracks")
    List<Track> searchForTrack(@QueryMap Map<String, String> options);

    /**
     * Asynchronously search for a track using custom query options
     *
     * @param options Key-value pairs representing the query parameters to use
     * @param callback Callback method
     */
    @GET("/tracks")
    void searchForTrack(@QueryMap Map<String, String> options, Callback<List<Track>> callback);

    /**
     * Get a track by its Id
     *
     * @param trackId The track's Id
     * @return The track specified by the Id
     */
    @GET("/tracks/{id}")
    Track getTrack(@Path("id") int trackId);

    /**
     * Asynchronously get a track by its Id
     *
     * @param trackId The track's Id
     * @param callback Callback method
     */
    @GET("/tracks/{id}")
    void getTrack(@Path("id") int trackId, Callback<Track> callback);

    /**
     * Get a list of comments for a track
     *
     * @param trackId The track's Id
     * @return A list of comments for the track
     */
    @GET("/tracks/{id}/comments")
    List<Comment> getTrackComments(@Path("id") int trackId);

    /**
     * Asynchronously get a list of comments for a track
     *
     * @param trackId The track's Id
     * @param callback Callback method
     */
    @GET("/tracks/{id}/comments")
    void getTrackComments(@Path("id") int trackId, Callback<List<Comment>> callback);

    /**
     * Get a specific comment for a track
     *
     * @param trackId The track's Id
     * @param commentId The comment's Id
     * @return The comment
     */
    @GET("/tracks/{id}/comments/{commentId}")
    Comment getTrackComment(@Path("id") int trackId, @Path("commentId") int commentId);

    /**
     * Asynchronously get a specific comment for a track
     *
     * @param trackId The track's Id
     * @param commentId The comment's Id
     * @param callback Callback method
     */
    @GET("/tracks/{id}/comments/{commentId}")
    void getTrackComment(@Path("id") int trackId, @Path("commentId") int commentId, Callback<Comment> callback);

    /**
     * Get a list of users who have favorited a track
     *
     * @param trackId The track's Id
     * @return A list of users who have favorited the track
     */
    @GET("/tracks/{id}/favoriters")
    List<User> getTrackFavoriters(@Path("id") int trackId);

    /**
     * Asynchronously get a list of users who have favorited a track
     *
     * @param trackId The track's Id
     * @param callback Callback method
     */
    @GET("/tracks/{id}/favoriters")
    void getTrackFavoriters(@Path("id") int trackId, Callback<List<User>> callback);


    /*************
     * Playlists
     *************/

    /**
     * Get a playlist by its Id
     *
     * @param playlistId The playlist Id
     * @return The playlist associated with the given Id
     */
    @GET("/playlists/{id}")
    Playlist getPlaylist(@Path("id") int playlistId);

    /**
     * Asynchronously get a playlist by its Id
     *
     * @param playlistId The playlist Id
     * @param callback Callback method
     */
    @GET("/playlists/{id}")
    void getPlaylist(@Path("id") int playlistId, Callback<Playlist> callback);


    /***********
     * Groups
     ***********/

    /**
     * Get a group by its Id
     *
     * @param groupId The group's Id
     * @return The group associated with the given Id
     */
    @GET("/groups/{id}")
    Group getGroup(@Path("id") int groupId);

    /**
     * Asynchronously get a group by its Id
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}")
    void getGroup(@Path("id") int groupId, Callback<Group> callback);

    /**
     * Get a list of users who moderate a group
     *
     * @param groupId The group's Id
     * @return The list of moderators associated with the group
     */
    @GET("/groups/{id}/moderators")
    List<User> getGroupModerators(@Path("id") int groupId);

    /**
     * Asynchronously get a list of users who moderate a group
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}/moderators")
    void getGroupModerators(@Path("id") int groupId, Callback<List<User>> callback);

    /**
     * Get a list of users belonging to a group
     *
     * @param groupId The group's Id
     * @return A list of members belonging to the group
     */
    @GET("/groups/{id}/members")
    List<User> getGroupMembers(@Path("id") int groupId);

    /**
     * Asynchronously get a list of users belonging to a group
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}/members")
    void getGroupMembers(@Path("id") int groupId, Callback<List<User>> callback);

    /**
     * Get a list of users who contributed a track to a group
     *
     * @param groupId The group's Id
     * @return A list of users who contributed a track to the group
     */
    @GET("/groups/{id}/contributors")
    List<User> getGroupContributors(@Path("id") int groupId);

    /**
     * Asynchronously get a list of users who contributed a track to a group
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}/contributors")
    void getGroupContributors(@Path("id") int groupId, Callback<List<User>> callback);

    /**
     * Get a list of users who contributed to, joined or moderate a group
     *
     * @param groupId The group's Id
     * @return A list of users who contributed to, joined or moderate the group
     */
    @GET("/groups/{id}/users")
    List<User> getGroupUsers(@Path("id") int groupId);

    /**
     * Asynchronously get a list of users who contributed to, joined or moderate a group
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}/users")
    void getGroupUsers(@Path("id") int groupId, Callback<List<User>> callback);

    /**
     * Get a list of contributed and approved tracks for a group
     *
     * @param groupId The group's Id
     * @return A list of contributed and approved tracks for the group
     */
    @GET("/groups/{id}/tracks")
    List<Track> getGroupTracks(@Path("id") int groupId);

    /**
     * Asynchronously get a list of contributed and approved tracks for a group
     *
     * @param groupId The group's Id
     * @param callback Callback method
     */
    @GET("/groups/{id}/tracks")
    void getGroupTracks(@Path("id") int groupId, Callback<List<Track>> callback);


    /***********
     * Comments
     ***********/

    /**
     * Get a comment associated with a given Id
     *
     * @param commentId The comment's Id
     * @return The comment associated with the given Id
     */
    @GET("/comments/{id}")
    Comment getComment(@Path("id") int commentId);

    /**
     * Asynchronously get a comment associated with a given Id
     *
     * @param commentId The comment's Id
     * @param callback Callback method
     */
    @GET("/comments/{id}")
    void getComment(@Path("id") int commentId, Callback<Comment> callback);


    /********
     * Apps
     ********/

    /**
     * Get an app associated with a given Id
     *
     * @param appId The app's Id
     * @return The app associated with the given Id
     */
    @GET("/apps/{id}")
    App getApp(@Path("id") int appId);

    /**
     * Asynchronously get an app associated with a given Id
     *
     * @param appId The app's Id
     * @param callback Callback method
     */
    @GET("/apps/{id}")
    void getApp(@Path("id") int appId, Callback<App> callback);


    /***********
     * Resolve
     ***********/

    /**
     * Resolve a user using their profile URL
     *
     * @param userProfileUrl The user's profile URL
     * @return The user associated with the given URL
     */
    @GET("/resolve")
    User resolveUser(@Query("url") String userProfileUrl);

    /**
     * Asynchronously resolve a user using their profile URL
     *
     * @param userProfileUrl The user's profile URL
     * @param callback Callback method
     */
    @GET("/resolve")
    void resolveUser(@Query("url") String userProfileUrl, Callback<User> callback);

    /**
     * Resolve a track using a URL
     *
     * @param trackUrl The track URL
     * @return The track associated with the given URL
     */
    @GET("/resolve")
    Track resolveTrack(@Query("url") String trackUrl);

    /**
     * Asynchronously resolve a track using a URL
     *
     * @param trackUrl The track URL
     * @param callback Callback method
     */
    @GET("/resolve")
    void resolveTrack(@Query("url") String trackUrl, Callback<Track> callback);

    /**
     * Resolve a playlist (set) using a URL
     *
     * @param playlistUrl The playlist URL
     * @return The playlist associated with the given URL
     */
    @GET("/resolve")
    Playlist resolvePlaylist(@Query("url") String playlistUrl);

    /**
     * Asynchronously resolve a playlist (set) using a URL
     *
     * @param playlistUrl The playlist URL
     * @param callback Callback method
     */
    @GET("/resolve")
    void resolvePlaylist(@Query("url") String playlistUrl, Callback<Playlist> callback);

    /**
     * Resolve a group using a URL
     *
     * @param groupUrl The group URL
     * @return The group associated with the given URL
     */
    @GET("/resolve")
    Group resolveGroup(@Query("url") String groupUrl);

    /**
     * Asynchronously resolve a group using a URL
     *
     * @param groupUrl The group URL
     * @param callback Callback method
     */
    @GET("/resolve")
    void resolveGroup(@Query("url") String groupUrl, Callback<Group> callback);

    /**
     * Resolve an app using a URL
     *
     * @param appUrl The app URL
     * @return The app associated with the given URL
     */
    @GET("/resolve")
    App resolveApp(@Query("url") String appUrl);

    /**
     * Asynchronously resolve an app using a URL
     *
     * @param appUrl The app URL
     * @param callback Callback method
     */
    @GET("/resolve")
    void resolveApp(@Query("url") String appUrl, Callback<App> callback);
}
