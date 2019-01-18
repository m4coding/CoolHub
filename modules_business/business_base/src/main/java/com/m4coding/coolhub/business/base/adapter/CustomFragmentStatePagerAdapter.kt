package com.m4coding.coolhub.business.base.adapter

import android.support.v4.app.FragmentManager
import android.view.ViewGroup

/**
 * @author mochangsheng
 * @description  可以根据position获取Fragment的FragmentStatePagerAdapter
 */
abstract class CustomFragmentStatePagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var mOnHandleListener: OnHandleListener? = null


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val any = super.instantiateItem(container, position)
        mOnHandleListener?.onInstantiateItem(container, position)
        return any
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        mOnHandleListener?.onDestroyItem(container, position, `object`)
    }

    fun setOnHandleListener(onHandleListener: OnHandleListener) {
        mOnHandleListener = onHandleListener
    }

    /**
     * 相关回调监听
     */
    interface OnHandleListener {
        fun onInstantiateItem(container: ViewGroup, position: Int)
        fun onDestroyItem(container: ViewGroup, position: Int, `object`: Any)
    }

    abstract class SimpleAdapterHandleLister : OnHandleListener {
        override fun onDestroyItem(container: ViewGroup, position: Int, `object`: Any) {

        }

        override fun onInstantiateItem(container: ViewGroup, position: Int) {

        }
    }
}