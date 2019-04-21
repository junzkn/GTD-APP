package com.jun.gtd.moudle.post;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BasePresenter;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.remote.NetCallBackSWL;
import com.jun.gtd.utils.ToastUtils;

public class PostPresenter extends BasePresenter<PostContract.View,PostContract.Model> implements PostContract.Presenter {

    public PostPresenter(PostContract.Model model) {
        super(model);
    }

    @Override
    public void requestAddTodo(TodoBean todoBean) {
        mView.displayAddTodoIng();
        mModel.executeAddTodo(todoBean, this, new NetCallBackSWL<ResponseDataBean<TodoBean>>() {
            @Override
            public void onFinished() {
                super.onFinished();
                mView.displayAddTodoFinished();
            }
            @Override
            public void onSuccess(ResponseDataBean<TodoBean> date) {
                super.onSuccess(date);
                ToastUtils.info(App.getInstance().getString(R.string.success_add));
            }
        });
    }

    @Override
    public void requestUpdate(TodoBean todoBean) {
        mView.displayAddTodoIng();
        mModel.executeUpdateTodo(todoBean, this, new NetCallBackSWL<ResponseDataBean<TodoBean>>() {
            @Override
            public void onFinished() {
                super.onFinished();
                mView.displayAddTodoFinished();
            }

            @Override
            public void onSuccess(ResponseDataBean<TodoBean> date) {
                super.onSuccess(date);
                ToastUtils.info(App.getInstance().getString(R.string.success_edit));
            }
        });
    }
}
