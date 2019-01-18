package com.m4coding.coolhub.business.search.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.base.IFragmentKeyListener
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.search.misc.SearchDBManager
import com.m4coding.coolhub.business.search.misc.SearchEvent
import com.m4coding.coolhub.business.search.model.bean.SearchHistoryBean
import com.m4coding.coolhub.business.search.ui.activity.SearchActivity
import com.m4coding.coolhub.business.search.ui.adapter.SearchHitAdapter
import com.m4coding.coolhub.business_search.R
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.business_search_fragment_search_hint.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.ArrayList
import java.util.concurrent.TimeUnit


class SearchHintFragment : BaseFragment(), IFragmentKeyListener {

    private var mAdapter: SearchHitAdapter? = null
    private var mList: ArrayList<SearchHistoryBean> = ArrayList()

    companion object {
        const val TAG = "SearchHintFragment"
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_search_fragment_search_hint, container, false)
    }

    override fun initData() {
        business_search_hint_recycler_view.layoutManager = LinearLayoutManager(context)
        mAdapter = SearchHitAdapter(mList)
        business_search_hint_recycler_view.adapter = mAdapter

        mAdapter?.setOnItemClickListener{ _, _, i ->
            context?.let {
                val bean = mList[i]
                val activity = it as SearchActivity
                activity.search(bean.content, true)
            }
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(searchEvent: SearchEvent) {
        when(searchEvent.type) {
            SearchEvent.TYPE_INPUT -> {
                val message = searchEvent.value
                if (message is String) {
                    searchHistory(message)
                }
            }
        }
    }


    /**
     * 搜索历史
     */
    @SuppressLint("CheckResult")
    private fun searchHistory(message: String) {
        if (message.isEmpty()) {
            hide(true)
            return
        }

        Observable.just(message)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()) //输入关键字后timeout时间后才发射数据，起到防抖动的作用
                .subscribeOn(Schedulers.newThread())
                .switchMap {it -> //只发射最后一次请求的数据，避免多次请求冲突
                    if (TextUtils.isEmpty(it)) {
                        Observable.just(null) //空，即发送null
                    } else {
                        Observable.just(SearchDBManager.getInstance().searchHistory(it))
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this@SearchHintFragment, FragmentEvent.DESTROY))
                .subscribe({
                    mList.clear()
                    it?.let {
                        mList.addAll(it)
                    }
                    mAdapter?.setNewData(mList)
                }, {
                    it.printStackTrace()
                    hide(true)
                }, {
                    if (mList.isEmpty()) {
                        hide(true)
                    } else {
                        hide(false)
                    }
                })
    }



    //isHide为true 隐藏   为false 显示
    private fun hide(isHide: Boolean) {
        context?.let {
            val activity = context as SearchActivity
            if (isHide) {
                activity.hideFragment(true, SearchActivity.FRAGMENT_TYPE_HINT)
            } else {
                activity.hideFragment(false, SearchActivity.FRAGMENT_TYPE_HINT)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (!isHidden) {
                context?.let {
                    val activity = context as SearchActivity
                    activity.hideFragment(true, SearchActivity.FRAGMENT_TYPE_HINT)
                    true
                } ?: false
            } else {
                false
            }
        } else false
    }
}