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
        Login.isLogin = PreUtils.isExist(Net.SAVE_USER_LOGIN_KEY);
        Login.userID = PreUtils.getInt(Login.USER_ID);
    }


    /**
     * 登录控制
     */
    public static class Login{

        public static final String USER_INFO = "user_info" ;
        public static final String USER_ID = "user_id" ;
        private static boolean isLogin ;
        private static int  userID ;

        public static boolean isLogin() {
            return isLogin ;
        }
        public static int getUserId() {return userID;}

        public static void login (UserBean userBean){
            isLogin = true ;
            userID = userBean.getId() ;
            PreUtils.set(App.Login.USER_INFO,new Gson().toJson(userBean));
            PreUtils.set(App.Login.USER_ID,userID);
        }

        public static void logout () {
            userID = -1 ;
            PreUtils.clearKey(Net.SAVE_USER_LOGIN_KEY);
            PreUtils.clearKey(App.Login.USER_INFO);
            PreUtils.clearKey(Login.USER_ID);
            isLogin = false ;
        }

    }
}
