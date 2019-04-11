package com.jun.gtd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jun.gtd.bean.ResponseDataBean;
import com.jun.gtd.bean.TodoBean;
import com.jun.gtd.bean.UserBean;
import com.jun.gtd.net.Net;

import java.util.List;

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
                        if(response.body().getErrorCode()==0)
                            textView.setText(response.body().toString());
                        else
                            textView.setText(response.body().getErrorMsg());
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
                        if(response.body().getErrorCode()==0)
                            textView.setText(response.body().toString());
                        else
                            textView.setText(response.body().getErrorMsg());
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
                        if(response.body().getErrorCode()==0)
                            textView.setText(response.body().toString());
                        else
                            textView.setText(response.body().getErrorMsg());
                    }
                    @Override
                    public void onFailure(Call<ResponseDataBean<UserBean>> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.islogin :
                break ;
            case R.id.addTodo :
                final TodoBean todoBean = new TodoBean();
                todoBean.setTitle("515");
                todoBean.setContent("515");
                todoBean.setUserid(17);
                Net.getInstance().postAddTodo(todoBean, new Callback<ResponseDataBean<TodoBean>>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean<TodoBean>> call, Response<ResponseDataBean<TodoBean>> response) {
                        if(response.body().getErrorCode()==0)
                            textView.setText(response.body().toString());
                        else
                            textView.setText(response.body().getErrorMsg());
                    }
                    @Override
                    public void onFailure(Call<ResponseDataBean<TodoBean>> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                }) ;
                break ;
            case R.id.getTodo :
                Net.getInstance().getGetTodo(TodoBean.GET_ALL, 1, new Callback<ResponseDataBean<List<TodoBean>>>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean<List<TodoBean>>> call, Response<ResponseDataBean<List<TodoBean>>> response) {
                        if(response.body().getErrorCode()==0) {
                            final List<TodoBean> data = response.body().getData();
                            for(final TodoBean todo : data){
                                Log.e("cd",todo.toString());
                            }
                        }

                        else
                            textView.setText(response.body().getErrorMsg());
                    }

                    @Override
                    public void onFailure(Call<ResponseDataBean<List<TodoBean>>> call, Throwable t) {
                        textView.setText("ERROR");
                    }
                });
                break ;
            case R.id.deleteTodo :
                Net.getInstance().postDeleteTodo(16, new Callback<ResponseDataBean>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean> call, Response<ResponseDataBean> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseDataBean> call, Throwable t) {

                    }
                });
                break ;
            case R.id.updateTodo :
                TodoBean todo = new TodoBean() ;
                todo.setId(15);
                todo.setTitle("我爱第四版");
                todo.setUserid(17);

                break ;
            case R.id.updateTodoStatus :
                Net.getInstance().postUpdateTodoStatus(15, 3, new Callback<ResponseDataBean>() {
                    @Override
                    public void onResponse(Call<ResponseDataBean> call, Response<ResponseDataBean> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseDataBean> call, Throwable t) {

                    }
                });
                break ;

        }
    }
}
