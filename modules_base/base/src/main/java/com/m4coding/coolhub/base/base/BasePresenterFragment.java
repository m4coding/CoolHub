package com.m4coding.coolhub.base.base;


import com.m4coding.coolhub.base.mvp.BasePresenter;

/**
 * @author mochangsheng
 * @description Presenter Fragment
 */
public abstract class BasePresenterFragment<P extends BasePresenter> extends BaseFragment {

    protected P mPresenter;

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }

}
