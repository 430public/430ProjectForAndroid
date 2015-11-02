package com.projectforandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.data.DetialNewsBean;
import com.projectforandroid.data.NewsExample;
import com.projectforandroid.utils.fileutils.FileUtils;
import com.projectforandroid.widget.GeneralImageView;
import java.io.File;
import java.security.Key;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/10/8.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<DetialNewsBean> news;
    private Context mContext;


    public RecyclerViewAdapter(Context context, List<DetialNewsBean> news) {
        this.mContext = context;
        this.news = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_collect, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 给ViewHolder设置元素
        DetialNewsBean p = news.get(i);
        viewHolder.c_title.setText(p.title);
        viewHolder.c_content.setText(p.description);
        viewHolder.c_time.setText(p.time);
        viewHolder.c_img.loadImage(p.imgurl);
    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return news.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView c_content;
        public TextView c_time;
        public TextView c_title;
        public GeneralImageView c_img;

        public ViewHolder(View v) {
            super(v);
            c_title = (TextView) v.findViewById(R.id.cardview_title);
            c_time = (TextView) v.findViewById(R.id.cardview_time);
            c_content = (TextView) v.findViewById(R.id.cardview_content);
            c_img = (GeneralImageView) v.findViewById(R.id.cardview_imageView);
        }
    }
}
