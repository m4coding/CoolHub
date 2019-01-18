package com.m4coding.coolhub.base.base;

import com.m4coding.coolhub.base.mvp.BasePresenter;

/**
 * @author mochangsheng
 * @description Presenter Activity （子类一定要实现IView否则会出错）
 */
public abstract class BasePresenterActivity<P extends BasePresenter> extends BaseActivity {

    protected P mPresenter;

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
            mPresenter = null;
        }
    }
}
