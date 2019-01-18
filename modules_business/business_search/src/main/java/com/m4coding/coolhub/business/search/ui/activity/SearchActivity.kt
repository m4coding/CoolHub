package com.m4coding.coolhub.business.search.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.m4coding.coolhub.base.base.BasePresenterActivity
import com.m4coding.coolhub.base.base.IFragmentKeyListener
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.base.utils.ViewUtils
import com.m4coding.coolhub.base.utils.ViewUtils.handleSoftInput
import com.m4coding.coolhub.business.search.misc.SearchDBManager
import com.m4coding.coolhub.business.search.misc.SearchEvent
import com.m4coding.coolhub.business.search.model.bean.SearchBean
import com.m4coding.coolhub.business.search.model.bean.SearchHistoryBean
import com.m4coding.coolhub.business.search.presenter.SearchPresenter
import com.m4coding.coolhub.business.search.ui.fragment.SearchContentFragment
import com.m4coding.coolhub.business.search.ui.fragment.SearchDefaultFragment
import com.m4coding.coolhub.business.search.ui.fragment.SearchHintFragment
import com.m4coding.coolhub.business_search.R
import kotlinx.android.synthetic.main.business_search_activity_search.*
import org.greenrobot.eventbus.EventBus

class SearchActivity : BasePresenterActivity<SearchPresenter>(), View.OnClickListener {

    private lateinit var mDefaultFragment: SearchDefaultFragment
    private lateinit var mContentFragment: SearchContentFragment
    private lateinit var mHintFragment: SearchHintFragment
    private var mIsHistorySearch: Boolean = false


    companion object {
        const val FRAGMENT_TYPE_DEFAULT = "default"
        const val FRAGMENT_TYPE_CONTENT = "content"
        const val FRAGMENT_TYPE_HINT = "hint"

        fun newInstance(context: Context) {
            val intent = Intent()
            intent.component = ComponentName(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        setContentView(R.layout.business_search_activity_search)

        busniess_search_tv_search.setOnClickListener(this)

        business_search_iv_cancel.visibility = View.GONE
        business_search_iv_cancel.setOnClickListener(this)

        business_search_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s)) {
                    business_search_iv_cancel.visibility = View.VISIBLE
                } else {
                    business_search_iv_cancel.visibility = View.GONE
                    hideFragment(false, FRAGMENT_TYPE_DEFAULT)
                }

                if (!mIsHistorySearch) {
                    //发送输入事件
                    EventBus.getDefault().post(SearchEvent(SearchEvent.TYPE_INPUT, s.toString()))
                }
                //重置
                mIsHistorySearch = false
            }
        })

        business_search_et.setOnEditorActionListener { v, actionId, event ->
            var handle = false
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    search(business_search_et.text.toString())
                    handle = true
                }
            }
            handle
        }


        business_search_et.addOnLayoutChangeListener(object : View.OnLayoutChangeListener{
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                business_search_et.removeOnLayoutChangeListener(this)
                ViewUtils.handleSoftInput(business_search_et,true)
            }
        })
    }

    /**
     * str:搜索内容，isHistory：是否是历史记录搜索
     */
     fun search(str :String, isHistory: Boolean) {
        if (!str.trim().isEmpty()) {

            mIsHistorySearch = isHistory

            if (isHistory) {
                business_search_et.setText(str)
                //光标在最后显示
                business_search_et.setSelection(business_search_et.length())
            }

            //发送搜索事件
            val searchBean = SearchBean()
            searchBean.query = str.trim()
            EventBus.getDefault().post(SearchEvent(SearchEvent.TYPE_SEARCH, searchBean))
            ViewUtils.handleSoftInput(business_search_et,false)
            hideFragment(true, FRAGMENT_TYPE_DEFAULT)
            hideFragment(true, FRAGMENT_TYPE_HINT)

            val searchHistoryBean = SearchHistoryBean()
            searchHistoryBean.content = searchBean.query
            SearchDBManager.getInstance().insertSearchHistory(searchHistoryBean)
        } else {
            ToastUtils.showShort(R.string.business_search_input_empty)
        }
    }

    fun search(str :String) {
        search(str, false)
    }

    override fun initData() {

        mDefaultFragment = SearchDefaultFragment()
        mContentFragment = SearchContentFragment()
        mHintFragment = SearchHintFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.business_search_fl_container, mContentFragment, SearchContentFragment.TAG)
                .add(R.id.business_search_fl_container, mDefaultFragment, SearchDefaultFragment.TAG)
                .add(R.id.business_search_fl_container, mHintFragment, SearchHintFragment.TAG)
                .hide(mHintFragment).commit()
    }

    fun hideFragment(isHide: Boolean, type: String) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = when(type) {
            FRAGMENT_TYPE_DEFAULT -> {
                mDefaultFragment
            }
            FRAGMENT_TYPE_CONTENT -> {
                mContentFragment
            }
            FRAGMENT_TYPE_HINT -> {
                mHintFragment
            }
            else -> {
                null
            }
        }

        if (fragment != null) {
            if (isHide) {
                transaction.hide(fragment).commit()
            } else {
                transaction.show(fragment).commit()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.busniess_search_tv_search -> {
                search(business_search_et.text.toString())
            }
            R.id.business_search_iv_cancel -> {
                business_search_et.text = null
                ViewUtils.handleSoftInput(business_search_et, true)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = mHintFragment
        return if (!fragment.isHidden &&
                ((fragment as IFragmentKeyListener).onKeyDown(keyCode, event))) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}