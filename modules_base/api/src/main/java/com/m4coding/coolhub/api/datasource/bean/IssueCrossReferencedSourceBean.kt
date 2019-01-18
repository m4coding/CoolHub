package com.m4coding.coolhub.api.datasource.bean

/**
 * @author mochangsheng
 * @description
 */
class IssueCrossReferencedSourceBean {
    enum class Type {
        issue, commit
    }

    var type: Type? = null
    var issue: IssueBean? = null
}