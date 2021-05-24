package ascomp.datepickedittext.repo

import java.time.LocalDateTime


class DateLimitSetter(private val dateMIN: LocalDateTime?, private val dateMAX: LocalDateTime?):
    DateLimiter {



    override fun setDateMinLimit(): LocalDateTime? {
        return dateMIN
    }

    override fun setDateMaxLimit(): LocalDateTime? {
        return dateMAX
    }

}