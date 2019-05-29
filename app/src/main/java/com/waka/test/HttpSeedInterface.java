package com.waka.test;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpSeedInterface {

    @FormUrlEncoded
    @POST("n/tokenShare/info/byText")
    Call<ResponseBody> tokenShareInfo(@Field("shareText") String str);
}
