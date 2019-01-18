package com.m4coding.coolhub.api.misc

import com.m4coding.coolhub.api.datasource.bean.HotDataBean
import com.m4coding.coolhub.api.datasource.bean.RecommendBean
import org.jsoup.Jsoup
import java.io.IOException

/**
 * @author mochangsheng
 * @description
 */
object GithubHtmlUtils {

    /**
     * 获取Github登录网页的authenticity_token
     */
    fun parseAuthenticityToken(html: String): String? {
        var authenticityToken: String? = null
        try {
            val doc = Jsoup.parse(html)
            val elements = doc?.select("div.auth-form")?.select("input")
            if (elements != null) {
                for (element in elements) {
                    val name = element.attr("name")
                    if (name == "authenticity_token") {
                        authenticityToken = element.attr("value")
                        break
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return authenticityToken
    }

    /**
     * 获取Github发现页的数据
     */
    fun parseDiscoverData(html: String): List<RecommendBean> {
        val list: ArrayList<RecommendBean> = ArrayList()
        try {
            val doc = Jsoup.parse(html)
            val elements = doc?.select("div.js-repository-recommendation-and-restore")
            if (elements != null) {
                for (element in elements) {
                    val recommendBean = RecommendBean()
                    for (e1 in element.select("div.col-12.js-restore-repo-rec-container").select("input")) {
                        val name = e1.attr("name")
                        val value = e1.attr("value")
                        when (name) {
                            "user_id" -> {
                                recommendBean.userId = value
                            }
                            "repository_id" -> {
                                recommendBean.repositoryId = value
                            }
                        }
                    }

                    recommendBean.repositoryName = element.select("div.d-flex.flex-justify-between")
                            .select("span")[0]
                            .select("a").html()

                    recommendBean.describe =  element.select("p.text-gray.mb-2").text()
                    recommendBean.avatar = element.select("img.avatar").attr("src")
                    recommendBean.language = element.select("span.mr-3").text()
                    try {
                        var starString = element.select("a.muted-link.mr-3").text().trim()
                        val index = starString.indexOf(" ")
                        if (index != -1) {
                            starString = starString.substring(0, index)
                        }
                        starString = starString.replace(",", "")
                        recommendBean.starNum = starString.toInt()
                    } catch (e: NumberFormatException) {
                        e.printStackTrace()
                    }

                    val tagElements = element.select("a.topic-tag.topic-tag-link.f6.my-1")
                    if (tagElements != null) {
                        recommendBean.tagsList = ArrayList()
                        for (tag in tagElements) {
                            (recommendBean.tagsList as ArrayList<String>).add(tag.text())
                        }
                    }

                    list.add(recommendBean)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return list
    }

    /**
     * 获取Github趋势页的数据
     */
    fun parseTrendData(html: String, timeType: String): List<HotDataBean> {
        val list: ArrayList<HotDataBean> = ArrayList()
        try {
            val doc = Jsoup.parse(html)
            val elements = doc?.select("li.col-12")
            if (elements != null) {
                for (element in elements) {
                    val hotDataBean = HotDataBean()
                    hotDataBean.fullName = element.select("div.d-inline-block").select("a").attr("href").substring(1)

                    hotDataBean.repositoryName = hotDataBean.fullName?.lastIndexOf("/")?.plus(1)
                            ?.let { hotDataBean.fullName?.substring(it) }

                    hotDataBean.ownerName = hotDataBean.fullName?.lastIndexOf("/")
                            ?.let { hotDataBean.fullName?.substring(0, it) }
                    for (el in element.select("div.f6").select("span")) {
                        if (el.attr("itemprop") == "programmingLanguage") {
                            hotDataBean.language = el.text()
                            break
                        }
                    }
                    hotDataBean.describe = element.select("div.py-1").select("p.col-9").text()
                    val numElements = element.getElementsByClass("muted-link d-inline-block mr-3")
                    if (numElements != null) {
                        if (numElements.size > 1) {
                            hotDataBean.forkNum = element.getElementsByClass("muted-link d-inline-block mr-3")[1]
                                    .text().trim().replace(",", "").toInt()
                        }

                        //总的星标数
                        hotDataBean.allStarNum = element.getElementsByClass("muted-link d-inline-block mr-3").first()
                                .text().trim().replace(",", "").toInt()
                    }

                    //获取特定时间内的星标数
                    val periodElement = element.getElementsByClass("d-inline-block float-sm-right").first()
                    if (periodElement != null) {
                        var periodNumStr: String = periodElement.childNodes()[2].toString().trim()
                        periodNumStr = periodNumStr.substring(0, periodNumStr.indexOf(" "))
                                .replace(",", "")
                        hotDataBean.starNumInTime = periodNumStr.toInt()
                    }

                    hotDataBean.timeType = timeType

                    list.add(hotDataBean)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return list
    }
}