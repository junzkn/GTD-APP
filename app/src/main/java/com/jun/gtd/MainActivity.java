package com.jun.gtd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.view.View;
import android.widget.TextView;

import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.net.Net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView) ;
    }

    public void click(View view) {
        switch (view.getId()){
            case R.id.login :
                Net.getInstance().postLogin("2525","2525", new Callback<ResponseDataBean<UserBean>>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean<UserBean>> call, Response<ResponseDataBean<UserBean>> response) {
                        textView.setText(response.body().getData().toString());
                    }
                    @Override
                    public void onFailure(Call<ResponseDataBean<UserBean>> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.logout :
                Net.getInstance().postLogout( new Callback<ResponseDataBean>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean> call, Response<ResponseDataBean> response) {
                        textView.setText(response.body().toString());
                    }
                    @Override
                    public void onFailure(Call<ResponseDataBean> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.register :
                Net.getInstance().postRegister("2525","8023","8888", new Callback<ResponseDataBean<UserBean>>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean<UserBean>> call, Response<ResponseDataBean<UserBean>> response) {
                        textView.setText(response.body().getErrorMsg());
                    }
                    @Override
                    public void onFailure(Call<ResponseDataBean<UserBean>> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.islogin :
                Net.getInstance().getIsLogin(new Callback<ResponseDataBean>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean> call, Response<ResponseDataBean> response) {
                        textView.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseDataBean> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.addTodo :

                break ;
            case R.id.getTodo :
                break ;
            case R.id.deleteTodo :
                break ;
            case R.id.updateTodo :
                break ;
            case R.id.updateTodoStatus :
                break ;

        }
    }
}
