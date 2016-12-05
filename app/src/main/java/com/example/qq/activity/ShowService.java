package com.example.qq.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.activity.BaseActivity;

/**
 * Created by 小凳子 on 2016/12/1.
 */

public class ShowService extends BaseActivity {
    private WebView webView;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.show_service);

        ((TextView) findViewById(R.id.title_text)).setText("QQ软件许可及服务");
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://192.168.191.1:8080/SSM/service/getService");
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        ((TextView) findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowService.this.finish();
            }
        });
    }

}
