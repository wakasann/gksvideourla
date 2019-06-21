package com.waka.mvp.present;

import com.waka.mvp.disposable.SubscriptionManager;
import com.waka.mvp.error.ExceptionHandle;
import com.waka.mvp.model.Observer;
import com.waka.mvp.model.VideoHomeTab;
import com.waka.mvp.view.SimpleView;

import io.reactivex.disposables.Disposable;

public class VideoHomePresenter extends BasePresenter<SimpleView> {
    private VideoHomeTab videoHomeTab;

    public VideoHomePresenter(){ videoHomeTab = new VideoHomeTab();}

    public void shareMessage(){
        videoHomeTab.messageInfo(new Observer<VideoHomeTab>() {
            @Override
            public void OnSuccess(VideoHomeTab videoHomeTab) {
                view.onSuccess(videoHomeTab);
            }

            @Override
            public void OnFail(ExceptionHandle.ResponeThrowable e) {
                view.onFail(e);
            }

            @Override
            public void OnCompleted() {
                view.OnCompleted();
            }

            @Override
            public void OnDisposable(Disposable d) {
                SubscriptionManager.getInstance().add(d);
            }
        });
    }


}
