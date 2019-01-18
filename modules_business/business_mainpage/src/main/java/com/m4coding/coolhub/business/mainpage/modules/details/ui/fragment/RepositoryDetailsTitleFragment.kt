package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.flyco.dialog.listener.OnOperItemClickL
import com.gyf.barlibrary.ImmersionBar
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.utils.MiscUtils
import com.m4coding.coolhub.business.base.utils.DialogUtils
import com.m4coding.coolhub.business.base.widgets.CustomSheetDialog
import com.m4coding.coolhub.business.mainpage.R
import kotlinx.android.synthetic.main.business_mainpage_fragment_repository_details_title.*

/**
 * @author mochangsheng
 * @description 仓库详情标题Fragment
 */
class RepositoryDetailsTitleFragment  : BaseFragment(), View.OnClickListener {

    private var mRepoBean: RepoBean? = null
    private var mSheetDialog: CustomSheetDialog? = null

    companion object {

        private const val KEY_REPO = "key_repo"

        fun newInstance(repoBean: RepoBean): RepositoryDetailsTitleFragment {
            val fragment = RepositoryDetailsTitleFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(): RepositoryDetailsTitleFragment {
            return RepositoryDetailsTitleFragment()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_title, container, false)
    }

    override fun initData() {

        //调整title，适配status bar
        ImmersionBar.setTitleBar(mActivity, business_mainpage_repository_details_title_back_cl)

        business_mainpage_repository_details_title_back_cl.visibility = View.INVISIBLE

        business_mainpage_repository_details_title_iv_back.setOnClickListener(this)
        business_mainpage_repository_details_title_iv_more.setOnClickListener(this)

        mRepoBean = arguments?.getParcelable(KEY_REPO)
        business_mainpage_repository_details_title_tv_name.text = mRepoBean?.name
    }

    override fun onDestroy() {
        super.onDestroy()

        mRepoBean = null
    }

    /**
     * 更新标题
     */
    fun updateData(repoBean: RepoBean) {
        mRepoBean = repoBean
        business_mainpage_repository_details_title_tv_name.text = mRepoBean?.name
    }


    fun setScrollChanges(currentY: Int, maxY: Int) {
        if (currentY >= maxY / 4) {
            business_mainpage_repository_details_title_back_cl.visibility = View.VISIBLE
            business_mainpage_repository_details_title_back_cl.alpha = currentY * 1.0f / (maxY / 4.0f * 3.0f)
        } else {
            if (business_mainpage_repository_details_title_back_cl.visibility  == View.VISIBLE) {
                business_mainpage_repository_details_title_back_cl.visibility = View.INVISIBLE
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.business_mainpage_repository_details_title_iv_back -> {
                mActivity.finish()
            }
            R.id.business_mainpage_repository_details_title_iv_more -> {
                if (null == mSheetDialog) {
                    val strings = arrayOf(getString(R.string.share), getString(R.string.copy_url),
                            getString(R.string.open_in_browser))
                    mSheetDialog = DialogUtils.getSheetDialog(context, strings) { parent, view, position, id ->
                        when(strings[position]) {
                            getString(R.string.share) -> {
                                context?.let { it1 ->
                                    mRepoBean?.htmlUrl?.let {
                                        MiscUtils.shareText(it1, it)
                                    }
                                }
                            }
                            getString(R.string.copy_url) -> {
                                context?.let {
                                    MiscUtils.toCopy(it, mRepoBean?.htmlUrl)
                                }
                            }
                            getString(R.string.open_in_browser) -> {
                                context?.let {
                                    MiscUtils.openBrowser(it, mRepoBean?.htmlUrl)
                                }
                            }
                        }
                    }
                }

                mSheetDialog?.show()
            }
        }
    }
}