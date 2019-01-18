package com.m4coding.coolhub.business.mainpage.modules.main.presenter

import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.main.contract.DynamicContract
import com.m4coding.coolhub.business.mainpage.modules.main.model.DynamicModel
import com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter.DynamicAdapter
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class DynamicPresenter(rootView: DynamicContract.View) : BaseListContentPresenter<EventBean, DynamicContract.Model, DynamicContract.View>(rootView) {

    companion object {
        //用户自己的动态
        const val DYNAMIC_OWN = "dynamic_own"
        //关注者的动态
        const val DYNAMIC_OTHERS_FOCUS = "dynamic_others_focus"
    }

    init {
        mModel = DynamicModel()
    }

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<EventBean>> {
        return when(map[ParamsMapKey.DYNAMIC_TYPE]) {
            DYNAMIC_OWN -> {
                mModel.getOwnDynamic(page, map)
            }
            DYNAMIC_OTHERS_FOCUS -> {
                mModel.getFocusDynamic(page, map)
            } else  -> {
                //返回一个空的处理
                Observable.empty<List<EventBean>>()
            }
        }
    }

    override fun initAdapter() {
        mAdapter = DynamicAdapter(mList)
    }

}