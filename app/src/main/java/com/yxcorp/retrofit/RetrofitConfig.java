package com.yxcorp.retrofit;

import android.util.Pair;
import com.google.gson.internal.bind.util.*;

import java.lang.annotation.Annotation;
import java.util.Map;
import okhttp3.Request;
import okhttp3.Response;

/* compiled from: RetrofitConfig */
public interface RetrofitConfig {

    /* compiled from: RetrofitConfig */
    public interface a {
        @android.support.annotation.Nullable
        Map<String, String> a();

        void a(Map<String, String> map);

        void a(Request request, Map<String, String> map, Map<String, String> map2, String str);

        void b(Map<String, String> map);
    }

    /* compiled from: RetrofitConfig */
    public interface b {
        Pair<String, String> computeSignature(Request request, Map<String, String> map, Map<String, String> map2);

        Pair<String, String> computeTokenSignature(String str, String str2);
    }

    e a();

    retrofit2.Response<Object> a(retrofit2.Response<Object> aVar);

//    l<?> b(l<?> lVar, retrofit2.a<Object> aVar, Annotation[] annotationArr);

    Response c();

    String d();

//    t i();
}