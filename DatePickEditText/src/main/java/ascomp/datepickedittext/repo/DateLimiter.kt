package ascomp.datepickedittext.repo

import java.time.LocalDateTime


interface DateLimiter {

    fun setDateMinLimit(): LocalDateTime?
    fun setDateMaxLimit(): LocalDateTime?
}