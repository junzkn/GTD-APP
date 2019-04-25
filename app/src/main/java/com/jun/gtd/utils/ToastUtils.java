package com.jun.gtd.utils;

import android.view.View;
import android.widget.Toast;

import com.jun.gtd.R;
import com.jun.gtd.base.App;

public class ToastUtils {

    public static Toast toast ;

    public static void info (String msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public static void info (int msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(App.getInstance().getString(msg));
        toast.show();
    }
    public static void success (String msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public static void success (int msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(App.getInstance().getString(msg));
        toast.show();
    }
    public static void error (String msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public static void error (int msg){
        if(toast==null) initToast();
        //
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(App.getInstance().getString(msg));
        toast.show();
    }


    public static void cancel (){
        if(toast!=null){
            toast.cancel();
        }
    }

    private static void initToast(){
        toast = Toast.makeText(App.getInstance(),"toast",Toast.LENGTH_SHORT) ;
//        toast = new Toast(App.getInstance()) ;
//        toast.setView(View.inflate(App.getInstance(), R.layout.xtoast_normal,null));
    }



}
