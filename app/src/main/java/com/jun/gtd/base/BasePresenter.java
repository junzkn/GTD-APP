package com.jun.gtd.base;

public abstract class BasePresenter<T,E> {
    protected T mView ;
    protected E mModel ;




    public void attachView(T view){
        this.mView = view ;
    }

    public void detachView() {
        mView =null ;
    }

    public BasePresenter(E model){
        this.mModel = model ;
    }




}
