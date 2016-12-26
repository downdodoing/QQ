package com.example.qq.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.qq.R;

import java.util.Arrays;
import java.util.LinkedList;

public class News extends BaseActivity {
    private LinkedList<String> mListItems;
    private ListView listView;
    public static String[] mStrings = {"一条微博1", "两条微博2", "三条微博3", "四条微博4", "五条微博5",
            "六条微博", "七条微博", "八条微博", "九条微博", "十条微博", "十一条微博", "十二条微博"};

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.news);
        listView = (ListView) findViewById(R.id.news_list);
        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mListItems);

        listView.setAdapter(adapter);
    }
}
