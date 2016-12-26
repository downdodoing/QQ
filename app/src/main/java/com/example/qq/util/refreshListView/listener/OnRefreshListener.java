package com.example.qq.util.refreshListView.listener;

import com.example.qq.util.refreshListView.PullToRefreshLayout;

public interface OnRefreshListener {
    /**
     * 刷新操作
     */
    void onRefresh(PullToRefreshLayout pullToRefreshLayout);

    /**
     * 加载操作
     */
    void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
}
