package com.jun.gtd.moudle.login;

import com.jun.gtd.R;
import com.jun.gtd.base.App;
import com.jun.gtd.base.BaseModel;
import com.jun.gtd.base.PresenterLifecycle;
import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.net.Net;
import com.jun.gtd.net.NetCallBack;
import com.jun.gtd.net.NetCallBackHandler;
import com.jun.gtd.utils.InputUtils;
import com.jun.gtd.utils.ToastUtils;

public class LoginModel extends BaseModel implements LoginContract.Model {
    @Override
    public void executeLogin(String username, String password, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<UserBean>> callback) {
        if(checkLoginInputError(username,password)){
            callback.onFinished();
            return ;
        }
        Net.getInstance().postLogin(username,password, NetCallBackHandler.getCallBack(presenterLife,callback));
    }



    @Override
    public void executeRegister(String username, String password, String repassword, PresenterLifecycle presenterLife, NetCallBack<ResponseDataBean<UserBean>> callback) {
        if(checkRegisterInputError(username,password)){
            callback.onFinished();
            return ;
        }
        Net.getInstance().postRegister(username,password,repassword,NetCallBackHandler.getCallBack(presenterLife,callback));
    }

    private boolean checkLoginInputError(String username, String password) {
        if (InputUtils.isEmpty(username)) {
            ToastUtils.error(App.getInstance().getString(R.string.please_input_account));
            return true;
        } else if (InputUtils.isEmpty(password)) {
            ToastUtils.error(App.getInstance().getString(R.string.please_input_password));
            return true;
        }
        return false;
    }

    private boolean checkRegisterInputError(String username, String password) {
        if (!InputUtils.isMatchRegEx(username,"^[0-9a-zA-Z]{1,}$")) {
            ToastUtils.error(App.getInstance().getString(R.string.input_account_error));
            return true;
        } else if (!InputUtils.isMatchRegEx(password,"^[0-9a-zA-Z]{1,}$")) {
            ToastUtils.error(App.getInstance().getString(R.string.input_password_error));
            return true;
        }
        return false;
    }
}
