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

}
