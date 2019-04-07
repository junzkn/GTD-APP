package com.jun.gtd.net;

import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {


    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseDataBean<UserBean>> postLogin(@Field("name") String username, @Field("password") String password);


    @POST("user/logout")
    Call<ResponseDataBean> postLogout();

    @POST("user/register")
    @FormUrlEncoded
    Call<ResponseDataBean<UserBean>> postRegister(@Field("name") String username, @Field("password") String password,
                                                  @Field(value="repassword") String repassword);


    @GET("user/islogin")
    Call<ResponseDataBean> getIsLogin();



}
