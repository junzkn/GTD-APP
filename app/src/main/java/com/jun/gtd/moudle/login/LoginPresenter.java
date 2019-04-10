package com.jun.gtd.moudle.login;

import com.jun.gtd.base.App;
import com.jun.gtd.base.BasePresenter;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.remote.NetCallBackSWL;

public class LoginPresenter extends BasePresenter<LoginContract.View,LoginContract.Model> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.Model model) {
        super(model);
    }

    @Override
    public void requestLogin(String username, String password) {
        mView.displayProgress();
        mModel.executeLogin(username, password, this, new NetCallBackSWL<ResponseDataBean<UserBean>>() {
            @Override
            public void onSuccess(ResponseDataBean<UserBean> date) {
                App.Login.login(date.getData());
                mView.loginSuccess();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                mView.dismissProgress();
            }
        });
    }

    @Override
    public void requestRegister(String username, String password, String repassword) {
        mView.displayProgress();
        mModel.executeRegister(username, password, repassword, this, new NetCallBackSWL<ResponseDataBean<UserBean>>() {
            @Override
            public void onSuccess(ResponseDataBean<UserBean> date) {
                App.Login.login(date.getData());
                mView.loginSuccess();
            }
            @Override
            public void onFinished() {
                super.onFinished();
                mView.dismissProgress();
            }
        });
    }
}
