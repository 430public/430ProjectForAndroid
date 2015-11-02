package com.projectforandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.cache.DiskCache;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.utils.MD5Tools;
import com.projectforandroid.utils.dateutils.DateUtils;
import com.projectforandroid.utils.fileutils.FileUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/10/16.
 */
public class DetailActivity extends BaseActivity {
    private WebView webview;
    private String FromUrl;
    private runable mRunable;
    private ArrayList<String> dlist;//接收封装好的新闻信息
    private File CollectCache;
    private ArrayList<String> CollectList;//保存要收藏的新闻的信息
    private CheckBox collect_checkbox;
    private File cacheDir;
    private static final String cacheName = "cache";
    private static String CollectCacheName = "CollectCache";
    private String title;
    private String description;
    private String url;
    private String time;
    private String Sumdata;
    private String picurl;
    private Intent intent;//向ColleActivity发送数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        setData();
        Collect();
    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webView);
        collect_checkbox = (CheckBox) findViewById(R.id.collect_checkbox);
    }

    private void setData() {
        dlist = getIntent().getStringArrayListExtra("detial");
        FromUrl = dlist.get(2);
        mRunable = new runable(FromUrl);
        mRunable.run();
    }

    private void Collect() {
        collect_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (collect_checkbox.isChecked()) {
                    CollectList = dlist;
                    Toast.makeText(DetailActivity.this, "成功收藏", Toast.LENGTH_SHORT).show();
                    saveData();
                    
                }
            }
        });
    }

    /* 将要收藏的新闻的数据存到本地 */
    private void saveData() {
        //创建存储收藏新闻的文件夹
        if (FileUtils.isSDexist()) {
            cacheDir =
                new File(FileUtils.getSDCardPath() + File.separator + "430project", cacheName);
            CollectCache = new File(cacheDir, CollectCacheName);
        } else {
            cacheDir = this.getCacheDir();
        }
        if (!cacheDir.exists()) cacheDir.mkdirs();
        if (!CollectCache.exists()) CollectCache.mkdirs();
        //往文件夹内写入收藏新闻的数据
        resolution();
        byte[] bytes=Sumdata.getBytes();
        FileUtils.saveBytesToSD(CollectCache.getAbsolutePath(),MD5Tools.hashKey(url),bytes);
    }

    /* 将CollectList数据进行解析 */
    private void resolution() {
        title = CollectList.get(0);
        description=CollectList.get(1);
        url=CollectList.get(2);
        time=CollectList.get(3);
        picurl=CollectList.get(4);
        Sumdata="{"+"\"title\""+":"+"\""+title+"\""+","
            +"\"description\""+":"+"\""+description+"\""+","
            +"\"url\""+":"+"\""+url+"\""+","
            +"\"time\""+":"+"\""+time+"\""+","
            +"\"picurl\""+":"+"\""+picurl+"\""+"}";//将数据转为Json格式
    }

    private class runable implements Runnable {
        private String url;

        public runable(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            webview.loadUrl(url);
            webview.setInitialScale(100);
        }
    }
}
