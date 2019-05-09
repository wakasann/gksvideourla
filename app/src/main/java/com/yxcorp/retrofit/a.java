package com.yxcorp.retrofit;

//import com.google.gson.e;
//import com.kwai.b.f;
//import com.yxcorp.retrofit.d.KwaiParams;
//import com.yxcorp.retrofit.e.d;
//import com.yxcorp.retrofit.model.b;
//import io.reactivex.l;
//import io.reactivex.t;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;
import okhttp3.r;
import okhttp3.u;

/* compiled from: BaseRetrofitConfig */
public abstract class a implements f {
    static int a;
    private static u c;
    private final t b;
    private final boolean d;

    public abstract r e();

    public a(t tVar, int i) {
        boolean z;
        this.b = tVar;
        a = i;
        if (a <= 0 || a > 10) {
            z = false;
        } else {
            z = true;
        }
        this.d = z;
    }

    public final t i() {
        return this.b;
    }

    public e a() {
        return new e();
    }

    public com.yxcorp.retrofit.f.a b() {
        return j.a().c().createRetrofitConfigParams();
    }

    public okhttp3.u.a a(int i) {
        okhttp3.u.a c = new okhttp3.u.a().a((long) i, TimeUnit.SECONDS).b((long) i, TimeUnit.SECONDS).c((long) i, TimeUnit.SECONDS);
        r e = e();
        if (e != null) {
            c.a(e);
        }
        okhttp3.o.a f = f();
        if (f != null) {
            if (f == null) {
                throw new NullPointerException("eventListenerFactory == null");
            }
            c.g = f;
        }
        c.a(new d()).a(new l()).a(new com.yxcorp.retrofit.d.d(b())).a(new com.yxcorp.retrofit.d.a()).a(new KwaiParams(b()));
        return c;
    }

    public okhttp3.o.a f() {
        return null;
    }

    public u c() {
        if (c == null) {
            c = a(15).a();
        }
        return c;
    }

    public retrofit2.a<Object> a(retrofit2.a<Object> aVar) {
        return new com.yxcorp.retrofit.b.a(new b(aVar));
    }

    public final l<?> b(l<?> lVar, retrofit2.a<Object> aVar, Annotation[] annotationArr) {
        l<?> a = a(lVar.observeOn(f.a).doOnComplete(com.yxcorp.retrofit.consumer.c.c).doOnError(com.yxcorp.retrofit.consumer.c.d).doOnNext(new com.yxcorp.retrofit.e.c()), aVar, annotationArr);
        if (this.d) {
            for (Annotation annotation : annotationArr) {
                if (annotation.annotationType() == com.yxcorp.retrofit.a.a.class) {
                    com.yxcorp.retrofit.a.a aVar2 = (com.yxcorp.retrofit.a.a) annotation;
                    return a.doOnSubscribe(new b(aVar)).retryWhen(new KwaiParams(this, aVar, aVar2.a(), aVar2.b()));
                }
            }
        }
        return a;
    }

    public l<?> a(l<?> lVar, retrofit2.a<Object> aVar, Annotation[] annotationArr) {
        return lVar;
    }

    static Exception a(Throwable th) {
        if (th instanceof Exception) {
            return (Exception) th;
        }
        return new Exception(th);
    }
}