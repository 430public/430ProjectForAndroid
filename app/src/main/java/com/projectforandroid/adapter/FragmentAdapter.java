package com.projectforandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.projectforandroid.R;
import com.projectforandroid.data.GetNewsDetail;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.model.NewsDetail;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/9/24.
 */
public class FragmentAdapter extends ListBaseAdapter {
    ArrayList<NewsDetail> list=new ArrayList<NewsDetail>(){};
    //定义静态方法ViewHolder
    static class ViewHolder {
        public ImageView Header;
        public TextView Title;
        public TextView Content;
        public ImageView img_jiantou;
    }

    public FragmentAdapter(Context context, List list, ListView listView) {
        super(context, list, listView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_toplineitem,parent,false);
            holder.Title=(TextView)convertView.findViewById(R.id.tv_title);
            holder.Header=(ImageView)convertView.findViewById(R.id.item_avatar);
            holder.Content=(TextView)convertView.findViewById(R.id.tv_content);
            holder.img_jiantou=(ImageView)convertView.findViewById(R.id.imgbutton_jiantou);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.Title.setText(list.get(position).getTitle());
       // holder.Header.setImageResource(list.get(position).getImageUrls()[position]);
        holder.Content.setText(list.get(position).getNeirong());
        holder.img_jiantou.setImageResource(R.drawable.btn_jiantou);
        return convertView;
    }
}

