package com.jun.gtd.moudle.login;

import com.jun.gtd.base.BaseContract;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.net.NetCallBack;

public class LoginContract {

    interface View extends BaseContract.View {
        void displayProgress();
        void dismissProgress();
        void switchToLogin();
        void switchToRegister();
        void loginSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        void requestLogin(String username, String password);
        void requestRegister(String username, String password,String repassword);
    }

    interface Model extends BaseContract.Model{
        void executeLogin(String username, String password, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<UserBean>> callback);
        void executeRegister(String username, String password, String repassword, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<UserBean>> callback);
    }


}
