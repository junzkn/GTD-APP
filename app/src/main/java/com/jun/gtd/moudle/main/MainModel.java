package com.jun.gtd.moudle.main;

import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.Net;
import com.jun.gtd.net.NetCallBack;
import com.jun.gtd.net.NetCallBackHandler;

import java.util.List;

public class MainModel implements MainContract.Model {
    @Override
    public void executeGetTodo(int symbol , int type, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<List<TodoBean>>> callback) {
        Net.getInstance().getGetTodo(symbol,type, NetCallBackHandler.getCallBack(presenterLife,callback));
    }

    @Override
    public void executeUpdateTodoStatus(int id , int status, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<TodoBean>> callback) {
        Net.getInstance().postUpdateTodoStatus(id,status,NetCallBackHandler.getCallBack(presenterLife,callback));
    }

    @Override
    public void executeDeleteTodo(int id, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean> callback) {
        Net.getInstance().postDeleteTodo(id,NetCallBackHandler.getCallBack(presenterLife,callback));
    }

    @Override
    public void executeLogout(PresenterLifecycle presenterLifecycle, NetCallBack<ResponseDataBean> callBack) {
        Net.getInstance().postLogout(NetCallBackHandler.getCallBack(presenterLifecycle,callBack));
    }


}
