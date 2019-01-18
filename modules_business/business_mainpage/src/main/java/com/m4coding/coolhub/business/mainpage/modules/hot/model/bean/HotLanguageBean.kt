package com.m4coding.coolhub.business.mainpage.modules.hot.model.bean

import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.business.mainpage.R

/**
 * @author mochangsheng
 * @description
 */
class HotLanguageBean(name: String, language: String) {

    companion object {
        const val ALL = "all"

        fun createList(): List<HotLanguageBean> {
            val list = ArrayList<HotLanguageBean>()
            list.add(HotLanguageBean(
                    BaseApplication.getContext().getString(R.string.business_mainpage_hot_all_language), ALL))
            list.add(HotLanguageBean("Java", "Java"))
            list.add(HotLanguageBean("Kotlin", "Kotlin"))
            list.add(HotLanguageBean("Objective-C", "Objective-C"))
            list.add(HotLanguageBean("Swift", "Swift"))
            list.add(HotLanguageBean("JavaScript", "JavaScript"))
            list.add(HotLanguageBean("PHP", "PHP"))
            list.add(HotLanguageBean("Go", "Go"))
            list.add(HotLanguageBean("C++", "C++"))
            list.add(HotLanguageBean("C", "C"))
            list.add(HotLanguageBean("HTML", "HTML"))
            list.add(HotLanguageBean("CSS", "CSS"))

            return list
        }

        fun getNameByLanguage(language: String): String {
            return when(language) {
                ALL -> {
                    BaseApplication.getContext().getString(R.string.business_mainpage_hot_all_language)
                }
                else -> {
                    language
                }
            }
        }
    }

    var name: String? = name
    var language: String? = language
}