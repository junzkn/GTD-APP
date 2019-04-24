package com.jun.gtd.moudle.main;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;

import com.jun.gtd.base.BaseContract;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.NetCallBack;

import java.util.List;

public class MainContract {


    /**
     * 既紧急又重要
     */
    public static final int PRIORITY_URGENT_IMPORTANT = 1;

    /**
     * 重要不紧急
     */
    public static final int PRIORITY_IMPORTANT_NOTURGENT = 2;

    /**
     * 紧急不重要
     */
    public static final int PRIORITY_URGENT_NOTIMPORTANT = 3;

    /**
     * 不紧急不重要
     */
    public static final int PRIORITY_NOTURGENT_NOTIMPORTANT = 0;


    interface View extends BaseContract.View{
        void displayTodoList(List<TodoBean> todos) ;
        void guidLogin();
        void refresh() ;
    }


    interface Presenter extends BaseContract.Presenter {
        void requestGetTodo(int symbol , int type, SwipeRefreshLayout refreshLayout);
        void requestUpdateTodoStatus(int id, int status );
        void requestDeleteTodo(int id);
    }

    interface Model extends BaseContract.Model {
        void executeGetTodo(int symbol , int type, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<List<TodoBean>>> callback);
        void executeUpdateTodoStatus(int id, int status , PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<TodoBean>> callback);
        void executeDeleteTodo(int id, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean> callback);
    }

}
