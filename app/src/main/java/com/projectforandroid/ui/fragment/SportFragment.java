package com.projectforandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.adapter.SportNewsFragmentAdapter;
import com.projectforandroid.data.EntertainmentBean;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.data.SportNewsBean;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.http.request.SportNewsRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.ui.activity.DetailActivity;
import com.projectforandroid.utils.dateutils.DateUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杰 on 2015/9/21.
 */
public class SportFragment extends Fragment implements OnResponseListener {

    private SportNewsRequest mSportNewsRequest;
    private SportNewsFragmentAdapter mSportFragmentAdapter;
    private ListView sListview;
    private List<SportNewsBean.SportNewsBeanResult> slist;
    private Intent intent;
    private ArrayList<String> detiallist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topline, null);//复用topLine的布局文件
        slist = new ArrayList<>();
        sListview = (ListView) view.findViewById(R.id.topline_listview);
        mSportFragmentAdapter = new SportNewsFragmentAdapter(this.getActivity(), slist, sListview);
        GetData();
        sListview.setAdapter(mSportFragmentAdapter);
        sListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                detiallist.clear();
                /*
                 * 0对应的是标题
                 * 1对应的是描述
                 * 2对应的是url
                 * 3对应的是时间
                 * 4对应的是图片
                 */
                detiallist.add(0, slist.get(position).title);
                detiallist.add(1, slist.get(position).description);
                detiallist.add(2, slist.get(position).url);
                detiallist.add(3, String.valueOf(slist.get(position).time));
                detiallist.add(4,slist.get(position).picUrl);
                UIHelper.startToDetialActivity(getActivity(), intent, detiallist);
            }
        });
        return view;
    }

    private void GetData() {
        mSportNewsRequest = new SportNewsRequest(this.getActivity(), 10, 1, "10881",
            DateUtils.getyyyyMMddHHmmss(System.currentTimeMillis()),
            ProjectApplication.getSecretKey());
        mSportNewsRequest.setOnResponseListener(this);
        if (mSportNewsRequest.LoadCache() != null) {
            SportNewsBean sbean = (SportNewsBean) mSportNewsRequest.LoadCache();
            this.slist.clear();
            this.slist.addAll(sbean.getSportNewsBeans());
            mSportFragmentAdapter.notifyDataSetChanged();
        } else {
            mSportNewsRequest.execute();
        }
    }

    //------------------------------------------listener-----------------------------------------------
    @Override
    public void onSuccess(BaseResponse response) {
        if (response.getStatus() == 0) {
            return;
        } else if (response.getStatus() != 0) {
            SportNewsBean sbean = (SportNewsBean) response.getData();
            slist.clear();
            slist.addAll(sbean.getSportNewsBeans());
            mSportFragmentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(BaseResponse response) {
        UIHelper.ToastMessage(this.getActivity(), "失败", 0);
    }

    @Override
    public void onHttpStart() {

    }

    @Override
    public void onHttpFinish() {

    }
}
