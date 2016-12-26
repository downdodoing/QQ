package com.example.qq.activity;

import android.os.*;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.qq.R;
import com.example.qq.util.refreshListView.PullToRefreshLayout;
import com.example.qq.util.refreshListView.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.LinkedList;

public class PhoneCall extends BaseActivity {
    public static String[] mStrings = {"一条微博", "两条微博", "三条微博", "四条微博", "五条微博",
            "六条微博", "七条微博", "八条微博", "九条微博", "十条微博", "十一条微博", "十二条微博"};
    private LinkedList<String> mListItems;
    private ListView listView;
    private PullToRefreshLayout ptrl;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.phone_call);

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mListItems);

        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                // 下拉刷新操作
                new Handler() {
                    @Override
                    public void handleMessage(android.os.Message msg) {
                        mListItems.add("刷新item");
                        adapter.notifyDataSetChanged();
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 5000);
            }

            @Override
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        mListItems.add("添加item");
                        adapter.notifyDataSetChanged();
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 5000);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
