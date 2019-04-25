package com.jun.gtd.moudle.login;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BasePresenter;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.remote.NetCallBackImpl;
import com.jun.gtd.utils.ToastUtils;

public class LoginPresenter extends BasePresenter<LoginContract.View,LoginContract.Model> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.Model model) {
        super(model);
    }

    @Override
    public void requestLogin(String username, String password) {
        mView.displayProgress();
        mModel.executeLogin(username, password, this, new NetCallBackImpl<ResponseDataBean<UserBean>>() {
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

            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
                ToastUtils.error(R.string.please_check_network);
            }
        });
    }

    @Override
    public void requestRegister(String username, String password, String repassword) {
        mView.displayProgress();
        mModel.executeRegister(username, password, repassword, this, new NetCallBackImpl<ResponseDataBean<UserBean>>() {
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

            @Override
            public void onFail(int code, String msg) {
                super.onFail(code, msg);
                ToastUtils.error(R.string.please_check_network);
            }
        });
    }
}
