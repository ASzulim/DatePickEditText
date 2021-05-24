package ascomp.datepickedittext.repo

import java.time.LocalDateTime


interface DateValidator {

    fun validateDate(date: LocalDateTime)

}
