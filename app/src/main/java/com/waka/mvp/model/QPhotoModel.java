package com.waka.mvp.model;
import com.waka.mvp.retrofit.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class QPhotoModel {

    public void tokenShareInfo(String shareText, Observer<List<ResponseBody>> observer) {
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("shareText",shareText);
        Observable<List<ResponseBody>> gSxin = RetrofitManager.getSingleton().Apiservice().tokenShareInfo(queryMap);
        gSxin.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }
}
