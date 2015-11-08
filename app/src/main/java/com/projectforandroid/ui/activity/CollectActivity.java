package com.projectforandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.projectforandroid.R;
import com.projectforandroid.adapter.RecyclerViewAdapter;
import com.projectforandroid.data.DetialNewsBean;
import com.projectforandroid.data.NewsExample;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.utils.fileutils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/10/8.
 */
public class CollectActivity extends BaseActivity {

    private List<DetialNewsBean> newslist = new ArrayList<>();
    private DetialNewsBean dbean;
    private List<JSONObject> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        list=FileUtils.getDataFromCache();
        PutData();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, newslist);

        RecyclerView RView = (RecyclerView) findViewById(R.id.recyclerView);
        RView.setHasFixedSize(true);
        RView.setLayoutManager(new LinearLayoutManager(this));
        RView.setItemAnimator(new DefaultItemAnimator());
        RView.setAdapter(adapter);
    }

    //------------------------------------------解析从本地获取的新闻数据-----------------------------------------------
    public void PutData() {
        for (int i = 0; i < list.size(); i++) {
            dbean = new DetialNewsBean();
            dbean.title = list.get(i).optString("title");
            dbean.description = list.get(i).optString("description");
            dbean.url = list.get(i).optString("url");
            dbean.time = list.get(i).optString("time");
            dbean.imgurl = list.get(i).optString("picurl");
            newslist.add(i, dbean);
        }
    }
}
