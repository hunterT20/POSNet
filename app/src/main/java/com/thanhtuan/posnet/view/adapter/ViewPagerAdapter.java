package com.thanhtuan.posnet.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thanhtuan.posnet.view.fragment.ReorderFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"Thông tin", "DS Sản phẩm",};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReorderFragment();
            case 1:
                return new ReorderFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
