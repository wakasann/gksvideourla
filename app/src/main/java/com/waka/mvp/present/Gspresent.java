package com.waka.mvp.present;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;

import com.waka.mvp.view.SimpleView;
import com.waka.mvp.model.QPhotoModel;
import com.waka.mvp.error.ExceptionHandle;
import com.waka.mvp.disposable.SubscriptionManager;


//(Presenter与View交互是通过接口),里面放一个接口
public class Gspresent extends BasePresenter<SimpleView> {
    private QPhotoModel qPhotoModel;

    public Gspresent() {
        qPhotoModel = new QPhotoModel();
    }

    //Presenter与View交互
    public void tokenShareInfo(String shareText) {

        qPhotoModel.tokenShareInfo(shareText, new Observer<List<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {
                SubscriptionManager.getInstance().add(d);
            }

            @Override
            public void onNext(List<ResponseBody> responseBodies) {
                //继承关系，可以使用泛型里面的属性。
                view.onSuccess(responseBodies);
            }

            @Override
            public void onError(Throwable e) {
                view.onFail(e);
            }

            @Override
            public void onComplete() {
                view.OnCompleted();
            }


        });
    }

}
