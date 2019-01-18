package com.m4coding.coolhub.base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<M extends IModel, V extends IView> {
    protected final String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected V mRootView;


    public BasePresenter(V rootView) {
        this.mRootView = rootView;
        onStart();
    }


    public void onStart() {

    }


    public void onDestroy() {
        unDisposable();//解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//集中处理
    }

    protected void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity结束时取消所有正在执行的订阅
        }
    }
}
