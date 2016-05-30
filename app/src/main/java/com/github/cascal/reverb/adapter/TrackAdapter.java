package com.github.cascal.reverb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.soundcloud.model.Track;
import com.github.cascal.reverb.soundcloud.model.User;
import com.github.cascal.reverb.util.StringUtils;
import com.github.cascal.reverb.util.ViewHolder;
import com.squareup.picasso.Picasso;

public class TrackAdapter extends ListItemAdapter<Track> {

    public TrackAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);
        }
        Track track = getItem(position);
        TextView artistTextView = ViewHolder.get(convertView, R.id.artistNameTextView);
        TextView titleTextView = ViewHolder.get(convertView, R.id.trackTitleTextView);
        TextView playCountTextView = ViewHolder.get(convertView, R.id.playCountTextView);
        TextView favoriteCountTextView = ViewHolder.get(convertView, R.id.favoriteCountTextView);
        TextView likeCountTextView = ViewHolder.get(convertView, R.id.likeCountTextView);
        TextView commentCountTextView = ViewHolder.get(convertView, R.id.commentCountTextView);
        ImageView artworkImageView = ViewHolder.get(convertView, R.id.artworkImageView);

        User artist = track.getUser();
        if (artist != null)
            artistTextView.setText(artist.getUsername());
        titleTextView.setText(track.getTitle());
        playCountTextView.setText(StringUtils.intToStringWithCommas(track.getPlaybackCount()));
        favoriteCountTextView.setText(StringUtils.intToStringWithCommas(track.getFavoritingsCount()));
        likeCountTextView.setText(StringUtils.intToStringWithCommas(track.getLikesCount()));
        commentCountTextView.setText(StringUtils.intToStringWithCommas(track.getCommentCount()));

        String artworkUrl = track.getArtworkUrl();
        if (artworkUrl == null && artist != null) {
            artworkUrl = artist.getAvatarUrl();
        }

        Picasso.with(getContext())
                .load(artworkUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(artworkImageView);

        return convertView;
    }
}