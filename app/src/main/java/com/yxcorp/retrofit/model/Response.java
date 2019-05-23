package com.yxcorp.retrofit.model;

import okhttp3.FormBody;

public class Response<T> {
    public final long a;
    public final long b;
    private final T c;
    private final int d;
    private final String e;
    private final String f;
    private FormBody g;

    public Response(T t, int i, String str, String str2, long j, long j2) {
        this.c = t;
        this.d = i;
        this.e = str;
        this.f = str2;
        this.a = j;
        this.b = j2;
    }

    /* Access modifiers changed, original: final */
    public final void Response(FormBody xVar) {
        this.g = xVar;
    }

    public final T Response() {
        return this.c;
    }

    public final FormBody b() {
        return this.g;
    }

    public final int c() {
        return this.d;
    }

    public final String d() {
        return this.e;
    }

    public final String e() {
        return this.f;
    }

}
