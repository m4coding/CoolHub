package com.m4coding.coolhub.business.mainpage.modules.mine.contract

import android.content.Context
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.mvp.IView

/**
 * @author mochangsheng
 * @description
 */
class MineContract {
    interface View : IView {
        fun onSuccessMineInfo(userBean: UserBean)
        fun onFailMineInfo()
        fun onLoadingMineInfo()
        fun getOwnContext(): Context?
    }
    interface Model : IModel
}