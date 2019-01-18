package com.m4coding.coolhub.business.base.activity;

import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.m4coding.business_base.R;
import com.m4coding.coolhub.base.base.BasePresenterActivity;
import com.m4coding.coolhub.base.mvp.BasePresenter;


/**
 * @author mochangsheng
 * @description toolbar相关使用Activity
 */

public abstract class BaseToolbarActivity<P extends BasePresenter> extends BasePresenterActivity<P> {

    public Toolbar mToolbar;

    protected TextView mCenterTitle;

    /**
     * 设置布局，动态添加ToolBar，如果不需要，则用回默认的setContentView
     *
     * @param layoutResId 布局id
     * @param titleId     标题id
     */
    public void setContentView(@LayoutRes int layoutResId, int titleId) {
        String title = null;
        if (titleId > 0) {
            //自定义的一些操作
            try {
                title = getString(titleId);
            } catch (Resources.NotFoundException ignored) {
            }
        }
        setContentView(layoutResId, title);
    }

    /**
     * @param layoutResId 布局id
     * @param title       标题id
     */
    public void setContentView(@LayoutRes int layoutResId, String title) {
        setContentView(layoutResId, -1, title);
    }

    /**
     *
     * @param layoutResId
     * @param toolbarResId  toolbar布局
     * @param title
     */
    public void setContentView(@LayoutRes int layoutResId, @LayoutRes int toolbarResId, String title) {

        ToolBarUtil toolBarHelper = new ToolBarUtil(this, layoutResId, toolbarResId);
        mToolbar = toolBarHelper.getToolBar();
        setSupportActionBar(mToolbar);

        setContentView(toolBarHelper.getContentView());

        //自定义的一些操作
        if (!TextUtils.isEmpty(title))
            super.setTitle(title);
        onCreateCustomToolBar(mToolbar);
        //设置左右两边的留白为0,即不留空，toolbar全占满
        mToolbar.setContentInsetsRelative(0, 0);
        mToolbar.showOverflowMenu();
    }


    /**
     * 定义Toolbar的事件
     * 如果需要自定义Toolbar布局和功能的话
     * 复写这个方法，然后按照下面的代码模式进行复写
     *
     * @param toolbar ToolBar
     */
    protected void onCreateCustomToolBar(Toolbar toolbar) {
        //添加入自定义布局到ToolBar
        getLayoutInflater().inflate(R.layout.item_common_toolbar, toolbar);
        ImageView leftView = (ImageView) findViewById(R.id.iv_left_toolbar);
        ImageView rightView = (ImageView) findViewById(R.id.iv_right_toolbar_2);
        TextView rightTextView = (TextView) findViewById(R.id.tv_right_toolbar);
        mCenterTitle = (TextView) findViewById(R.id.tv_title_toolbar);
        mCenterTitle.setText(getTitle());
        getTitleCenterView(mCenterTitle);
        getTitleLeftView(leftView);
        getTitleRightView(rightView);
        getTitleRightTextView(rightTextView);
    }

    protected void getTitleCenterView(TextView textView) {
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mCenterTitle != null)
            mCenterTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mCenterTitle != null)
            mCenterTitle.setText(titleId);
    }

    /**
     * 返回左边的对象
     *
     * @param leftView 返回按钮
     */
    protected void getTitleLeftView(ImageView leftView) {
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 返回右边的view对象
     * 如果没使用onCreateCustomToolBar而直接复写该方法的话会出现空指针
     * 如果调用出现空指针异常的话，说明调用有误，要避免
     *
     * @param rightView 右边的view对象
     */
    protected void getTitleRightView(ImageView rightView) {
    }

    protected void getTitleRightTextView(TextView rightView) {
    }

}
