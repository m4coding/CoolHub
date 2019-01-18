package com.m4coding.coolhub.business.search.ui.fragment

import android.annotation.SuppressLint
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.search.misc.SearchDBManager
import com.m4coding.coolhub.business.search.misc.SearchEvent
import com.m4coding.coolhub.business.search.model.bean.SearchBean
import com.m4coding.coolhub.business.search.model.bean.SearchHistoryBean
import com.m4coding.coolhub.business.search.ui.activity.SearchActivity
import com.m4coding.coolhub.business_search.R
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.business_search_fragment_search_default.*
import org.greenrobot.eventbus.EventBus

/**
 * 搜索默认fragment界面
 */
class SearchDefaultFragment : BaseFragment(), View.OnClickListener {

    private var mHistoryList: List<SearchHistoryBean>? = null

    companion object {
        const val TAG = "SearchDefaultFragment"
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_search_fragment_search_default, container, false)
    }

    override fun initData() {
        business_search_default_iv_history_delete.setOnClickListener(this)

        update()
    }

    @SuppressLint("CheckResult")
    private fun update() {
        if (business_search_fb_layout_history.childCount > 0) {
            business_search_fb_layout_history.removeAllViews()
        }
        Observable.create<SearchHistoryBean> {
            val list = SearchDBManager.getInstance().searchHistory
            mHistoryList = list
            for (bean in list) {
                it.onNext(bean)
            }
            it.onComplete()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this@SearchDefaultFragment, FragmentEvent.DESTROY))
                .subscribe({
                    val textView: TextView = layoutInflater.inflate(R.layout.business_search_item_history,
                            business_search_fb_layout_history, false) as TextView
                    textView.text = it.content
                    textView.setOnClickListener(this@SearchDefaultFragment)
                    business_search_fb_layout_history.addView(textView)
                }, {
                    it.printStackTrace()
                    business_search_default_group.visibility = View.GONE
                },{
                    if (mHistoryList?.isNotEmpty() == true) {
                        business_search_default_group.visibility = View.VISIBLE
                    } else {
                        business_search_default_group.visibility = View.GONE
                    }
                })
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            update()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mHistoryList = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.business_search_default_iv_history_delete -> {
                val dialog = NormalDialog(context)
                dialog.btnNum(2)
                        .content(getString(R.string.business_search_delete_history))
                        .isTitleShow(false)
                        .setOnBtnClickL(OnBtnClickL{
                            dialog.dismiss()
                        }, OnBtnClickL{
                            dialog.dismiss()
                            SearchDBManager.getInstance().clearSearchHistory()
                            if (business_search_fb_layout_history.childCount > 0) {
                                business_search_fb_layout_history.removeAllViews()
                            }
                            business_search_default_group.visibility = View.GONE
                        })
                dialog.show()
            }
            R.id.business_search_default_tv_history_content -> {
                context?.let {
                    val activity = it as SearchActivity
                    activity.search((v as TextView).text.toString(), true)
                }
            }
        }
    }

}