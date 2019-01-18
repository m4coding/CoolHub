package com.m4coding.coolhub.business.mainpage.modules.details.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import com.m4coding.coolhub.api.datasource.bean.BlobBean
import com.m4coding.coolhub.api.datasource.bean.BranchBean
import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.activity.BaseToolbarActivity
import com.m4coding.coolhub.business.base.utils.KotlinUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.contract.CodeReaderContract
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.CodeReaderPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.SourceEditor
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.TypeJudgeUtils
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.*
import kotlinx.android.synthetic.main.business_mainpage_activity_code_reader.*

/**
 * 代码查看
 */
class CodeReaderActivity : BaseToolbarActivity<CodeReaderPresenter>(), CodeReaderContract.View {

    private var mSourceEditor: SourceEditor? = null
    private var mFileBean: FileBean? = null
    private var mRepoBean: RepoBean? = null
    private var mBranchBean: BranchBean? = null

    companion object {

        private const val KEY_FILE_CONTENT = "key_file_bean"
        private const val KEY_REPO = "key_repo"
        private const val KEY_BRANCH = "key_branch"

        fun newInstance(context: Context, repoBean: RepoBean, fileBean: FileBean, branchBean: BranchBean?) {
            val intent = Intent()
            intent.component = ComponentName(context, CodeReaderActivity::class.java)
            intent.putExtra(KEY_FILE_CONTENT, fileBean)
            intent.putExtra(KEY_REPO, repoBean)
            intent.putExtra(KEY_BRANCH, branchBean)
            context.startActivity(intent)
        }
    }

    override fun initView() {

        mFileBean = intent?.getParcelableExtra(KEY_FILE_CONTENT)
        mRepoBean = intent?.getParcelableExtra(KEY_REPO)
        mBranchBean = intent?.getParcelableExtra(KEY_BRANCH)

        setContentView(R.layout.business_mainpage_activity_code_reader, mFileBean?.name)
    }

    override fun initData() {

        business_mainpage_code_reader_state_view.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(R.id.business_base_cl_error)
                ?.setOnClickListener{
                    loadCode()
                }


        mPresenter = CodeReaderPresenter(this)

        mSourceEditor = SourceEditor(business_mainpage_code_reader_webview)


        loadCode()
    }

    private fun loadCode() {
        if (TypeJudgeUtils.isMd(mFileBean?.name)) {
            mSourceEditor?.setMarkdownViewer(true)
            KotlinUtils.whenAllNotNull(mRepoBean, mFileBean) {
                mPresenter.loadMd(it[0] as RepoBean, it[1] as FileBean, mBranchBean)
            }
        } else {
            mSourceEditor?.setMarkdownViewer(false)
            mPresenter.loadContent(mRepoBean?.owner?.login ?: "",
                    mRepoBean?.name ?: "", mFileBean?.sha ?: "")
        }
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

    override fun onGetBlobSuccess(blobBean: BlobBean) {
        business_mainpage_code_reader_state_view.viewState = VIEW_STATE_CONTENT
        mSourceEditor?.setSource(mFileBean?.name, blobBean)
    }

    override fun onGetMdSuccess(content: String) {
        business_mainpage_code_reader_state_view.viewState = VIEW_STATE_CONTENT
        mSourceEditor?.setSource(mFileBean?.name, content, false)
    }

    override fun onGetBlobFail() {
        business_mainpage_code_reader_state_view.viewState = VIEW_STATE_ERROR

    }

    override fun onGetBlobBegin() {
        business_mainpage_code_reader_state_view.viewState = VIEW_STATE_LOADING
    }

}