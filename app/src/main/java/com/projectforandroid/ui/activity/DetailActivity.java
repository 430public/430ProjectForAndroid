package com.projectforandroid.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.ui.activity.base.BaseActivity;
import java.util.ArrayList;

/**
 * Created by 杰 on 2015/10/16.
 */
public class DetailActivity extends BaseActivity{
    private TextView newstitle;
    private TextView newstime;
    private TextView news_content;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        setData();
    }
    private void initView(){
        newstitle=(TextView)findViewById(R.id.newstitle);
        newstime=(TextView)findViewById((R.id.newstime));
        news_content=(TextView)findViewById(R.id.news_content);
    }
    private void setData(){
        list= getIntent().getStringArrayListExtra("detail");
        Log.i("tag",list.get(2));
        newstitle.setText(list.get(0));
        newstime.setText(list.get(1));
        news_content.setText(list.get(2));
    }
}
