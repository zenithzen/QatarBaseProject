package com.mbt.datelib

import android.content.res.Resources
import java.util.*

object DateLib {
    fun getDay(res: Resources, day: Int) = when (day) {
        Calendar.SUNDAY -> res.getString(R.string.datelib_day_01)
        Calendar.MONDAY -> res.getString(R.string.datelib_day_02)
        Calendar.TUESDAY -> res.getString(R.string.datelib_day_03)
        Calendar.WEDNESDAY -> res.getString(R.string.datelib_day_04)
        Calendar.THURSDAY -> res.getString(R.string.datelib_day_05)
        Calendar.FRIDAY -> res.getString(R.string.datelib_day_06)
        Calendar.SATURDAY -> res.getString(R.string.datelib_day_07)
        else -> throw DateLibException("Illegal day $day, must be a valid day in Calendar.**DAY")
    }
    fun getDayString(res: Resources, day: String) = when (day) {
        "Sunday" -> res.getString(R.string.datelib_day_01)
        "Monday" -> res.getString(R.string.datelib_day_02)
        "Tuesday" -> res.getString(R.string.datelib_day_03)
        "Wednesday" -> res.getString(R.string.datelib_day_04)
        "Thursday" -> res.getString(R.string.datelib_day_05)
        "Friday" -> res.getString(R.string.datelib_day_06)
        "Saturday" -> res.getString(R.string.datelib_day_07)
        else -> throw DateLibException("Illegal day $day, must be a valid day in Calendar.**DAY")
    }

    fun getShortDay(res: Resources, day: Int) = when (day) {
        Calendar.SUNDAY -> res.getString(R.string.datelib_day_short_01)
        Calendar.MONDAY -> res.getString(R.string.datelib_day_short_02)
        Calendar.TUESDAY -> res.getString(R.string.datelib_day_short_03)
        Calendar.WEDNESDAY -> res.getString(R.string.datelib_day_short_04)
        Calendar.THURSDAY -> res.getString(R.string.datelib_day_short_05)
        Calendar.FRIDAY -> res.getString(R.string.datelib_day_short_06)
        Calendar.SATURDAY -> res.getString(R.string.datelib_day_short_07)
        else -> throw DateLibException("Illegal day $day, must be a valid day in Calendar.**DAY")
    }

    fun getSingleCharDay(res: Resources, day: Int) = when (day) {
        Calendar.SUNDAY -> res.getString(R.string.datelib_day_single_01)
        Calendar.MONDAY -> res.getString(R.string.datelib_day_single_02)
        Calendar.TUESDAY -> res.getString(R.string.datelib_day_single_03)
        Calendar.WEDNESDAY -> res.getString(R.string.datelib_day_single_04)
        Calendar.THURSDAY -> res.getString(R.string.datelib_day_single_05)
        Calendar.FRIDAY -> res.getString(R.string.datelib_day_single_06)
        Calendar.SATURDAY -> res.getString(R.string.datelib_day_single_07)
        else -> throw DateLibException("Illegal day $day, must be a valid day in Calendar.**DAY")
    }

    fun getMonth(res: Resources, month: Int) = when (month) {
        Calendar.JANUARY -> res.getString(R.string.datelib_month_01)
        Calendar.FEBRUARY -> res.getString(R.string.datelib_month_02)
        Calendar.MARCH -> res.getString(R.string.datelib_month_03)
        Calendar.APRIL -> res.getString(R.string.datelib_month_04)
        Calendar.MAY -> res.getString(R.string.datelib_month_05)
        Calendar.JUNE -> res.getString(R.string.datelib_month_06)
        Calendar.JULY -> res.getString(R.string.datelib_month_07)
        Calendar.AUGUST -> res.getString(R.string.datelib_month_08)
        Calendar.SEPTEMBER -> res.getString(R.string.datelib_month_09)
        Calendar.OCTOBER -> res.getString(R.string.datelib_month_10)
        Calendar.NOVEMBER -> res.getString(R.string.datelib_month_11)
        Calendar.DECEMBER -> res.getString(R.string.datelib_month_12)
        else -> throw DateLibException("Illegal month $month, must be a valid month in Calendar.MONTH")
    }
    fun getMonthString(res: Resources, month: String) = when (month) {
        "January" -> res.getString(R.string.datelib_month_01)
        "February" -> res.getString(R.string.datelib_month_02)
        "March" -> res.getString(R.string.datelib_month_03)
        "April" -> res.getString(R.string.datelib_month_04)
        "May" -> res.getString(R.string.datelib_month_05)
        "June" -> res.getString(R.string.datelib_month_06)
        "July" -> res.getString(R.string.datelib_month_07)
        "August" -> res.getString(R.string.datelib_month_08)
        "September" -> res.getString(R.string.datelib_month_09)
        "October" -> res.getString(R.string.datelib_month_10)
        "November" -> res.getString(R.string.datelib_month_11)
        "December" -> res.getString(R.string.datelib_month_12)
        else -> throw DateLibException("Illegal month $month, must be a valid month in Calendar.MONTH")
    }

    fun getShortMonth(res: Resources, month: Int) = when (month) {
        Calendar.JANUARY -> res.getString(R.string.datelib_month_short_01)
        Calendar.FEBRUARY -> res.getString(R.string.datelib_month_short_02)
        Calendar.MARCH -> res.getString(R.string.datelib_month_short_03)
        Calendar.APRIL -> res.getString(R.string.datelib_month_short_04)
        Calendar.MAY -> res.getString(R.string.datelib_month_short_05)
        Calendar.JUNE -> res.getString(R.string.datelib_month_short_06)
        Calendar.JULY -> res.getString(R.string.datelib_month_short_07)
        Calendar.AUGUST -> res.getString(R.string.datelib_month_short_08)
        Calendar.SEPTEMBER -> res.getString(R.string.datelib_month_short_09)
        Calendar.OCTOBER -> res.getString(R.string.datelib_month_short_10)
        Calendar.NOVEMBER -> res.getString(R.string.datelib_month_short_11)
        Calendar.DECEMBER -> res.getString(R.string.datelib_month_short_12)
        else -> throw DateLibException("Illegal month $month, must be a valid month in Calendar.MONTH")
    }

    fun getAmPm(res: Resources, ampm: Int) = when (ampm) {
        Calendar.AM -> res.getString(R.string.datelib_am)
        Calendar.PM -> res.getString(R.string.datelib_pm)
        else -> throw DateLibException("Illegal value $ampm, should be one of ${Calendar.AM} (Calendar.AM) or ${Calendar.PM} (Calendar.PM)")
    }
    fun getAmPmString(res: Resources, ampm: String) = when (ampm) {
        "AM" -> res.getString(R.string.datelib_am)
        "PM" -> res.getString(R.string.datelib_pm)
        else -> throw DateLibException("Illegal value $ampm, should be one of ${Calendar.AM} (Calendar.AM) or ${Calendar.PM} (Calendar.PM)")
    }
}

class DateLibException(override val message: String) : Exception(message)