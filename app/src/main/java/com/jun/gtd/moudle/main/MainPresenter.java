package com.jun.gtd.moudle.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BasePresenter;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.Net;
import com.jun.gtd.remote.NetCallBackSWL;
import com.jun.gtd.utils.ToastUtils;

import java.util.List;

public class MainPresenter extends BasePresenter<MainContract.View,MainContract.Model> implements MainContract.Presenter {

    public MainPresenter(MainContract.Model model) {
        super(model);
    }

    @Override
    public void requestGetTodo(int symbol, int type, SwipeRefreshLayout refreshLayout) {
        mModel.executeGetTodo(symbol,type,this ,new NetCallBackSWL<ResponseDataBean<List<TodoBean>>>(refreshLayout){
            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
                if (code == Net.NO_LOGIN) {
                    mView.guidLogin();
                }
            }

            @Override
            public void onSuccess(ResponseDataBean<List<TodoBean>> date) {
                super.onSuccess(date);
                mView.displayTodoList(date.getData());
            }
        });
    }


    @Override
    public void requestUpdateTodoStatus(int id, int status) {
        mModel.executeUpdateTodoStatus(id, status, this, new NetCallBackSWL<ResponseDataBean<TodoBean>>() {
            @Override
            public void onFinished() {
                super.onFinished();
            }

            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
            }

            @Override
            public void onSuccess(ResponseDataBean<TodoBean> date) {
                super.onSuccess(date);
                mView.refresh();
            }
        });
    }

    @Override
    public void requestDeleteTodo(int id) {
        mModel.executeDeleteTodo(id, this, new NetCallBackSWL<ResponseDataBean>() {
            @Override
            public void onSuccess(ResponseDataBean date) {
                super.onSuccess(date);
                ToastUtils.info(App.getInstance().getString(R.string.success_delete));
            }
        });
    }
}
