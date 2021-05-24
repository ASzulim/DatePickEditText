package ascomp.datepickedittext.repo

import ascomp.datepickedittext.repo.utils.*
import java.lang.Exception
import java.time.LocalDateTime

class PickDateBetweenValidator(
    private val dateMIN: LocalDateTime,
    private val dateMAX: LocalDateTime): DateValidator {

    override fun validateDate(date: LocalDateTime) {
        if(!date.isAfter(dateMIN)||!date.isBefore(dateMAX)){
        throw Exception(getErrorString())
        }
    }

     private fun getErrorString(): String {
         val dateFrom = String.format(
                 DATE_FORMAT,
                 dateMIN.dayOfMonth,
                 dateMIN.monthValue,
                 dateMIN.year)
         val dateTo = String.format(
                 DATE_FORMAT,
                 dateMAX.dayOfMonth,
                 dateMAX.monthValue,
                 dateMAX.year)
        return "$INVALID_DATE_MESSAGE_DATE_BETWEEN$dateFrom || $dateTo"
    }


}