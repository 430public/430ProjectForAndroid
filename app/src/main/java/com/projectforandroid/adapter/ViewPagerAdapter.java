package com.projectforandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by 大灯泡 on 2015/9/22.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> pagers;
    List<String> titles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> title) {
        super(fm);
        this.pagers = fragments;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return pagers.get(position);
    }

    @Override
    public int getCount() {
        return pagers.size() == 0 ? 0 : pagers.size();
    }

    //获取页面标题内容
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
