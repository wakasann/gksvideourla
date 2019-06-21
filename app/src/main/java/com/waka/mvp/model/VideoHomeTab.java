package com.waka.mvp.model;

import com.waka.mvp.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class VideoHomeTab{

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }

    public void messageInfo( Observer<VideoHomeTab> observer){
        Observable<VideoHomeTab> myMessage = RetrofitManager.getSingleton().Apiservice().getHomeTabs();
        myMessage.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}



