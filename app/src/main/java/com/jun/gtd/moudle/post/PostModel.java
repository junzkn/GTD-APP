package com.jun.gtd.moudle.post;

import com.jun.gtd.base.BaseModel;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.Net;
import com.jun.gtd.net.NetCallBack;
import com.jun.gtd.net.NetCallBackHandler;

public class PostModel extends BaseModel implements PostContract.Model {
    @Override
    public void executeAddTodo(TodoBean todoBean, PresenterLifecycle lifecycle, NetCallBack<ResponseDataBean<TodoBean>> callBack) {
        Net.getInstance().postAddTodo(todoBean, NetCallBackHandler.getCallBack(lifecycle,callBack));
    }

    @Override
    public void executeUpdateTodo(TodoBean todoBean, PresenterLifecycle lifecycle, NetCallBack<ResponseDataBean<TodoBean>> callBack) {
        Net.getInstance().postUpdateTodo(todoBean, NetCallBackHandler.getCallBack(lifecycle, callBack));

    }
}
