package com.jun.gtd.net;

public interface NetCallBack<T> {
    void onFinished();

    void onSuccess(T date);

    void onFail(int code, String msg);
}
