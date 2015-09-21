package com.projectforandroid.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TabWidget;
import com.projectforandroid.R;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.ui.fragment.EntertainmentFragment;
import com.projectforandroid.ui.fragment.FragmentSport;
import com.projectforandroid.ui.fragment.TopLineFragment;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import java.util.ArrayList;

/**
 * Created by 杰 on 2015/9/21.
 * 启动的第一个界面
 */
public class AppStart extends BaseActivity implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ViewPager pager;
    private Fragment mFragment1;
    private Fragment mFragment2;
    private Fragment mFragment3;
    private ArrayList<Fragment> fragmentlist;
    private String[] title;
    private MyPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fristpage);
        initialize();
        init();
        //插入所有的tab
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(tabHost.newTab().
                setText(adapter.getPageTitle(i)).setTabListener(this));

        }
    }
    //写一个ViewPager的配置方法
    public void init() {
        mFragment1 = new TopLineFragment();
        mFragment2 = new EntertainmentFragment();
        mFragment3 = new FragmentSport();
        fragmentlist = new ArrayList<Fragment>();
        fragmentlist.add(mFragment1);
        fragmentlist.add(mFragment2);
        fragmentlist.add(mFragment3);
       title = new String[] { "头条", "娱乐", "体育" };
        adapter=new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //写一个PagerAdapter类
    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //获取各个页面
        @Override
        public Fragment getItem(int arg0) {
            return fragmentlist.get(arg0);
        }
        //获取页面总数
        @Override
        public int getCount() {
            return fragmentlist.size();
        }
        //获取页面标题内容
        @Override
        public CharSequence getPageTitle(int position) {;
            return title[position];
        }
    }

    //------------------------------------------initview-----------------------------------------------
    private void initialize() {
        tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        pager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
