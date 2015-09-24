package com.projectforandroid.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.projectforandroid.R;
import com.projectforandroid.data.GetChannelList;
import com.projectforandroid.data.GetNewsDetail;
import com.projectforandroid.data.GetNewsList;
import com.projectforandroid.http.OnResponseListener;
import com.projectforandroid.model.BaseResponse;
import com.projectforandroid.model.Channel;
import com.projectforandroid.model.NewsDetail;
import com.projectforandroid.model.NewsDigest;
import com.projectforandroid.model.NewsList;
import com.projectforandroid.utils.viewutils.L;
import com.show.api.ShowApiRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mBtn = (Button) findViewById(R.id.activity_test_btn);
        mTv = (TextView) findViewById(R.id.activity_test_tv);


        mBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GetNewsDetail getNewsDetail = new GetNewsDetail();
                getNewsDetail.setOnResponseListener(new OnResponseListener() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        NewsDetail detail = (NewsDetail) response.getData();
                        L.i(detail.toString());
                    }

                    @Override
                    public void onFailure(BaseResponse response) {

                    }

                    @Override
                    public void onStart() {
                        L.i("由我来获取新闻列表！");
                    }
                });
                getNewsDetail.getNewsDetail("http://www.leiphone.com/news/201509/eeCZwlkS5ICRyvv2.html");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
