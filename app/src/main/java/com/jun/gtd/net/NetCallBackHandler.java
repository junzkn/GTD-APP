package com.jun.gtd.net;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetCallBackHandler {

    public static<T extends ResponseDataBean> Callback<T> getCallBack
            (final PresenterLifecycle presenterLifecycle , final NetCallBack<T> netCallBack){
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if(presenterLifecycle.isEnd()) return ;
                netCallBack.onFinished();
                ResponseDataBean responseDataBean = response.body() ;
                if(responseDataBean!=null){
                    if(responseDataBean.getErrorCode() == 0){
                        netCallBack.onSuccess(response.body());
                    }else{
                        netCallBack.onFail(responseDataBean.getErrorCode(),responseDataBean.getErrorMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (presenterLifecycle.isEnd()) return;
                netCallBack.onFinished();
                String errMsg;
                if (t instanceof UnknownHostException || t instanceof ConnectException) {
                    errMsg = App.getInstance().getString(R.string.please_check_network);
                } else if (t instanceof TimeoutException || t instanceof SocketTimeoutException) {
                    errMsg = App.getInstance().getString(R.string.link_out_time);
                } else {
                    errMsg = t.getMessage();
                }
                netCallBack.onFail(-200,errMsg);
            }
        };

    }
}
