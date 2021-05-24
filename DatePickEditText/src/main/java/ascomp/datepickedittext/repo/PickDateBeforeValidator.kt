package ascomp.datepickedittext.repo

import ascomp.datepickedittext.repo.utils.*
import java.lang.Exception
import java.time.LocalDateTime

class PickDateBeforeValidator(private val dateMAX: LocalDateTime): DateValidator {

    override fun validateDate(date: LocalDateTime) {
        if (!date.isAfter(dateMAX)){
            throw Exception(getErrorString())
        }
    }

    private fun getErrorString(): String {
        return INVALID_DATE_MESSAGE_DATE_PAST +String.format(
                DATE_FORMAT,
                dateMAX.dayOfMonth,
                dateMAX.monthValue,
                dateMAX.year)
    }
}