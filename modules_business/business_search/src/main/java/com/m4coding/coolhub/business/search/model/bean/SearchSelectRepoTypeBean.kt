package com.m4coding.coolhub.business.search.model.bean

import android.text.TextUtils
import com.m4coding.coolhub.base.utils.SPUtils


class SearchSelectRepoTypeBean(sort: String, order: String, showName: String) {


    companion object {

        private const val SP_KEY_TYPE = "repo_search_sort_option"

        fun createList(): List<SearchSelectRepoTypeBean> {
            val list = ArrayList<SearchSelectRepoTypeBean>()
            list.add(SearchSelectRepoTypeBean("best match", "", "Best match"))
            list.add(SearchSelectRepoTypeBean("stars", "desc", "Most stars"))
            list.add(SearchSelectRepoTypeBean("stars", "asc", "Fewest stars"))
            list.add(SearchSelectRepoTypeBean("forks", "desc", "Most forks"))
            list.add(SearchSelectRepoTypeBean("forks", "asc", "Fewest forks"))
            list.add(SearchSelectRepoTypeBean("updated", "desc", "Recently updated"))
            list.add(SearchSelectRepoTypeBean("updated", "asc", "Least recently updated"))

            return list
        }

        private fun getDefault(): SearchSelectRepoTypeBean {
            return SearchSelectRepoTypeBean("best match", "", "Best match")
        }

        /**
         * 保存
         */
        fun save(selectRepoTypeBean: SearchSelectRepoTypeBean) {
            SPUtils.getInstance().put(SearchSelectRepoTypeBean.SP_KEY_TYPE, selectRepoTypeBean.sort + ","
                    + selectRepoTypeBean.order + "," + selectRepoTypeBean.showName)
        }


        /**
         * 从本地获取
         */
        fun get(): SearchSelectRepoTypeBean {
            val value = SPUtils.getInstance().getString(SearchSelectRepoTypeBean.SP_KEY_TYPE)
            return if (TextUtils.isEmpty(value)) {
                getDefault()
            } else {
                val strs = value.split(",")
                if (strs.size > 2) {
                    SearchSelectRepoTypeBean(strs[0], strs[1], strs[2])
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
                "stars,desc" -> {
                    "Most stars"
                }
                "stars,asc" -> {
                    "Fewest stars"
                }
                "forks,desc" -> {
                    "Most forks"
                }
                "forks,asc" -> {
                    "Least forks"
                }
                "updated,desc" -> {
                    "Recently updated"
                }
                "updated,asc" -> {
                    "Least recently updated"
                }
                else -> {
                    "Best match"
                }
            }
        }
    }

    var sort: String? = sort //搜索类型  -> best match, stars, forks, updated
    var order: String? = order //顺序 -> desc, asc
    var showName: String? = showName //显示名称
}