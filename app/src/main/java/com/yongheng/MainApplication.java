package com.yongheng;

import android.app.Application;
import android.content.Context;

import com.yongheng.application.MyClient;

import java.util.Stack;

public class MainApplication extends Application {

  private static Stack<Context> appContext = new Stack<>();

  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this.getApplicationContext();
    MyClient.getMyClient().init(this);

  }


  public static Context getContext(){
    return context;
  }


  public static void addContext(Context context){
    appContext.push(context);
  }

  public static void removeContext(){
    appContext.pop();
  }

  public static Context getTopContext(){
    return (Context) appContext.peek();
  }


}
