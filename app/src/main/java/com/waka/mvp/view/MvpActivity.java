package com.waka.mvp.view;

import com.waka.mvp.disposable.SubscriptionManager;
import com.waka.mvp.present.BasePresenter;
import android.os.Bundle;

public abstract class MvpActivity<p extends BasePresenter> {
    public p presener;


    public void initBefore(Bundle savedInstanceState) {
        presener = initPresener();
        //把所有继承此类的Activity都绑定到这里了，这样View就和Present联系起来了。
        presener.addView(this);
    }

    protected abstract p initPresener();


    protected void onDestroy() {
        presener.detattch();
        //View消除时取消订阅关系
        SubscriptionManager.getInstance().cancelall();
    }
}
