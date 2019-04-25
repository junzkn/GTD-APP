package com.jun.gtd.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.jun.gtd.utils.StatusBarUtils;
import com.jun.gtd.utils.ToastUtils;
import com.jun.gtd.utils.ToolbarUtils;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter ;
    protected Context mContext ;
    protected Activity mActivity ;

    protected abstract int getLayoutId();
    protected abstract T initPresenter();
    protected abstract void init();
    protected abstract void prepare();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ToolbarUtils.initTranslucent(this);
        super.onCreate(savedInstanceState);
        mContext = this ;
        mActivity = this ;
        setContentView(getLayoutId());
        mPresenter = initPresenter();
        mPresenter.attachView(this);
        init();
        prepare();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
        mPresenter.detachView();
    }
}
