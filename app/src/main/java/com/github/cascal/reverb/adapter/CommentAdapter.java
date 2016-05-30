package com.github.cascal.reverb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.cascal.reverb.R;
import com.github.cascal.reverb.soundcloud.model.Comment;
import com.github.cascal.reverb.soundcloud.model.User;
import com.github.cascal.reverb.util.ViewHolder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommentAdapter extends ListItemAdapter<Comment> {

    public CommentAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_comment, parent, false);
        }
        ImageView avatarImageView = ViewHolder.get(convertView, R.id.avatarImageView);
        TextView userNameTextView = ViewHolder.get(convertView, R.id.userNameTextView);
        TextView timeTextView = ViewHolder.get(convertView, R.id.timeTextView);
        TextView bodyTextView = ViewHolder.get(convertView, R.id.bodyTextView);

        Comment comment = getItem(position);
        User user = comment.getUser();
        userNameTextView.setText(user != null ? user.getUsername() : "Unknown");

        Date date = null;
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z", Locale.US);
        try {
            date = inFormat.parse(comment.getCreatedAt());
        } catch (Exception e){
            Log.e("TAG", "unable to parse date");
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        timeTextView.setText(date != null ? outFormat.format(date) : "Unknown");
        bodyTextView.setText(comment.getBody());

        if (user != null) {
            Picasso.with(getContext())
                    .load(user.getAvatarUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(avatarImageView);
        }
        return convertView;
    }
}