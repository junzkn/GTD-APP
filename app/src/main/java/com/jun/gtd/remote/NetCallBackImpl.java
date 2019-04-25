package com.jun.gtd.remote;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.jun.gtd.R;
import com.jun.gtd.net.NetCallBack;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ViewEmptyUtils;

import java.lang.ref.WeakReference;

public abstract class NetCallBackImpl<T> implements NetCallBack<T> {

    private WeakReference<SwipeRefreshLayout> refreshLayout;

    public NetCallBackImpl() {}

    public NetCallBackImpl(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = new WeakReference<>(refreshLayout);
    }

    @Override
    public void onFinished() {
        if (refreshLayout != null && refreshLayout.get() != null) {
            refreshLayout.get().setRefreshing(false);
        }
    }

    @Override
    public void onFail(int code, String msg) {
        if (refreshLayout != null && refreshLayout.get() != null) {
            for (int i = 0; i < refreshLayout.get().getChildCount(); i++) {
                if (!(refreshLayout.get().getChildAt(i) instanceof RecyclerView)) continue;
                RecyclerView recyclerView = (RecyclerView) refreshLayout.get().getChildAt(i);
                if(code==-200) {
                    ToastUtils.error(R.string.please_check_network);
                    ViewEmptyUtils.setError(recyclerView, msg);
                }
                else ViewEmptyUtils.setEmpty(recyclerView);
                return;
            }
        }
    }

    @Override
    public void onSuccess(T date) {

    }
}
