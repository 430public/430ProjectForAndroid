package com.projectforandroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.projectforandroid.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by 杰 on 2015/9/21.
 */
public class TopLineFragment extends Fragment {


    //定义静态方法ViewHolder
    static class ViewHolder{
        public ImageView Header;
        public TextView Title;
        public TextView Content;
        public ImageView img_jiantou;

    }
    private String[] itemtitle = new String[] {
        "This is item1 title", "This is item2 title", "This is item3 title", "This is item4 title",
        "This is item5 title", "This is item6 title", "This is item7 title"
    };
    private int[] header = new int[] {
        R.drawable.header, R.drawable.header, R.drawable.header, R.drawable.header,
        R.drawable.header, R.drawable.header, R.drawable.header
    };
    private String[] content=new String[]{"This is item1 content", "This is item2 content", "This is item3 content", "This is item4 content",
        "This is item5 content", "This is item6 content", "This is item7 content"};


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topline, null);
        ListView listView=(ListView)view.findViewById(R.id.topline_listview);
        //定义一个Baseadapter
        BaseAdapter adapter=new BaseAdapter() {

            @Override
            public int getCount() {
                return itemtitle.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                    holder=new ViewHolder();
                    convertView=inflater.inflate(R.layout.listview_toplineitem,null);
                    holder.Title=(TextView)convertView.findViewById(R.id.tv_title);
                    holder.Header=(ImageView)convertView.findViewById(R.id.img);
                    holder.Content=(TextView)convertView.findViewById(R.id.tv_content);
                    holder.img_jiantou=(ImageView)convertView.findViewById(R.id.imgbutton_jiantou);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder)convertView.getTag();
                }
                holder.Title.setText(itemtitle[position]);
                holder.Header.setImageResource(header[position]);
                holder.Content.setText(content[position]);
                holder.img_jiantou.setImageResource(R.drawable.btn_jiantou);
                return convertView;
            }
        };
        listView.setAdapter(adapter);
        return view;
    }
}
