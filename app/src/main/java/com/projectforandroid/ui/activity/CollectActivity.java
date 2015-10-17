package com.projectforandroid.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.adapter.RecyclerViewAdapter;
import com.projectforandroid.data.NewsExample;
import com.projectforandroid.ui.activity.base.BaseActivity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/10/8.
 */
public class CollectActivity extends BaseActivity {
    //------------------------------------------测试数据-----------------------------------------------
    String[] title = new String[] { "1", "2", "3", "4", "5" };
    String[] content = new String[] { "aaa", "bbb", "ccc", "ddd", "eee" };
    String[] time = new String[] { "2015-8-8", "2015-8-9", "2015-8-10", "2015-8-11", "2015-8-12" };
    int[] Id = new int[] {
        R.drawable.default_menu_bg, R.drawable.default_menu_bg, R.drawable.default_menu_bg,
        R.drawable.default_menu_bg, R.drawable.default_menu_bg
    };
    //------------------------------------------上面为测试数据-----------------------------------------------
    private List<NewsExample> newslist=new ArrayList<NewsExample>();
    int[] location=new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        //设置ToolBar
        toolbar.setTitle("");
        TextView textView=new TextView(this);
        textView.setGravity(Gravity.CENTER);

        textView.setText("个人设置");
        textView.setTextColor(Color.WHITE);
        toolbar.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));


        for(int i=0;i<5;i++){
            newslist.add(i,new NewsExample(title[i],content[i],Id[i],time[i]));
        }
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,newslist);
        RecyclerView RView= (RecyclerView) findViewById(R.id.recyclerView);
        RView.setHasFixedSize(true);
        RView.setLayoutManager(new LinearLayoutManager(this));
        RView.setItemAnimator(new DefaultItemAnimator());
        RView.setAdapter(adapter);
    }
}
