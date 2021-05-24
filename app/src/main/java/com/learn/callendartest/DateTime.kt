package com.learn.callendartest

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import org.joda.time.DateTime

fun DateTime.withAll(year: Int, month: Int, day: Int, hours: Int, minutes: Int) =
    this.withYear(year)
        .withMonthOfYear(month)
        .withDayOfMonth(day)
        .withHourOfDay(hours)
        .withMinuteOfHour(minutes)
        .withSecondOfMinute(0)
        .withMillisOfSecond(0)

fun DateTime.datePicker(context: Context, editText: EditText){
    val dateTime = DateTime()
    DatePickerDialog(context, { _, year, month, day ->
        val datePick = DateTime.now().withAll(year, month + 1, day, 0, 0)
        if (datePick.isAfterNow) {
            editText.setText(String.format("%1$02d/%2$02d/%3$04d", day, month + 1, year))
        } else {
            AlertDialog.Builder(context)
                    .setTitle("tittle")
                    .setMessage("message")
                    .setNegativeButton("OK",null).show()
        }
    }, dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth).show()
}