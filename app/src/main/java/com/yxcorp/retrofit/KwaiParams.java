package com.yxcorp.retrofit;

import android.support.annotation.Nullable;
import android.text.TextUtils;
//import com.eclipsesource.v8.Platform;
//import com.kuaishou.android.feed.b;
//import com.tencent.connect.common.Constants;
//import com.yxcorp.gateway.pay.params.GatewayPayConstant;
import com.yxcorp.retrofit.RetrofitConfig;
import com.yxcorp.retrofit.i;
import com.yxcorp.retrofit.j;
import com.yxcorp.retrofit.k;
//import com.yxcorp.utility.r;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/* compiled from: KwaiParams */
public class KwaiParams extends k{
    public final void a(Map<String, String> map) {
        super.a(map);
        //((b) com.yxcorp.utility.singleton.a.a(b.class)).a((Map) map);
    }

    public final void a(Request request, Map<String, String> map, Map<String, String> map2, String str) {
        RetrofitConfig.b createRetrofitConfigSignature = j.a().c().createRetrofitConfigSignature();
        if (createRetrofitConfigSignature != null) {
            try {
                Object obj;
//                boolean equalsIgnoreCase = Constants.HTTP_GET.equalsIgnoreCase(request.method());
//                RequestBody body = request.body();
//                if (equalsIgnoreCase || (body instanceof t) || (body instanceof FormBody) || body == null || body.contentLength() == 0) {
//                    obj = null;
//                } else {
//                    obj = 1;
//                }
                obj = 1;
                if (obj != null) {
                    RequestBody body2 = request.body();
                    okio.Buffer cVar = new okio.Buffer();
                    if (body2 != null) {
                        body2.writeTo(cVar);
                    }
                    byte[] q = cVar.readByteArray();
                    HashMap hashMap = new HashMap();
                    i c = j.a().c();
                    map.put(GatewayPayConstant.KEY_OS, Platform.ANDROID);
                    map.put("client_key", c.c());
                    a(map);
                    hashMap.put("bodyMd5", r.b(q));
                    String m = c.m();
                    String userTokenClientSalt = c.getUserTokenClientSalt();
                    boolean n = c.n();
                    if (n) {
                        hashMap.put("token", m);
                    }
                    for (Entry entry : map.entrySet()) {
                        hashMap.put(entry.getKey(), entry.getValue());
                    }
                    m = (String) createRetrofitConfigSignature.computeSignature(request, hashMap, new HashMap()).second;
                    map.put("sig2", m);
                    if (n && !TextUtils.isEmpty(userTokenClientSalt)) {
                        map.put("__NStokensig", (String) createRetrofitConfigSignature.computeTokenSignature(m, userTokenClientSalt).second);
                        return;
                    }
                    return;
                }
                super.a(request, map, map2, str);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}