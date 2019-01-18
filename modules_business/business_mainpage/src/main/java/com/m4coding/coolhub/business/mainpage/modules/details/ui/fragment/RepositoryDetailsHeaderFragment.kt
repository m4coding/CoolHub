package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.business.mainpage.R
import kotlinx.android.synthetic.main.business_mainpage_fragment_repository_details_header.*

/**
 * @author mochangsheng
 * @description 仓库头部HeaderFragment
 */
class RepositoryDetailsHeaderFragment : BaseFragment(){

    private var mRepoBean: RepoBean? = null

    companion object {

        private const val  KEY_REPO = "key_repo"

        fun newInstance(repoBean: RepoBean): RepositoryDetailsHeaderFragment {
            val fragment = RepositoryDetailsHeaderFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_header, container, false)
    }

    override fun initData() {
        mRepoBean = arguments?.getParcelable(KEY_REPO)
        updateData()
    }

    private fun updateData() {
        business_mainpage_repository_details_header_tv_name.text = mRepoBean?.name
        if (TextUtils.isEmpty(mRepoBean?.description)) {
            business_mainpage_repository_details_header_tv_introduce.visibility = View.GONE
        } else {
            business_mainpage_repository_details_header_tv_introduce.visibility = View.VISIBLE
            business_mainpage_repository_details_header_tv_introduce.text = mRepoBean?.description
        }
    }
}