package com.jun.gtd.utils;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jun.gtd.R;

public class ViewEmptyUtils {
    public static void setEmpty(RecyclerView recyclerView){
        if (recyclerView.getAdapter() instanceof BaseQuickAdapter) {
            ((BaseQuickAdapter) recyclerView.getAdapter()).setEmptyView(R.layout.view_empty, recyclerView);
        }
    }

    public static void setError(RecyclerView recyclerView, String errorMsg) {
        if (recyclerView.getAdapter() instanceof BaseQuickAdapter) {
            View view = LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.view_error, recyclerView, false);
            TextView textView = view.findViewById(R.id.tv_error_msg);
            if (TextUtils.isEmpty(errorMsg)) {
                textView.setVisibility(View.GONE);
            } else {
                textView.setText(errorMsg);
            }
            ((BaseQuickAdapter) recyclerView.getAdapter()).setEmptyView(view);
        }
    }
}
