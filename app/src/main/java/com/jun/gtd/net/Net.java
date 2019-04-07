package com.jun.gtd.net;

import android.text.TextUtils;
import android.util.Log;

import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.utils.Common;
import com.jun.gtd.utils.PreUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Net {

    private static Net mInstance;
    private API api;

    final Interceptor requestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            String cookie = PreUtil.getString(Common.Net.SAVE_USER_LOGIN_KEY);
            if (!TextUtils.isEmpty(cookie)) {
                builder.addHeader(Common.Net.COOKIE_NAME, cookie);
            }
            return chain.proceed(builder.build());
        }
    };

    final Interceptor responseInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            final String requestUrl = request.url().toString();
            if(requestUrl.contains("user")){
                PreUtil.set(Common.Net.SAVE_USER_LOGIN_KEY, encodeCookie(response.headers(Common.Net.SET_COOKIE_KEY)));
            }
            return response;
        }
    };

    private Net() {
        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(requestInterceptor)
                .addInterceptor(responseInterceptor)
                .connectTimeout(Common.Net.TIME_OUT_CONNECT, TimeUnit.SECONDS)
                .readTimeout(Common.Net.TIME_OUT_READ, TimeUnit.SECONDS)
                .build();

        api = new Retrofit.Builder()
                .baseUrl(Common.Net.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()
                .create(API.class);
    }


    public static Net getInstance() {
        if (mInstance == null) {
            synchronized (Net.class) {
                mInstance = new Net();
            }
        }
        return mInstance;
    }


    public void postLogin(String username, String password, Callback<ResponseDataBean<UserBean>> callback) {
        api.postLogin(username, password).enqueue(callback);
    }

    public void postLogout(Callback<ResponseDataBean> callback){
        api.postLogout().enqueue(callback);
    }

    public void postRegister(String username, String password,String repassword,
                             Callback<ResponseDataBean<UserBean>> callback) {
        api.postRegister(username, password,repassword).enqueue(callback);
    }



    public void postAddTodo ( TodoBean todo ,Callback<ResponseDataBean<TodoBean>> callback){
        api.postAddTodo(todo).enqueue(callback);
    }

    public void getGetTodo (int symbol , int flag , Callback<ResponseDataBean<List<TodoBean>>> callback){
        api.getGetTodo(symbol,flag).enqueue(callback);
    }

    public void postDeleteTodo(int id,Callback<ResponseDataBean> callback){
        api.postDeleteTodo(id).enqueue(callback);
    }

    public void postUpdateTodo(TodoBean todo , Callback<ResponseDataBean> callback){
        api.postUpdateTodo(todo).enqueue(callback);
    }

    public void postUpdateTodoStatus(int id , int status,Callback<ResponseDataBean> callback){
        api.postUpdateTodoStatus(id,status).enqueue(callback);
    }


    public static String encodeCookie(List<String> cookies) {
        final StringBuilder sb = new StringBuilder();
        final Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            set.addAll(Arrays.asList(cookie.split(";")));
        }
        for (String s : set) {
            sb.append(s).append(";");
        }
        if (sb.length() > 0)
            sb.delete(sb.length() - 1, sb.length());
        return sb.toString().trim();
    }




}
