package com.learn.callendartest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import org.joda.time.DateTime


class DateTimePicker {

    var datePicked:DateTime?=null


    companion object{
        private const val UNIX_DAY_MILLISECOND = 86400000
        private const val DATE_FORMAT = "%1$02d/%2$02d/%3$04d"
        private const val DATE_TIME_FORMAT = "%1$02d/%2$02d/%3$04d %4$02d:%5$02d"
    }

    fun DateTime.withAll(year: Int, month: Int, day: Int, hours: Int, minutes: Int): DateTime =
            this.withYear(year)
                    .withMonthOfYear(month)
                    .withDayOfMonth(day)
                    .withHourOfDay(hours)
                    .withMinuteOfHour(minutes)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0)

    fun dayCounter():Int{
        val dateFromUnix: Long = datePicked!!.withAll(datePicked!!.year,datePicked!!.monthOfYear,datePicked!!.dayOfMonth,0,0).toDate().time
        val dateToUnix: Long = DateTime.now().withAll(DateTime.now().year,DateTime.now().monthOfYear,DateTime.now().dayOfMonth,0,0).toDate().time
        return ((dateFromUnix - dateToUnix) / UNIX_DAY_MILLISECOND).toInt()
    }
    fun datePicker(editText: EditText,context: Context){
        val dateTime = DateTime()
        DatePickerDialog(context, { _, year, month, day ->
            val datePick = DateTime.now().withAll(year, month + 1, day, 0, 0)
            if (datePick.isAfterNow) {
                datePicked = datePick
                editText.setText(getDateString())
            } else {
                showDateErrorDialog(context)
            }
        }, dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth).show()
    }


    fun dateTimePicker(context: Context){
        val dateTime = DateTime()
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, {_, hour, min ->
               // if(validate){
               //     if (validateDateIsAfterToday()){
                        datePicked= DateTime.now().withAll(year, month + 1, day, hour, min)
               //     } else
               //     {
               //         showDateErrorDialog(context)
               //     }
               // }
               // datePicked= DateTime.now().withAll(year, month + 1, day, hour, min)
            }, dateTime.hourOfDay, dateTime.minuteOfHour, true).show()
        }, dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth).show()
    }

    fun getDateString():String = String.format(
            DATE_FORMAT,
            datePicked!!.dayOfMonth,
            datePicked!!.monthOfYear+1,
            datePicked!!.year
    )

    fun getDateTimeString():String = String.format(
            DATE_TIME_FORMAT,
            datePicked!!.dayOfMonth,
            datePicked!!.monthOfYear+1,
            datePicked!!.year,
            datePicked!!.hourOfDay,
            datePicked!!.minuteOfHour
    )

    fun showDateErrorDialog(context: Context) {
        AlertDialog.Builder(context)
                .setTitle("Nieprawidłowa data")
                .setMessage("Nie można wybrać wstecznej daty")
                .setNegativeButton("OK",null).show()
    }

}