package com.jun.gtd.utils;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jun.gtd.base.BaseActivity;

public class ToolbarUtils {

    public static void initTranslucent(BaseActivity activity) {
        StatusBarUtils.setTranslucentStatus(activity);
        StatusBarUtils.setLightMode(activity);
    }

    /**
     * 将该View的 top Padding 向下偏移一个状态栏的高度
     */
    public static void initPaddingTopDiffBar(View view) {
        view.setPadding(
                view.getPaddingLeft(),
                view.getPaddingTop() + ScreenUtils.getStatusHeight(view.getContext()),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    /**
     * 将View的top margin 向下偏移一个状态栏的高度
     */
    public static void initMarginTopDiffBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) params;
            linearParams.topMargin += ScreenUtils.getStatusHeight(view.getContext());
        } else if (params instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams frameParams = (FrameLayout.LayoutParams) params;
            frameParams.topMargin += ScreenUtils.getStatusHeight(view.getContext());
        } else if (params instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) params;
            relativeParams.topMargin += ScreenUtils.getStatusHeight(view.getContext());
        } else if (params instanceof ConstraintLayout.LayoutParams) {
            ConstraintLayout.LayoutParams constraintParams = (ConstraintLayout.LayoutParams) params;
            constraintParams.topMargin += ScreenUtils.getStatusHeight(view.getContext());
        }
        view.setLayoutParams(params);
    }

    /**
     * 将Toolbar高度填充到状态栏
     */
    public static void initFullBar(Toolbar toolbar, BaseActivity activity) {
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        params.height = ScreenUtils.getStatusHeight(activity) + ScreenUtils.getSystemActionBarSize(activity);
        toolbar.setLayoutParams(params);
        toolbar.setPadding(
                toolbar.getLeft(),
                toolbar.getTop() + ScreenUtils.getStatusHeight(activity),
                toolbar.getRight(),
                toolbar.getBottom()
        );
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
