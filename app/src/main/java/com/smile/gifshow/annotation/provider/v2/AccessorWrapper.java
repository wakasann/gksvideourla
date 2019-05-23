package com.smile.gifshow.annotation.provider.v2;

import com.google.common.base.Optional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AccessorWrapper {
    public final Map<String, Accessor> a = new HashMap();
    public final Map<Class, Accessor> b = new HashMap();


    public final <T> void a(Class cls, T t) {
        Accessor accessor = (Accessor) this.b.get(cls);
        if (accessor != null) {
            accessor.set(t);
        }
    }


    public final void b() {
        this.a.clear();
        this.b.clear();
    }

    public final <T> Accessor<T> b(String str) {
        return (Accessor) this.a.get(str);
    }
}