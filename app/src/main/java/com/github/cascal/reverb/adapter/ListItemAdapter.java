package com.github.cascal.reverb.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

public abstract class ListItemAdapter<T> extends BaseAdapter {
    private List<T> items = Collections.emptyList();
    private final Context context;

    public ListItemAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swapList(List<T> newItems) {
        items = newItems;
        if (items == null)
            items = Collections.emptyList();
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return items;
    }

    protected Context getContext() {
        return context;
    }
}
