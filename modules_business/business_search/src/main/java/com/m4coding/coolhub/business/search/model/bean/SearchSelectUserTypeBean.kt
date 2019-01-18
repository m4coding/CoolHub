package com.m4coding.coolhub.business.search.model.bean

import android.text.TextUtils
import com.m4coding.coolhub.base.utils.SPUtils


class SearchSelectUserTypeBean(sort: String, order: String, showName: String) {


    companion object {

        const val SP_KEY_TYPE = "user_search_sort_option"

        fun createList(): List<SearchSelectUserTypeBean> {
            val list = ArrayList<SearchSelectUserTypeBean>()
            list.add(SearchSelectUserTypeBean("best match", "", getShowName("best match", "")))
            list.add(SearchSelectUserTypeBean("followers", "desc", getShowName("followers", "desc")))
            list.add(SearchSelectUserTypeBean("followers", "asc", getShowName("followers", "asc")))
            list.add(SearchSelectUserTypeBean("joined", "desc", getShowName("joined", "desc")))
            list.add(SearchSelectUserTypeBean("joined", "asc", getShowName("joined", "asc")))
            list.add(SearchSelectUserTypeBean("repositories", "desc", getShowName("repositories", "desc")))
            list.add(SearchSelectUserTypeBean("repositories", "asc", getShowName("repositories", "asc")))

            return list
        }

        private fun getDefault(): SearchSelectUserTypeBean {
            return SearchSelectUserTypeBean("best match", "", "Best match")
        }

        /**
         * 保存
         */
        fun save(selectUserTypeBean: SearchSelectUserTypeBean) {
            SPUtils.getInstance().put(SP_KEY_TYPE, selectUserTypeBean.sort + ","
                    + selectUserTypeBean.order + "," + selectUserTypeBean.showName)
        }


        /**
         * 从本地获取
         */
        fun get(): SearchSelectUserTypeBean {
            val value = SPUtils.getInstance().getString(SP_KEY_TYPE)
            return if (TextUtils.isEmpty(value)) {
                getDefault()
            } else {
                val strs = value.split(",")
                if (strs.size > 2) {
                    SearchSelectUserTypeBean(strs[0], strs[1], strs[2])
                } else {
                    getDefault()
                }
            }
        }

        /**
         * 根据sort和order获取showName
         */
        fun getShowName(sort: String, order: String): String {
            val temp = "$sort,$order"
            return when(temp) {
                "followers,desc" -> {
                    "Most followers"
                }
                "followers,asc" -> {
                    "Fewest followers"
                }
                "joined,desc" -> {
                    "Most Recently joined"
                }
                "joined,asc" -> {
                    "Least recently joined"
                }
                "repositories,desc" -> {
                    "Most repositories"
                }
                "repositories,asc" -> {
                    "Fewest repositories"
                }
                else -> {
                    "Best match"
                }
            }
        }
    }

    var sort: String? = sort //搜索类型  -> best match, followers, repositories, joined
    var order: String? = order //顺序 -> desc, asc
    var showName: String? = showName //显示名称
}