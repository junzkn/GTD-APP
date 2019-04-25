package com.jun.gtd.moudle.main;

import android.support.v4.widget.SwipeRefreshLayout;

import com.jun.gtd.base.BaseContract;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.NetCallBack;

import java.util.List;

public class MainContract {



    interface View extends BaseContract.View{
        void displayTodoList(List<TodoBean> todos) ;
        void guidLogin();
        void refresh() ;
    }


    interface Presenter extends BaseContract.Presenter {
        void requestGetTodo(int symbol , int type, SwipeRefreshLayout refreshLayout);
        void requestUpdateTodoStatus(int id, int status );
        void requestDeleteTodo(int id);
        void requestLogout();
    }

    interface Model extends BaseContract.Model {
        void executeGetTodo(int symbol , int type, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<List<TodoBean>>> callback);
        void executeUpdateTodoStatus(int id, int status , PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<TodoBean>> callback);
        void executeDeleteTodo(int id, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean> callback);
        void executeLogout(PresenterLifecycle presenterLifecycle , NetCallBack<ResponseDataBean> callBack);
    }

}
