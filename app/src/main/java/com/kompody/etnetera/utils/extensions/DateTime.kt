package com.kompody.etnetera.utils.extensions

import com.kompody.etnetera.utils.Const
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {
    const val Simple = "dd.MM.yyyy"
    const val Month = "LLLL"
    const val MonthYear = "LLLL yyyy"
    const val Hour = "HH:mm"
    const val Seconds = "HH:mm:ss"
    const val FullSet = "dd.MM.yyyy HH:mm:ss"
    const val FullSetText = "dd MMMM yyyy г., HH:mm"
    const val DayMonth = "d MMMM"
    const val DayMonthYear = "d MMMM yyyy"
    const val FullDayMonthYear = "dd.MM.yyyy"
}

const val DATE_LOCALE = "cz"

val defaultLocale = Locale(DATE_LOCALE)

fun now(): Calendar = calendarOf(Date())

fun calendarOf(day: Int, month: Int, year: Int, locale: Locale = defaultLocale): Calendar {
    val calendar = Calendar.getInstance(locale)
    calendar[Calendar.DAY_OF_MONTH] = day
    calendar[Calendar.MONTH] = month
    calendar[Calendar.YEAR] = year
    return calendar
}

fun calendarOf(date: Date): Calendar = Calendar.getInstance().apply {
    time = date
}

fun calendarOf(millis: Long, timezone: TimeZone = TimeZone.getDefault()): Calendar = Calendar.getInstance(timezone).apply {
    timeInMillis = millis
}

fun dateOf(millis: Long): Date = Date(millis)

fun dateOf(hour: Int, minute: Int): Date = Date(hour * Const.ONE_HOUR + minute * Const.ONE_MINUTE)

fun Date.toString(pattern: String, locale: Locale = defaultLocale): String =
    SimpleDateFormat(pattern, locale).format(this)
        .replaceFirstChar { it.uppercase() }

fun String.toDate(pattern: String, locale: Locale = defaultLocale): Date = try {
    SimpleDateFormat(pattern, locale).parse(this)
        ?: throw RuntimeException("Can't convert $this to Date")
} catch (e: Throwable) {
    SimpleDateFormat(pattern.replace("X", String.empty), locale).parse(this)
        ?: throw RuntimeException("Can't convert $this to Date")
}

fun String.reformatDate(
    sourcePattern: String,
    targetPattern: String,
    locale: Locale = defaultLocale
) = this.toDate(sourcePattern).toString(targetPattern, locale).replaceFirstChar { it.uppercase() }

fun Date.getDayMonth(locale: Locale = defaultLocale): String {
    val sdf = SimpleDateFormat(DateFormat.DayMonth, locale)
    return sdf.format(this)
}

fun Date.getDayMonthYear(locale: Locale = defaultLocale): String {
    val sdf = SimpleDateFormat(DateFormat.DayMonthYear, locale)
    return sdf.format(this)
}

fun getDateTimes(year: Int, month: Int, dayOfMonth: Int): String {
    val cal = Calendar.getInstance()
    cal.set(year, month, dayOfMonth)
    val simpleDateFormat = SimpleDateFormat(DateFormat.FullDayMonthYear, Locale("ru"))
    return simpleDateFormat.format(cal.time)
}

fun getMilliseconds(timeString: String, isNeedAddTimeZone: Boolean = false): Long {
    if (timeString.isEmpty()) return -TimeZone.getDefault().rawOffset.toLong()

    val subString = timeString.split(":")
    val watch = subString[0].trim()
    val minutes = subString[1].trim()

    val watchLong = watch.toLong()
    val minutesLong = minutes.toLong()

    val milliseconds = watchLong * Const.WATCH_MILLIS + minutesLong * Const.MINUTES_MILLIS

    return if (isNeedAddTimeZone) {
        milliseconds + TimeZone.getDefault().rawOffset
    } else {
        milliseconds
    }
}