package com.waka.mvp.view;

import com.waka.mvp.error.ExceptionHandle;

public interface SimpleView {
    void onSuccess(Object object);

    void onFail(Throwable t);

    void OnCompleted();

}
