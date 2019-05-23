package com.smile.gifshow.annotation;

public interface Reference<T> {
    T get();

    void set(T t);
}
