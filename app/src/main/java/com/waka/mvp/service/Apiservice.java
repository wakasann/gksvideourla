package com.waka.mvp.service;

import com.waka.mvp.model.VideoHomeTab;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface Apiservice {

    @FormUrlEncoded
    @POST("n/tokenShare/info/byText")
    Observable<List<ResponseBody>> tokenShareInfo(@QueryMap Map<String,String> map);

    @GET("videoHomeTab")
    Observable<VideoHomeTab> getHomeTabs();
}
