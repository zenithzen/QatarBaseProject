package com.gco.gco.utils

import java.util.*

object DateToken {
    fun convertTimestampToDateToken(timeInMillis: Long): Long {
        val cal = Calendar.getInstance(Locale.US)
        cal.timeInMillis = timeInMillis
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val date = cal[Calendar.DAY_OF_MONTH]
        return convertToDateToken(year, month, date)
    }

    fun convertDateTokenToTimestamp(dateToken: Long): Long {
        //  2021   20210629  / 10000L
        val year = dateToken / 10000L
        //  0629       20210629  - 20210000
        val monthDay = dateToken - year * 10000L
        //  06    =   0629   / 100
        val month = monthDay / 100L
        //  29   =  0629    - 0600
        val date = monthDay - month * 100L
        val cal = Calendar.getInstance(Locale.US)
        cal.set(year.toInt(), month.toInt(), date.toInt(), 0, 0, 0)
        cal[Calendar.MILLISECOND] = 0
        return cal.timeInMillis
    }

    fun convertToDateToken(year: Int, month: Int, date: Int): Long {
        return year * 10000L + month * 100L + date
    }

    fun isSameMonthAndYear(lhsToken: Long, rhsToken: Long): Boolean {
        return (lhsToken / 100L) == (rhsToken / 100L)
    }

    fun getFirstDayOfMonth(dateToken: Long): Long {
        return (dateToken / 100L) * 100L + 1L
    }
}