package com.m4coding.coolhub.business.mainpage.modules.setting.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.setting.model.bean.SettingBean

class SettingAdapter(list: List<DataListBean>?) : BaseListAdapter<DataListBean, BaseViewHolder>(list) {

    init {
        multiTypeDelegate = object : MultiTypeDelegate<DataListBean>() {
            override fun getItemType(entity: DataListBean): Int {
                //根据你的实体类来判断布局类型
                return entity.type
            }
        }


        multiTypeDelegate.registerItemType(DataListBean.TYPE_SETTING_TITLE_VALUE, R.layout.business_mainpage_item_setting)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_SETTING_LOGIN_OUT, R.layout.business_mainpage_item_setting_login_out)
    }

    override fun convert(helper: BaseViewHolder?, item: DataListBean?) {
        val settingBean = item?.value as SettingBean
        when(item.type) {
            DataListBean.TYPE_SETTING_TITLE_VALUE -> {
                helper?.setVisible(R.id.setting_iv_go, false)
                helper?.setVisible(R.id.setting_iv_icon, false)
                helper?.setVisible(R.id.setting_tv_sub_title, false)
                helper?.setText(R.id.setting_tv_title, settingBean.title)
                helper?.setText(R.id.setting_tv_value, settingBean.value)
            }
            DataListBean.TYPE_SETTING_LOGIN_OUT -> {

            }
        }
    }
}