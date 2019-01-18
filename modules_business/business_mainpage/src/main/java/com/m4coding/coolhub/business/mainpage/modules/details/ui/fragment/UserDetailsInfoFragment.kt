package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.business.base.utils.BusinessFormatUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.DataListActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryListActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.UserDetailsEvent
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_CONTENT
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_LOADING
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import kotlinx.android.synthetic.main.business_mainpage_fragment_user_details_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author mochangsheng
 * @description 用户详情页-资料Fragment
 */
class UserDetailsInfoFragment : BaseFragment(), View.OnClickListener, HeaderScrollHelper.ScrollableContainer {

    private var mUserBean:UserBean? = null

    companion object {
        private const val KEY_USER = "key_user"
        fun newInstance(userBean: UserBean): UserDetailsInfoFragment {
            val fragment = UserDetailsInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_USER, userBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_user_details_info, container, false)
    }

    override fun initData() {

    }

    override fun initLazyData() {

        business_mainpage_user_details_info_state_view.viewState = VIEW_STATE_LOADING

        business_mainpage_user_details_info_ll_repositories.setOnClickListener(this)
        business_mainpage_user_details_info_ll_followers.setOnClickListener(this)
        business_mainpage_user_details_info_ll_following.setOnClickListener(this)

        mUserBean = arguments?.getParcelable(KEY_USER)


        updateData()
    }

    private fun updateData() {
        business_mainpage_user_details_info_tv_location.text = getString(
                R.string.business_mainpage_user_details_info_location, mUserBean?.location ?: getString(R.string.business_mainpage_mine_unknow))

        business_mainpage_user_details_info_tv_email.text = getString(
                R.string.business_mainpage_user_details_info_email, mUserBean?.email ?: getString(R.string.business_mainpage_mine_unknow))

        business_mainpage_user_details_info_tv_repositories.text =
                BusinessFormatUtils.formatNumber(mUserBean?.publicRepos ?: 0)

        business_mainpage_user_details_info_tv_followers.text =
                BusinessFormatUtils.formatNumber(mUserBean?.followers ?: 0)

        business_mainpage_user_details_info_tv_following.text =
                BusinessFormatUtils.formatNumber(mUserBean?.following ?: 0)

        business_mainpage_user_details_info_state_view.viewState = VIEW_STATE_CONTENT
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(userDetailsEvent: UserDetailsEvent) {
        when(userDetailsEvent.type) {
            UserDetailsEvent.TYPE_UPDATE -> {
                if (mActivity == AppManager.getInstance().currentActivity) {
                    mUserBean = userDetailsEvent.value as UserBean?
                    updateData()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.business_mainpage_user_details_info_ll_repositories -> {
                context?.let { it1 ->
                    mUserBean?.login?.let {
                        RepositoryListActivity.newInstance(it1, it)
                    }
                }
            }
            R.id.business_mainpage_user_details_info_ll_followers -> {
                context?.let { it1 ->
                    mUserBean?.login?.let {
                        DataListActivity.newInstance(it1, it, "", DataListBean.TYPE_FOLLOWERS)
                    }
                }
            }
            R.id.business_mainpage_user_details_info_ll_following-> {
                context?.let { it1 ->
                    mUserBean?.login?.let {
                        DataListActivity.newInstance(it1, it, "",DataListBean.TYPE_FOLLOWING)
                    }
                }
            }
        }
    }

    override fun getScrollableView(): View {
        return business_mainpage_user_details_info_scroll_view
    }
}