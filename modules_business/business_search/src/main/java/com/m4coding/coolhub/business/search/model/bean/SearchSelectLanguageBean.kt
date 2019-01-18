package com.m4coding.coolhub.business.search.model.bean

import android.text.TextUtils
import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.base.utils.SPUtils
import com.m4coding.coolhub.business_search.R

/**
 * @author mochangsheng
 * @description
 */
class SearchSelectLanguageBean(name: String, language: String) {

    companion object {
        private const val SP_KEY_LANGUAGE = "sp_key_language"
        private const val ALL = "all"

        fun createList(): List<SearchSelectLanguageBean> {
            val list = ArrayList<SearchSelectLanguageBean>()
            list.add(SearchSelectLanguageBean(
                    BaseApplication.getContext().getString(R.string.business_search_all_language), ALL))
            list.add(SearchSelectLanguageBean("Java", "Java"))
            list.add(SearchSelectLanguageBean("Kotlin", "Kotlin"))
            list.add(SearchSelectLanguageBean("Objective-C", "Objective-C"))
            list.add(SearchSelectLanguageBean("Swift", "Swift"))
            list.add(SearchSelectLanguageBean("JavaScript", "JavaScript"))
            list.add(SearchSelectLanguageBean("PHP", "PHP"))
            list.add(SearchSelectLanguageBean("Go", "Go"))
            list.add(SearchSelectLanguageBean("C++", "C++"))
            list.add(SearchSelectLanguageBean("C", "C"))
            list.add(SearchSelectLanguageBean("HTML", "HTML"))
            list.add(SearchSelectLanguageBean("CSS", "CSS"))

            return list
        }

        fun getNameByLanguage(language: String): String {
            return when(language) {
                ALL -> {
                    BaseApplication.getContext().getString(R.string.business_search_all_language)
                }
                else -> {
                    language
                }
            }
        }


        /**
         * 保存
         */
        fun save(language: String) {
            SPUtils.getInstance().put(SP_KEY_LANGUAGE, language)
        }


        /**
         * 从本地获取
         */
        fun get(): String {
            val value = SPUtils.getInstance().getString(SearchSelectLanguageBean.SP_KEY_LANGUAGE)
            return if (TextUtils.isEmpty(value)) {
                ALL
            } else {
               value
            }
        }
    }

    var name: String? = name
    var language: String? = language
}