package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by gaurav on 13/02/18.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private int[] imageSource;

    public ImageAdapter(Context context, int[] imageSource) {
        this.context = context;
        this.imageSource = imageSource;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
