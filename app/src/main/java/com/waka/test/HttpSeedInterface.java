package com.waka.test;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface HttpSeedInterface {

    @FormUrlEncoded
    @POST("n/tokenShare/info/byText")
    Call<ResponseBody> tokenShareInfo(@QueryMap Map<String,String> map, @Body TokenRequest bean);
}
