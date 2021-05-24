package ascomp.datepickedittext.repo


import ascomp.datepickedittext.repo.utils.*
import java.lang.Exception
import java.time.LocalDateTime

class PickDateAfterValidator(private val dateMIN: LocalDateTime) : DateValidator {
    override fun validateDate(date: LocalDateTime) {
            if (!date.isAfter(dateMIN)) {
                throw Exception(getErrorString())
            }
    }
    private fun getErrorString(): String {
        return INVALID_DATE_MESSAGE_DATE_FUTURE +String.format(
                DATE_FORMAT,
                dateMIN.dayOfMonth,
                dateMIN.monthValue,
                dateMIN.year)
    }

}