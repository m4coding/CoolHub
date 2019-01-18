package com.m4coding.coolhub.business.mainpage.modules.main.model

import com.m4coding.coolhub.api.datasource.UserDataSource
import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.api.exception.ApiException
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.mainpage.modules.main.contract.DynamicContract
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class DynamicModel : DynamicContract.Model {
    /**
     * 获取自己的动态信息
     */
    override fun getOwnDynamic(page: Int, map: Map<String, Any>): Observable<List<EventBean>> {
        if (map[ParamsMapKey.USER_NAME] is String) {
            return UserDataSource.getEvents(map[ParamsMapKey.USER_NAME] as String, page)
        }

        return Observable.error(NetExceptionHelper.wrapException(Throwable("username is null")))
    }

    /**
     * 获取自己关注用户的动态信息
     */
    override fun getFocusDynamic(page: Int, map: Map<String, Any>): Observable<List<EventBean>> {
        if (map[ParamsMapKey.USER_NAME] is String) {
            return UserDataSource.getReceivedEvents(map[ParamsMapKey.USER_NAME] as String, page)
        }
        
        return Observable.error(NetExceptionHelper.wrapException(Throwable("username is null")))
    }

    override fun onDestroy() {

    }
}