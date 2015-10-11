package com.projectforandroid.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.projectforandroid.R;
import com.projectforandroid.adapter.FragmentAdapter;
import com.projectforandroid.data.GetNewsDetail;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.model.BaseResponse;
import com.projectforandroid.model.NewsDetail;
import com.projectforandroid.ui.activity.TopLine;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/9/24.
 */
public class TopLineFragment extends Fragment {
    ArrayList<NewsDetail> mylist=new ArrayList<NewsDetail>();
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topline, null);
        ListView listView=(ListView)view.findViewById(R.id.topline_listview);
        GetNewsDetail mygetter=new GetNewsDetail();
        mygetter.setOnResponseListener(new OnResponseListener() {
            @Override
            public void onSuccess(BaseResponse response) {
                NewsDetail detail = (NewsDetail) response.getData();
            }

            @Override
            public void onFailure(BaseResponse response) {

            }

            @Override
            public void onStart() {

            }
        });
        mygetter.getNewsDetail("http://www.leiphone.com/news/201509/eeCZwlkS5ICRyvv2.html");
        FragmentAdapter adapter=new FragmentAdapter(getActivity(),mylist,listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TopLine.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
