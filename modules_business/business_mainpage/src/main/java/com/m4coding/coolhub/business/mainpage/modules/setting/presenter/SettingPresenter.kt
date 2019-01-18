package com.m4coding.coolhub.business.mainpage.modules.setting.presenter

import android.annotation.SuppressLint
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.business.base.list.IListView
import com.m4coding.coolhub.business.base.utils.KotlinUtils
import com.m4coding.coolhub.business.base.utils.PhoneInformationManager
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.setting.contract.SettingContract
import com.m4coding.coolhub.business.mainpage.modules.setting.model.bean.SettingBean
import com.m4coding.coolhub.business.mainpage.modules.setting.ui.adapter.SettingAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class SettingPresenter(rootView: SettingContract.View) :
        BasePresenter<SettingContract.Model,SettingContract.View>(rootView) {

    private var mAdapter: SettingAdapter? = null
    private var mList: ArrayList<DataListBean> = ArrayList()

    init {
        mAdapter = SettingAdapter(mList)
    }

   @SuppressLint("CheckResult")
   fun update() {
       Observable.create<List<DataListBean>> {
           val list = ArrayList<DataListBean>()
           list.add(DataListBean(DataListBean.TYPE_SETTING_TITLE_VALUE, SettingBean(KotlinUtils.getString(R.string.business_mainpage_app_version),
                   null, PhoneInformationManager.getInstance().getValue(PhoneInformationManager.KEY_APP_VERSION_NAME), SettingBean.TYPE_APP_VERSION)))

           val loginOutBean = SettingBean()
           loginOutBean.type = SettingBean.TYPE_LOGIN_OUT
           list.add(DataListBean(DataListBean.TYPE_SETTING_LOGIN_OUT, loginOutBean))

           it.onNext(list)
           it.onComplete()
       }.doOnSubscribe{
           mRootView.startLoad(IListView.LOAD_TYPE_START_LOAD)
       }
       .subscribeOn(Schedulers.newThread())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe({
           mList.clear()
           mList.addAll(it)
           mAdapter?.setNewData(mList)
           mRootView.successLoad(IListView.LOAD_TYPE_SUCCESS_LOAD)
       }, {
           mRootView.errorLoad(IListView.LOAD_TYPE_ERROR_LOAD)
           it.printStackTrace()
       })
    }

    fun getAdapter(): SettingAdapter? {
        return mAdapter
    }

}