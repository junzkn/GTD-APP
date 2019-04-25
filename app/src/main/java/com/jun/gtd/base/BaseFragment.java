package com.jun.gtd.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jun.gtd.utils.ToastUtils;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T mPresenter ;
    protected View rootView;

    protected abstract int getLayoutId();
    protected abstract void init();
    protected abstract T initPresenter();
    protected abstract void prepare();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter() ;
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView = inflater.inflate(getLayoutId(),container,false) ;
        }
        init();
        prepare();
        return rootView ;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ToastUtils.cancel();
        mPresenter.detachView();
    }


    public View getRootView(){
        return rootView ;
    }
}
