package com.vivekanand.literature.literatureofvivekanand.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vivekanand.literature.literatureofvivekanand.fragment.PageFragment;

import java.util.List;

/**
 * Created by gaurav on 13/02/18.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Integer> images;

    public ViewPagerAdapter(FragmentManager fm, List<Integer> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    public void setImages(List<Integer> images){
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
