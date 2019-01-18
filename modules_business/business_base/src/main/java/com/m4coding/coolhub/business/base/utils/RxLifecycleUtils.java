package com.m4coding.coolhub.business.base.utils;

import android.support.annotation.NonNull;

import com.m4coding.coolhub.base.mvp.IView;
import com.m4coding.coolhub.business.base.widgets.RxDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * ================================================
 * 使用此类操作 RxLifecycle 的特性
 * <p>
 * Created by JessYan on 26/08/2017 17:52
 * Contact with jess.yan.effort@gmail.com
 * Follow me on https://github.com/JessYanCoding
 * ================================================
 */

public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 绑定Activity / Fragment / Dialog的生命周期
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Object view, Object event) {

        if (view instanceof RxFragment) {
            return ((RxFragment)view).bindUntilEvent((FragmentEvent) event);
        } else if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity)view).bindUntilEvent((ActivityEvent) event);
        } else if (view instanceof RxDialog) {
            return ((RxDialog)view).bindUntilEvent((ActivityEvent) event);
        } else {
            throw new IllegalArgumentException("view isn't LifecycleProvider");
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycleWithActivity(@NonNull RxAppCompatActivity activity, Object event) {
        return activity.bindUntilEvent((ActivityEvent) event);
    }
}
