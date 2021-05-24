package ascomp.datepickedittext.repo.utils


import ascomp.datepickedittext.repo.utils.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


fun dayDifferenceFromNow(date:LocalDateTime):Int{
    val dateFromUnix: Long = date.withDateTime(
            date.year,
            date.monthValue,
            date.dayOfMonth,
            0, 0).atZone(ZoneId.systemDefault()).toEpochSecond()
    val dateToUnix: Long = LocalDateTime.now().withDateTime(
            LocalDateTime.now().year,
            LocalDateTime.now().monthValue,
            LocalDateTime.now().dayOfMonth,
            0, 0).atZone(ZoneId.systemDefault()).toEpochSecond()
    val difference: Int = ((dateFromUnix - dateToUnix) / UNIX_DAY_MILLISECOND).toInt()
    return if(difference>=0){
        difference
    } else{
        difference*-1
    }
}


fun LocalDateTime.withDateTime(year: Int,month: Int,day: Int,hours: Int,minutes: Int): LocalDateTime =
    this.withYear(year)
            .withMonth(month)
            .withDayOfMonth(day)
            .withHour(hours)
            .withMinute(minutes)
            .withSecond(0)
            .withNano(0)

fun LocalDateTime.withTime(hours: Int,minutes: Int,seconds: Int,milliseconds:Int): LocalDateTime =
    this.withHour(hours)
        .withMinute(minutes)
        .withSecond(seconds)
        .withNano(milliseconds)

fun LocalDateTime.withDate(year: Int,month: Int,day: Int): LocalDateTime =
    this.withYear(year)
        .withMonth(month)
        .withDayOfMonth(day)


fun dayDifference(dateFrom: LocalDateTime,dateTo: LocalDateTime):Int{
    val dateFromUnix: Long = dateFrom.withDateTime(
            dateFrom.year,
            dateFrom.monthValue,
            dateFrom.dayOfMonth,
            0,0).atZone(ZoneId.systemDefault()).toEpochSecond()
    val dateToUnix: Long = dateTo.withDateTime(
            dateTo.year,
            dateTo.monthValue,
            dateTo.dayOfMonth,
            0,0).atZone(ZoneId.systemDefault()).toEpochSecond()
    val difference: Int = ((dateFromUnix - dateToUnix) / UNIX_DAY_MILLISECOND).toInt()
    return if(difference>0){
        difference
    } else{
        difference*-1
    }
}