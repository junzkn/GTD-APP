package com.jun.gtd.utils;

import android.widget.Toast;

import com.jun.gtd.base.App;

public class ToastUtils {

    public static Toast toast ;

    public static void info (String msg){
        if(toast==null){
            toast = new Toast(App.getInstance());
        }
        //TODO
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public static void success (String msg){
        if(toast==null){
            toast = new Toast(App.getInstance());
        }
        //TODO
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
    public static void error (String msg){
        if(toast==null){
            toast = new Toast(App.getInstance());
        }
        //TODO
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }


    public static void cancel (){
        if(toast!=null){
            toast.cancel();
        }
    }


}
