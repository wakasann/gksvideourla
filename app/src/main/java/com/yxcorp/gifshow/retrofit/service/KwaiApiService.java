package com.yxcorp.gifshow.retrofit.service;

import com.yxcorp.gifshow.model.PhotoInfoList;
import com.yxcorp.gifshow.model.response.PhotoResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface KwaiApiService {

    @POST("n/photo/info")
    Call<Response<PhotoResponse>> getPhotoInfos(@Field("photoInfos")PhotoInfoList photoInfoList);
}
