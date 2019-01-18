package com.m4coding.coolhub.business.base.activity;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.m4coding.business_base.R;


/**
 * ToolBar工具类
 */
public class ToolBarUtil {

    private Activity mContext;

    private LinearLayout mContentView;

    private Toolbar mToolBar;

    private LayoutInflater mInflater;

    /**
     *
     * @param context
     * @param layoutId 要显示的layoutId
     */
    public ToolBarUtil(Activity context, int layoutId) {
        this(context, layoutId, -1);
    }


    /**
     *
     * @param context
     * @param layoutId  要显示的layoutId
     * @param toolbarLayoutId  toolbar的layoutId
     */
    public ToolBarUtil(Activity context, int layoutId, int toolbarLayoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        // 初始化整个内容
        initContentView();
        // 初始化toolbar
        initToolBar(toolbarLayoutId);
        // 初始化用户定义的布局
        initUserView(layoutId);
    }

    private void initContentView() {
        // 直接创建一个帧布局，作为视图容器的父容器
        mContentView = new LinearLayout(mContext);
        mContentView.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);
    }

    private void initToolBar(int toolbarLayoutId) {
        int resId = toolbarLayoutId <= 0 ? R.layout.item_base_toolbar : toolbarLayoutId;

        // 通过inflater获取toolbar的布局文件
        View barView = mInflater.inflate(resId, mContentView);
        mToolBar = (Toolbar) barView.findViewById(R.id.toolbar);
    }

    private void initUserView(int id) {
        View userView = mInflater.inflate(id, mContentView, false);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) userView.getLayoutParams();
        mContentView.addView(userView, params);
    }

    public LinearLayout getContentView() {
        return mContentView;
    }

    public Toolbar getToolBar() {
        return mToolBar;
    }
}
