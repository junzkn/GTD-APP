package com.jun.gtd.moudle.post;

import com.jun.gtd.base.BaseContract;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.net.NetCallBack;

public class PostContract  {
    interface View extends BaseContract.View {

        void showChooseCalender();
        void hideChooseCalender();
        void showChooseCategory();
        void hideChooseCategory();
        void showChoosePriority();
        void hideChoosePriority();

        void displayAddTodoIng();
        void displayAddTodoFinished();
    }

    interface Presenter extends BaseContract.Presenter{
        void requestAddTodo(TodoBean todoBean) ;
        void requestUpdate(TodoBean todoBean) ;
    }

    interface Model extends BaseContract.Model{
        void executeAddTodo(TodoBean todoBean, PresenterLifecycle lifecycle, NetCallBack<ResponseDataBean<TodoBean>> callBack) ;
        void executeUpdateTodo(TodoBean todoBean, PresenterLifecycle lifecycle, NetCallBack<ResponseDataBean<TodoBean>> callBack) ;
    }
}
