package com.projectforandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.adapter.ViewPagerAdapter;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.ui.fragment.EntertainmentFragment;
import com.projectforandroid.ui.fragment.SportFragment;
import com.projectforandroid.ui.fragment.TopLineFragment;
import com.projectforandroid.widget.PagerSlidingIndicator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2015/9/22.
 * 含有viewpager的首页
 */
public class IndexActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewHolder vh;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }
    //------------------------------------------初始化页面-----------------------------------------------

    private void initView() {
        vh = new ViewHolder();
        mFragments = new ArrayList<Fragment>();
        mTitles = new ArrayList<String>();
        vh.mViewPager = (ViewPager) findViewById(R.id.index_viewpager);
        vh.mTabStrip = (PagerSlidingIndicator) findViewById(R.id.tabs);
        vh.mTopLineFragment = new TopLineFragment();
        vh.mEntertainmentFragment = new EntertainmentFragment();
        vh.mSportFragment = new SportFragment();

        // fragment
        mFragments.add(vh.mTopLineFragment);
        mFragments.add(vh.mEntertainmentFragment);
        mFragments.add(vh.mSportFragment);
        // pager title
        mTitles.add(ProjectApplication.getResString(R.string.frag_top));
        mTitles.add(ProjectApplication.getResString(R.string.frag_entertain));
        mTitles.add(ProjectApplication.getResString(R.string.frag_sport));

        //adapter
        ViewPagerAdapter adapter =
            new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vh.mViewPager.setAdapter(adapter);
        vh.mTabStrip.setViewPager(vh.mViewPager);
        // 设置tab（必须放到setAdapter后面）
        vh.mTabStrip.setShouldExpand(true);
        vh.mTabStrip.setDividerColor(ProjectApplication.getResColor(R.color.color_66a5a5a5));
        vh.mTabStrip.setUnderlineHeight((int) ProjectApplication.getResDimen(R.dimen.dp_3));
        vh.mTabStrip.setUnderlineColor(ProjectApplication.getResColor(R.color.color_f0f0f0));
        vh.mTabStrip.setDividerPadding(UIHelper.dip2px(this,ProjectApplication.getResDimen(R.dimen.dp_6)));
        vh.mTabStrip.setSelectedTextColor(ProjectApplication.getResColor(R.color.color_00bcd4));
        vh.mTabStrip.setIndicatorColor(ProjectApplication.getResColor(R.color.color_00bcd4));
        vh.mTabStrip.setIndicatorHeight((int) ProjectApplication.getResDimen(R.dimen.dp_3));
        vh.mTabStrip.setTextColor(ProjectApplication.getResColor(R.color.color_a5a5a5));
        vh.mTabStrip.setTextSize(28);
        // 监听
        vh.mTabStrip.setOnPageChangeListener(this);
    }

    //------------------------------------------页面切换监听-----------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //-----------------------------------------------------------------------------------------

    //---------------------------避免太乱，用viewholder收集变量-----------------------------------
    class ViewHolder {
        public ViewPager mViewPager;
        public PagerSlidingIndicator mTabStrip;
        public TopLineFragment mTopLineFragment;
        public EntertainmentFragment mEntertainmentFragment;
        public SportFragment mSportFragment;
    }
}
