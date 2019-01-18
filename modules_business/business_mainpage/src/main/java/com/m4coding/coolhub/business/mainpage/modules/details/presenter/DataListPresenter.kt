package com.m4coding.coolhub.business.mainpage.modules.details.presenter

import com.m4coding.coolhub.api.datasource.bean.IssueEventBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.contract.DataListContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.DataListModel
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.DataListAdapter
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DataListPresenter(view: DataListContract.View) :
        BaseListContentPresenter<DataListBean, DataListContract.Model, DataListContract.View>(view) {

    init {
        mModel = DataListModel()
    }

    override fun initAdapter() {
        mAdapter = DataListAdapter(mList)
    }

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<DataListBean>> {

        val type = (map[ParamsMapKey.LIST_TYPE] as String).toInt()
        val username = map[ParamsMapKey.USER_NAME]
        val reponame = map[ParamsMapKey.REPO]

        return when(type) {
            DataListBean.TYPE_FOLLOWERS -> {
                mModel.getFollowers(username as String, page).map {
                    initList(it, type)
                }
            }

            DataListBean.TYPE_FOLLOWING -> {
                mModel.getFollowing(username as String, page).map {
                    initList(it, type)
                }
            }

            DataListBean.TYPE_FORKERS -> {
                mModel.getForkers(username as String, reponame as String, page).map {
                    initList(it, type)
                }
            }

            DataListBean.TYPE_STARTERS -> {
                mModel.getStarers(username as String, reponame as String, page).map {
                    initList(it, type)
                }
            }

            DataListBean.TYPE_WATCHERS -> {
                mModel.getWatchers(username as String, reponame as String, page).map {
                    initList(it, type)
                }
            }
            DataListBean.TYPE_ISSUE_OPEN -> {
                mModel.getOpenIssue(username as String, reponame as String, page).map {
                    initList(it, type)
                }
            }
            DataListBean.TYPE_ISSUE_CLOSE -> {
                mModel.getCloseIssue(username as String, reponame as String, page).map {
                    initList(it, type)
                }
            }
            DataListBean.TYPE_ISSUE_TIME_LINE -> {
                val issueNumber = map[ParamsMapKey.ISSUE_NUMBER] as String
                mModel.getIssueTimeLine(username as String, reponame as String, issueNumber.toInt(), page).map {
                    val list = ArrayList<DataListBean>()
                    for (bean in it) {
                        if (bean.type == IssueEventBean.Type.commented) {
                            val dataListBean = DataListBean()
                            dataListBean.type = type
                            dataListBean.value = bean
                            list.add(dataListBean)
                        }
                    }

                    if (1 == mPage) {
                        val dataListBean = DataListBean()
                        dataListBean.type = type
                        dataListBean.value = mRootView.getSpecial()
                        list.add(0, dataListBean)
                    }
                    list
                }
            }
            else -> {
                return Observable.error(NetExceptionHelper.wrapException(Throwable("type is null")))
            }
        }
    }

    private fun initList(rawList: List<Any>, type: Int): ArrayList<DataListBean> {
        val list = ArrayList<DataListBean>()
        for (bean in rawList) {
            val dataListBean = DataListBean()
            dataListBean.type = type
            dataListBean.value = bean
            list.add(dataListBean)
        }
        return list
    }

}