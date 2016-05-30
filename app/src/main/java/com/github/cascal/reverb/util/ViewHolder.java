package com.github.cascal.reverb.util;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int resId) {
        SparseArray<View> childrenViews = (SparseArray<View>) view.getTag();
        if (childrenViews == null) {
            childrenViews = new SparseArray<>();
            view.setTag(childrenViews);
        }
        View child = childrenViews.get(resId);
        if (child == null) {
            child = view.findViewById(resId);
            childrenViews.put(resId, child);
        }
        return (T) child;
    }
}
