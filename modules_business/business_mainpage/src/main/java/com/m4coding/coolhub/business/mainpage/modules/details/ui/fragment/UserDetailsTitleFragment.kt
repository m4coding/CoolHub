package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gyf.barlibrary.ImmersionBar
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.UserDetailsEvent
import kotlinx.android.synthetic.main.business_mainpage_fragment_user_details_title.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class UserDetailsTitleFragment : BaseFragment(), View.OnClickListener {

    private var mUserBean: UserBean? = null

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_user_details_title, container, false)
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun initData() {

        ImmersionBar.setTitleBar(mActivity, business_mainpage_user_details_title_back_cl)

        business_mainpage_user_details_title_back_cl.visibility = View.INVISIBLE
        business_mainpage_user_details_title_iv_back.setOnClickListener(this)
    }

    private fun updateData() {
        ImageLoader.begin()
                .displayImage(mUserBean?.avatarUrl, business_mainpage_user_details_title_iv_avatar)
        business_mainpage_user_details_title_tv_name.text = mUserBean?.login
    }


    fun setScrollChanges(currentY: Int, maxY: Int) {
        if (currentY >= maxY / 4) {
            business_mainpage_user_details_title_back_cl.visibility = View.VISIBLE
            business_mainpage_user_details_title_back_cl.alpha = currentY * 1.0f / (maxY / 4.0f * 3.0f)
        } else {
            if (business_mainpage_user_details_title_back_cl.visibility  == View.VISIBLE) {
                business_mainpage_user_details_title_back_cl.visibility = View.INVISIBLE
            }
        }
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
            R.id.business_mainpage_user_details_title_iv_back -> {
                mActivity.finish()
            }
        }
    }
}