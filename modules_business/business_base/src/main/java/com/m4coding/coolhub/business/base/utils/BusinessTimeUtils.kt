package com.m4coding.coolhub.business.base.utils

import android.annotation.SuppressLint
import android.content.Context
import com.m4coding.business_base.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author mochangsheng
 * @description
 */
object BusinessTimeUtils {

    private const val MILLIS_LIMIT = 1000.0
    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT
    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT
    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT
    private const val DAYS_LIMIT = 30 * HOURS_LIMIT

    fun getTimeStr(context: Context, date: Date?): String? {

        if (null == date) {
            return null
        }

        val subTime = System.currentTimeMillis() - date.time
        return when {
            subTime < MILLIS_LIMIT -> context.getString(R.string.just_now)
            subTime < SECONDS_LIMIT -> Math.round(subTime / MILLIS_LIMIT).toString() + " " + context.getString(R.string.seconds_ago)
            subTime < MINUTES_LIMIT -> Math.round(subTime / SECONDS_LIMIT).toString() + " " + context.getString(R.string.minutes_ago)
            subTime < HOURS_LIMIT -> Math.round(subTime / MINUTES_LIMIT).toString() + " " + context.getString(R.string.hours_ago)
            subTime < DAYS_LIMIT -> Math.round(subTime / HOURS_LIMIT).toString() + " " + context.getString(R.string.days_ago)
            else -> getDateStr(date)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateStr(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
}