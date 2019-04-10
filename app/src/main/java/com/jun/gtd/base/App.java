package com.jun.gtd.base;

import android.app.Application;

import com.google.gson.Gson;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.net.Net;
import com.jun.gtd.utils.PreUtils;

public class App extends Application {
    private static App instance ;

    public static App getInstance(){
        return instance ;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this ;
    }


    /**
     * 登录控制
     */
    public static class Login{

        public static final String USER_INFO = "user_info" ;
        private static boolean isLogin ;

        public static boolean isLogin() {
            return isLogin ;
        }

        public static void login (UserBean userBean){
            isLogin = true ;
            PreUtils.set(App.Login.USER_INFO,new Gson().toJson(userBean));
        }

        public static void logout () {
            PreUtils.clearKey(Net.SAVE_USER_LOGIN_KEY);
            PreUtils.clearKey(App.Login.USER_INFO);
            isLogin = false ;
        }

    }
}
