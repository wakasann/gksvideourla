package com.yxcorp.retrofit;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
//import com.eclipsesource.v8.Platform;
//import com.meizu.cloud.pushsdk.constants.PushConstants;
//import com.tencent.stat.DeviceInfo;
//import com.yxcorp.gateway.pay.params.GatewayPayConstant;
import com.yxcorp.retrofit.RetrofitConfig.a;
import com.yxcorp.retrofit.RetrofitConfig.b;
//import com.yxcorp.utility.ak;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import okhttp3.Request;

/* compiled from: RetrofitParams */
public class k implements a {
    @android.support.annotation.Nullable
    public Map<String, String> a() {
        HashMap hashMap = new HashMap();
        hashMap.put("User-Agent", j.a().c().b());
        hashMap.put("Accept-Language", j.a().c().o());
        hashMap.put("X-REQUESTID", String.valueOf(SystemClock.elapsedRealtime()));
        hashMap.put("Connection", "keep-alive");
        HashMap hashMap2 = new HashMap();
        String m = j.a().c().m();
        if (!TextUtils.isEmpty(m)) {
            hashMap2.put("token", m);
        }
        String c = c(hashMap2);
        if (!TextUtils.isEmpty(c)) {
            hashMap.put("Cookie", c);
        }
        return hashMap;
    }

    private static String c(Map<String, String> map) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            stringBuilder.append((String) entry.getKey()).append('=').append((String) entry.getValue()).append(';');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public void a(Request request, Map<String, String> map, Map<String, String> map2, String str) {
        b createRetrofitConfigSignature = j.a().c().createRetrofitConfigSignature();
        if (createRetrofitConfigSignature != null) {
            Pair computeSignature = createRetrofitConfigSignature.computeSignature(request, map, map2);
            if (!(TextUtils.isEmpty((CharSequence) computeSignature.first) || TextUtils.isEmpty((CharSequence) computeSignature.second))) {
                map2.put(computeSignature.first, computeSignature.second);
            }
            if (!TextUtils.isEmpty(str)) {
                Pair computeTokenSignature = createRetrofitConfigSignature.computeTokenSignature((String) computeSignature.second, str);
                if (!TextUtils.isEmpty((CharSequence) computeTokenSignature.first) && !TextUtils.isEmpty((CharSequence) computeTokenSignature.second)) {
                    map2.put(computeTokenSignature.first, computeTokenSignature.second);
                }
            }
        }
    }

    public void a(@android.support.annotation.a Map<String, String> map) {
        i c = j.a().c();
        map.put("ud", c.l());
        map.put(DeviceInfo.TAG_VERSION, c.getVersion());
        map.put(GatewayPayConstant.KEY_SYS, c.e());
        map.put(GatewayPayConstant.KEY_CHANNEL, c.h());
        map.put("oc", c.getOriginChannel());
        map.put(GatewayPayConstant.KEY_DID, c.g());
        map.put(GatewayPayConstant.KEY_MOD, c.f());
        map.put(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, c.getApp());
        map.put(GatewayPayConstant.KEY_COUNTRYCODE, c.i());
        map.put(GatewayPayConstant.KEY_APPVER, c.d());
        map.put(GatewayPayConstant.KEY_LAT, c.j());
        map.put(GatewayPayConstant.KEY_LON, c.k());
        map.put("hotfix_ver", c.getPatchVersion());
        map.put("language", j.a().c().o());
        map.put("kpn", "KUAISHOU");
        map.put("kpf", "ANDROID_PHONE");
        map.put(GatewayPayConstant.KEY_NET, ak.c(j.a().b()));
    }

    public void b(@android.support.annotation.a Map<String, String> map) {
        map.put(GatewayPayConstant.KEY_OS, Platform.ANDROID);
        map.put("client_key", j.a().c().c());
        i c = j.a().c();
        String m = c.m();
        String userTokenClientSalt = c.getUserTokenClientSalt();
        if (c.n()) {
            map.put("token", m);
            map.put("client_salt", userTokenClientSalt);
        }
    }
}