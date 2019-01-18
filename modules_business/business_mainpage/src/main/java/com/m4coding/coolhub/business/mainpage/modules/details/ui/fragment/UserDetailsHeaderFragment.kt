package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.UserDetailsEvent
import kotlinx.android.synthetic.main.business_mainpage_fragment_user_details_header.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author mochangsheng
 * @description
 */
class UserDetailsHeaderFragment : BaseFragment(), View.OnClickListener {

    companion object {
        const val TAG = "UserDetailsHeaderFragment"
    }

    private var mUserBean: UserBean? = null

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_user_details_header, container, false)
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun initData() {

    }

    private fun updateData() {
        ImageLoader.begin().setImageOnDefault(R.color.text_gray)
                .displayImage(mUserBean?.avatarUrl, business_mainpage_user_details_header_iv_avatar)
        business_mainpage_user_details_header_tv_name.text = mUserBean?.login
        if (TextUtils.isEmpty(mUserBean?.bio)) {
            business_mainpage_user_details_header_tv_introduce.visibility = View.GONE
        } else {
            business_mainpage_user_details_header_tv_introduce.visibility = View.VISIBLE
            business_mainpage_user_details_header_tv_introduce.text = mUserBean?.bio
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(userDetailsEvent: UserDetailsEvent) {
        when(userDetailsEvent.type) {
            UserDetailsEvent.TYPE_UPDATE -> {
                if (AppManager.getInstance().currentActivity == mActivity) {
                    mUserBean = userDetailsEvent.value as UserBean?
                    updateData()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {

        }
    }
}