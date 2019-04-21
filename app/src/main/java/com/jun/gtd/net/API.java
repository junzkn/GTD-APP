package com.jun.gtd.net;

import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.bean.UserBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {


    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Call<ResponseDataBean<UserBean>> postLogin(@Field("name") String username, @Field("password") String password);


    /**
     * 登出
     * @return
     */
    @POST("user/logout")
    Call<ResponseDataBean> postLogout();


    /**
     * 注册
     * @param username
     * @param password
     * @param repassword
     * @return
     */
    @POST("user/register")
    @FormUrlEncoded
    Call<ResponseDataBean<UserBean>> postRegister(@Field("name") String username, @Field("password") String password,
                                                  @Field(value="repassword") String repassword);


    /**
     * 新增Todo
     * @param todoBean
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("todo/addTodo")
    Call<ResponseDataBean<TodoBean>>postAddTodo(@Body TodoBean todoBean) ;


    /**
     * 获取Todo
     * @param symbol
     * @param flag
     * @return
     */
    @GET("todo/getTodo")
    Call<ResponseDataBean<List<TodoBean>>>getGetTodo(@Query("symbol") int symbol, @Query("flag") int flag) ;


    /**
     * 删除Todo
     * @param id
     * @return
     */
    @POST("todo/deleteTodo")
    @FormUrlEncoded
    Call<ResponseDataBean> postDeleteTodo(@Field("id")int id);


    /**
     * 更新Todo
     * @param todoBean
     * @return
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("todo/updateTodo")
    Call<ResponseDataBean<TodoBean>> postUpdateTodo(@Body TodoBean todoBean) ;


    /**
     * 更新Todo状态
     * @param id
     * @param status
     * @return
     */
    @POST("todo/updateTodoStatus")
    @FormUrlEncoded
    Call<ResponseDataBean<TodoBean>> postUpdateTodoStatus(@Field("id")int id,@Field("status")int status) ;






}
