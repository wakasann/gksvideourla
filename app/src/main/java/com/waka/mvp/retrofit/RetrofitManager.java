package com.waka.mvp.retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.waka.mvp.service.Apiservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private volatile static RetrofitManager retrofitManager;
    private Retrofit retrofit;

    //无参的单利模式
    public static RetrofitManager getSingleton() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }


    //无参的构造方法
    private RetrofitManager() {
        initRetrofitManager();
    }

    //构造方法创建Retrofit实例
    private void initRetrofitManager() {
        retrofit = new Retrofit.Builder().baseUrl("https://api.apiopen.top" + "/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Apiservice Apiservice() {
        return retrofit.create(Apiservice.class);
    }
}
