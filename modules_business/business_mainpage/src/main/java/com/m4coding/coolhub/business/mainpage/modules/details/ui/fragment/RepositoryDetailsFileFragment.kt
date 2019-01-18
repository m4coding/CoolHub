package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.BranchBean
import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.base.IFragmentKeyListener
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.base.utils.log.MLog
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.base.utils.KotlinUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryFileContract
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.RepositoryFilesPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.CodeReaderActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.RepoDetailsEvent
import com.m4coding.coolhub.widgets.MultiStateView.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryDetailsFileFragment : BaseListContentFragment<RepositoryFilesPresenter>(),
        RepositoryFileContract.View, IFragmentKeyListener {

    private var mIndexView: TextView? = null
    private var mPath = "" //要加载的路径
    private var mNowPath = "" //显示的目录路径
    private var mRepoBean: RepoBean? = null
    private var mCurrentBranch: BranchBean? = null


    companion object {
        private const val KEY_REPO = "key_repo"
        private const val KEY_BRANCH = "key_branch"

        fun newInstance(repoBean: RepoBean, branchBean: BranchBean?): RepositoryDetailsFileFragment {
            val fragment = RepositoryDetailsFileFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            bundle.putParcelable(KEY_BRANCH, branchBean)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun initListPresenter() {

        mRepoBean = arguments?.getParcelable(KEY_REPO) as RepoBean
        mCurrentBranch = arguments?.getParcelable(KEY_BRANCH)

        mHashMap = HashMap()
        mHashMap?.put(ParamsMapKey.REPO, mRepoBean?.name ?: "")
        mHashMap?.put(ParamsMapKey.USER_NAME, mRepoBean?.owner?.login ?: "")
        mHashMap?.put(ParamsMapKey.BRANCH, mCurrentBranch?.name ?: "")
        mHashMap?.put(ParamsMapKey.PATH, "/")
        mPath = "/"

        mPresenter = RepositoryFilesPresenter(this)

        mPresenter.getAdapter()?.setOnItemClickListener { baseQuickAdapter: BaseQuickAdapter<Any,
                BaseViewHolder>, view: View, position: Int ->
            if (position >= 0 && position < mPresenter.getList()?.size ?: 0) {
                val tree = mPresenter.getList()?.get(position)
                if (tree?.type.equals(FileBean.TYPE_DIR)) {//进入目录
                    mHashMap?.let {
                        mHashMap?.put(ParamsMapKey.PATH, getPath(true, tree?.path?:"") ?: "")
                        mRefreshView?.autoRefresh()
                    }
                } else {
                    //查看源码

                    KotlinUtils.whenAllNotNull(context, mRepoBean, tree) {
                        CodeReaderActivity.newInstance(it[0] as Context,
                                it[1] as RepoBean, it[2] as FileBean, mCurrentBranch)
                    }

                }
            }
        }
    }

    override fun startLoad(type: Int) {
        super.startLoad(type)
    }

    override fun successLoad(type: Int) {
        super.successLoad(type)

        mNowPath = mPath

        setIndex()
    }

    override fun errorLoad(type: Int) {
        super.errorLoad(type)

        //加载失败及恢复原来的路径
        mPath = mNowPath
    }

    override fun firstUpdate() {

        mPresenter.getAdapter()?.setEnableLoadMore(false)
        mPresenter.getAdapter()?.setOnLoadMoreListener(null, mRecyclerView)

        mHashMap?.let {
            mPresenter.refreshData(false, it)
        }
    }

    /**
     * 通过分支来刷新文件
     */
    private fun updateByBranch() {
        mHashMap?.let {
            it[ParamsMapKey.BRANCH] = mCurrentBranch?.name ?: ""
            mPresenter.refreshData(false, it)
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_files, container, false)
    }

    override fun initData() {
        mMultiStateView = mRootView.findViewById(R.id.business_mainpage_repository_files_state_view)
        mRefreshView = mRootView.findViewById(R.id.business_mainpage_repository_files_sm_refresh_l)
        mRecyclerView = mRootView.findViewById(R.id.business_mainpage_repository_files_recyclerview)
        mIndexView = mRootView.findViewById(R.id.business_mainpage_repository_files_index_text)
        //要实现点击效果，这个是必须要设置的
        mIndexView?.movementMethod = LinkMovementMethod.getInstance();
    }

    override fun onDestroy() {
        super.onDestroy()

        mRepoBean = null
    }

    /**
     * 获取路径
     * @param isForward 是否进入目录
     * @param path 路径
     * @return
     */
    private fun getPath(isForward: Boolean, path: String?): String? {
        mPath = if (isForward) { //前进
            "/$path/"
        } else {//后退
            var lastIndex = mPath.lastIndexOf("/")
            var nowIndex = -1
            for (i in lastIndex - 1 downTo 0) {
                if ("/" == mPath.substring(i, i + 1)) {
                    nowIndex = i
                    break
                }
            }

            mPath.substring(0, nowIndex + 1)
        }

        MLog.d("getPath==$mPath")
        return if (mPath.isEmpty()) {
            "/"
        } else {
            mPath = mPath.replace("//", "/")
            mPath
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (mMultiStateView?.viewState == VIEW_STATE_ERROR) {
                return false
            }

            val canBack = mPath.trim() != "/"
            return if (canBack && mMultiStateView?.viewState == VIEW_STATE_CONTENT
                    && mRefreshView?.isRefreshing == false) {
                mHashMap?.let {
                    mHashMap?.put(ParamsMapKey.PATH, getPath(false, null) ?: "")
                    mRefreshView?.autoRefresh()
                }
                canBack
            } else {
                if (canBack) {
                    showMessage(getString(R.string.loading))
                }
                canBack
            }
        } else false
    }

    private fun setIndex() {
        if (mNowPath.isEmpty()) {
            mIndexView?.visibility = View.GONE
            return
        }

        mIndexView?.visibility = View.VISIBLE
        val ps = PathString(mNowPath.replace("/", " / "))
        mIndexView?.text = ps
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(repoDetailsEvent: RepoDetailsEvent) {
        when(repoDetailsEvent.type) {
            RepoDetailsEvent.TYPE_UPDATE_BRANCH -> {
                if (AppManager.getInstance().currentActivity == mActivity) {
                    val branchBean = repoDetailsEvent.value as BranchBean?
                    mCurrentBranch = branchBean
                    updateByBranch()
                }
            }
        }
    }

    private inner class PathString internal constructor(text: String) : SpannableString(text) {
        init {
            setup(text)
        }

        private fun setup(text: String) {
            var text = text
            var start = 0
            if (text.replace(" ", "").endsWith("/")) {
                text = text.substring(0, text.length - 2)
            }
            var chatIndex = text.indexOf(File.separatorChar)
            val pathStart = 0// 路径String位置，text最开始为工程名称，不包含在Path内，所以标注开始位置用于截取Path
            var pathIndex = 1// 标注层级，用于获取缓存
            while (chatIndex >= 0) {
                val path = if (chatIndex > pathStart) text.substring(pathStart, chatIndex) else " "

                setSpan(Clickable(path, pathIndex), start, chatIndex + 1, Spanned
                        .SPAN_EXCLUSIVE_EXCLUSIVE)

                pathIndex++
                start = chatIndex + 1
                chatIndex = text.indexOf(File.separatorChar, start)
            }
        }

        internal inner class Clickable(private val path: String, private val index: Int) : ClickableSpan() {

            override fun onClick(widget: View) {
                mHashMap?.let {
                    MLog.d("clickPath==" + this.path)
                    val tmpPath = path.replace(" ", "") + "/"
                    mHashMap?.put(ParamsMapKey.PATH, tmpPath)

                    mRefreshView?.autoRefresh()

                    mPath = tmpPath
                }
            }

            override fun updateDrawState(ds: TextPaint) {
                //super.updateDrawState(ds)
                context?.let {
                    ds.color = ContextCompat.getColor(it, R.color.accent)
                }
                ds.isUnderlineText = false//去掉下划线
                ds.clearShadowLayer()
            }
        }
    }

}