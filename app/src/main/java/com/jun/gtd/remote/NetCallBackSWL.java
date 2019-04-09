package com.jun.gtd.remote;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.jun.gtd.net.NetCallBack;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ViewEmptyUtils;

import java.lang.ref.WeakReference;

public abstract class NetCallBackSWL<T> implements NetCallBack<T> {

    private WeakReference<SwipeRefreshLayout> refreshLayout;

    public NetCallBackSWL() {}

    public NetCallBackSWL(SwipeRefreshLayout refreshLayout) {
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
        if(msg!=null) ToastUtils.error(msg);
        if (refreshLayout != null && refreshLayout.get() != null) {
            for (int i = 0; i < refreshLayout.get().getChildCount(); i++) {
                if (!(refreshLayout.get().getChildAt(i) instanceof RecyclerView)) continue;
                RecyclerView recyclerView = (RecyclerView) refreshLayout.get().getChildAt(i);
                ViewEmptyUtils.setError(recyclerView, msg);
                return;
            }
        }
    }

    @Override
    public void onSuccess(T date) {

    }
}
