package ascomp.datepickedittext.repo

import ascomp.datepickedittext.repo.utils.*

class RelatedComponentValidator(private val datePickETMain: DatePickEditText, private val datePickETRelated: DatePickEditText) {



    fun pickAfterValidate() {
//        datePickETRelated.isEnabled = false
            datePickETMain.setOnDateChangeListener(object : OnDateChangeListener {
                override fun onDateChange() {
//                    datePickETRelated.isEnabled = true
                    datePickETRelated.clear()
                    datePickETRelated.setDateValidator(PickDateAfterValidator(datePickETMain.getDateTime()))
            }
        })
    }

    fun pickBeforeValidate() {
        datePickETMain.setOnDateChangeListener(object : OnDateChangeListener {
            override fun onDateChange() {
                datePickETRelated.clear()
                datePickETRelated.setDateValidator(PickDateBeforeValidator(datePickETMain.getDateTime()))
            }
        })
    }

    private fun showDateErrorDialog() {
        androidx.appcompat.app.AlertDialog.Builder(datePickETRelated.context)
            .setTitle(INVALID_DATE)
            .setMessage(PRIMARY_DATE_NOT_SET)
            .setNegativeButton("OK") { _, _ ->
            }.show()

    }
}

