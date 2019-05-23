package com.smile.gifshow.annotation.provider.v2;

import com.smile.gifshow.annotation.Reference;

public abstract class Accessor<T> implements Reference<T> {
    public Reference<T> c;

    static class NotImplementedException extends RuntimeException {
        NotImplementedException() {
        }
    }

    public void set(T t) {
        throw new NotImplementedException();
    }

    public final Reference<T> a() {
        return this.c;
    }
}