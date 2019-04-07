package com.jun.gtd.utils;

public interface Common {

    interface Net{
        String BASE_URL = "http://jun:8080" ;
        int TIME_OUT_CONNECT = 200 ;
        int TIME_OUT_READ = 200 ;
        String COOKIE_NAME = "Cookie";
        String SAVE_USER_LOGIN_KEY = "user/login";
        String SET_COOKIE_KEY = "set-cookie";
    }

    interface Todo{
        int GET_BY_TYPE = 1001 ;
        int GET_BY_STATUS = 1002;
        int GET_ALL = 1003 ;
    }

}
