package com.m4coding.coolhub.business.mainpage.modules.main.contract

import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.business.base.list.IListView
import com.m4coding.coolhub.base.mvp.IModel
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class DynamicContract {
    interface View : IListView
    interface Model : IModel {
        fun getFocusDynamic(page: Int, map: Map<String, Any>): Observable<List<EventBean>>
        fun getOwnDynamic(page: Int, map: Map<String, Any>): Observable<List<EventBean>>
    }
}