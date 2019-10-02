package com.waka.mvp.present;

import android.util.Log;
import android.widget.Toast;

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
                if(videoHomeTab == null){
                    Log.e("Api","请求失败");
                }else{
                    view.onSuccess(videoHomeTab);
                }

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
