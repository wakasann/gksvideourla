package com.yxcorp.retrofit;

import android.content.Context;
import retrofit2.Invocation;

/* compiled from: RetrofitManager */
public final class j {
    public i a;

    /* compiled from: RetrofitManager */
    private static final class RetrofitManagerA {
        private static final j a = new j();
    }

    j(byte b) {
        this();
    }

    private j() {
    }

    public static j a() {
        return RetrofitManagerA.a;
    }

    public final Context b() {
        return this.a.a();
    }

    public final i c() {
        return this.a;
    }

    public static l a(a aVar) {
        return h.a(aVar).a();
    }

    public static <T> T a(a aVar, Class<T> cls) {
        return a(aVar).a((Class) cls);
    }
}