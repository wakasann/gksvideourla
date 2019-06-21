package com.waka.mvp.present;

//<里面传入的参数必须是BaseView的子类或者本身>
//这个类的作用就是获取到当前的View
public class BasePresenter<V> {
    public V view;

    //加载View,建立连接
    public void addView(V v) {
        this.view = v;
    }

    //断开连接
    public void detattch() {
        if (view != null) {
            view = null;
        }
    }
}
