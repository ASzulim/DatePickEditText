package ascomp.datepickedittext.repo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.AttributeSet
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import ascomp.datepickedittext.repo.*
import ascomp.datepickedittext.repo.utils.*
import ascomp.datepickedittext.repo.utils.withDate
import ascomp.datepickedittext.repo.utils.withDateTime
import ascomp.datepickedittext.repo.utils.withTime
import java.time.LocalDateTime
import java.time.ZoneId


class DatePickEditText @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle:Int = android.R.attr.editTextStyle,
        ): androidx.appcompat.widget.AppCompatEditText(context, attributeSet, defStyle),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{


        private var datePicked: LocalDateTime? = null
        private var validation: Boolean = false
        private var dateTimeMode: Boolean = false
        private var minDate: LocalDateTime? = null
        private var maxDate: LocalDateTime? = null
        private lateinit var dateValidator: DateValidator
        private lateinit var dateLimiter: DateLimiter
        private var listener: OnDateChangeListener? = null


         private fun setDatePicked(datePicked: LocalDateTime){
                this.datePicked= datePicked
                listener?.onDateChange()
        }


        fun setOnDateChangeListener(listener: OnDateChangeListener){
                this.listener=listener
        }


        fun setDateValidator(dateValidator: DateValidator) {
                this.dateValidator=dateValidator
                validation=true
        }

        fun setDateLimiter(dateLimiter: DateLimiter){
                this.dateLimiter=dateLimiter
                maxDate=dateLimiter.setDateMaxLimit()
                minDate=dateLimiter.setDateMinLimit()
        }

        private fun setTime(hourOfDay: Int,minute: Int){
                setDatePicked(updateTime(hourOfDay, minute))

        }

        private fun setDate(year: Int,month: Int,dayOfMonth: Int){
                try {
                setDatePicked(updateDate(year,month+1,dayOfMonth))
                }catch (ex: Exception){
                        showDateErrorDialog(ex.localizedMessage!!)
                }
        }



        fun datePicker(){
                val dateTime = LocalDateTime.now()
                        DatePickerDialog(context,this,dateTime.year,dateTime.monthValue-1,dateTime.dayOfMonth).also { dialog ->
                                minDate?.let {
                                        dialog.datePicker.minDate = it.atZone(ZoneId.systemDefault()).toEpochSecond()*1000
                                }
                                maxDate?.let {
                                        dialog.datePicker.maxDate = it.atZone(ZoneId.systemDefault()).toEpochSecond()*1000
                                }
                        }.show()
        }

        private fun timePicker(){
                val dateTime = LocalDateTime.now()
                TimePickerDialog(context,
                        this,
                        dateTime.hour,
                        dateTime.minute,
                        true).show()
        }

        fun dateTimePicker(){
                datePicker()
                dateTimeMode = true
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val datePick = LocalDateTime.now().withDateTime(year, month+1 , dayOfMonth,0,0)
                if (validation) {
                        try {
                                dateValidator.validateDate(datePick)
                                setDate(year,month,dayOfMonth)
                                if (dateTimeMode){
                                        timePicker()
                                } else {
                                        setText(getDateString())
                                }
                        }
                        catch (e:Exception) {
                                showDateErrorDialog(e.localizedMessage!!)
                        }

                }else{
                        setDate(year,month,dayOfMonth)
                        if (dateTimeMode){
                                timePicker()
                        } else {
                                setText(getDateString())
                        }
                }
        }

        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                if (dateTimeMode) {
                        setTime(hourOfDay, minute)
                        setText(getDateTimeString())
                } else {
                        setTime(hourOfDay,minute)
                        setText(getTimeString())
                }
        }



        private fun updateTime(hourOfDay: Int,minute: Int):LocalDateTime{
                return if (checkIfDatePickedIsNotNull()) {
                        datePicked!!.withTime(hourOfDay, minute, 0, 0)
                } else{
                        LocalDateTime.now().withTime(hourOfDay, minute, 0, 0)
                }
        }

        private fun updateDate(year: Int,month: Int,dayOfMonth: Int):LocalDateTime{
                return if (checkIfDatePickedIsNotNull()){
                        datePicked!!.withDateTime(
                                year,
                                month,
                                dayOfMonth,
                                datePicked!!.hour,
                                datePicked!!.minute)
                }else {
                        LocalDateTime.now().withDate(year, month, dayOfMonth)
                }
        }

        private fun showDateErrorDialog(message:String) {
                AlertDialog.Builder(context)
                        .setTitle(INVALID_DATE)
                        .setMessage(message)
                        .setNegativeButton("OK") { _, _ ->
                                datePicker()
                        }.show()
        }

        fun getDateString():String {
                return when(checkIfDatePickedIsNotNull()) {
                        true -> String.format(
                                DATE_FORMAT,
                                datePicked!!.dayOfMonth,
                                datePicked!!.monthValue,
                                datePicked!!.year)
                        else -> DATE_NOT_SELECTED
                }
        }

        fun getDateTimeString():String {
                return when(checkIfDatePickedIsNotNull()) {
                        true -> String.format(
                                DATE_TIME_FORMAT,
                                datePicked!!.dayOfMonth,
                                datePicked!!.monthValue,
                                datePicked!!.year,
                                datePicked!!.hour,
                                datePicked!!.minute)
                         else -> DATE_NOT_SELECTED
                }
        }

        fun getTimeString():String {
                return when(checkIfDatePickedIsNotNull()) {
                        true -> String.format(
                                TIME_FORMAT,
                                datePicked!!.hour,
                                datePicked!!.minute)
                        else -> TIME_NOT_SELECTED
                }
        }

        fun checkIfDatePickedIsNotNull():Boolean{
                return datePicked!=null
        }
        fun checkIfMinDateIsNotNull():Boolean{
                return minDate!=null
        }
        fun checkIfMaxDateIsNotNull():Boolean{
                return maxDate!=null
        }

        fun getDateTime():LocalDateTime{
                return datePicked!!
        }

        fun clear(){
                datePicked=null
                minDate=null
                maxDate=null
                validation=false
                this.setText("")
        }


}



/*
        fun validateAfterToday(){
                validator= VALIDATE_AFTER_TODAY
        }

        fun validateBeforeToday(){
                validator = VALIDATE_BEFORE_TODAY
        }

        fun validateAfterDate(date: DateTime){
                validator = VALIDATE_AFTER_DATE
                dateValidation = date
        }

        fun validateBeforeDate(date: DateTime){
                validator = VALIDATE_BEFORE_DATE
                dateValidation = date
        }

        companion object{
                private const val VALIDATE_AFTER_TODAY = "Validate after today"
                private const val VALIDATE_BEFORE_TODAY = "Validate before today"
                private const val VALIDATE_AFTER_DATE = "Validate after date"
                private const val VALIDATE_BEFORE_DATE = "Validate before date"
                private const val UNIX_DAY_MILLISECOND = 86400000
                private const val DATE_FORMAT = "%1$02d/%2$02d/%3$04d"
                private const val DATE_TIME_FORMAT = "%1$02d/%2$02d/%3$04d %4$02d:%5$02d"
                private const val INVALID_DATE_TITLE = "Invalid date"
                private const val INVALID_DATE_MESSAGE_PAST = "Please pick past date"
                private const val INVALID_DATE_MESSAGE_FUTURE = "Please pick future date"
                private const val INVALID_DATE_MESSAGE_DATE_FUTURE = "Please pick date after: "
                private const val INVALID_DATE_MESSAGE_DATE_PAST = "Please pick date before: "
                private const val INVALID_DATE = "Invalid date"
                private const val DATE_NOT_SELECTED = "Date has not been selected"
        }

                private fun onDateSetValidator(date: DateTime) {
                when(validator){
                        VALIDATE_AFTER_TODAY -> isDateAfterToday(date, format)
                        VALIDATE_BEFORE_TODAY -> isDateBeforeToday(date, format)
                        VALIDATE_AFTER_DATE -> isDateAfterDate(date, format)
                        VALIDATE_BEFORE_DATE -> isDateBeforeDate(date, format)
                        else -> { datePicked = date
                                this.setText(getDateString())}
                }
        }

                private fun isDateAfterToday(date: DateTime,format:String) {
                if(date.isAfterNow&&dayDifference(date,DateTime.now())>0){
                        when(format){
                                DATE_FORMAT -> {datePicked = date
                                        this.setText(getDateString()) }
                                DATE_TIME_FORMAT -> {datePicked = date
                                        this.setText(getDateTimeString())}
                        }
                } else {
                        showDateErrorDialog()
                }
        }

        private fun isDateBeforeToday(date: DateTime,format:String) {
                if(date.isBeforeNow&&dayDifference(date,DateTime.now())>0){
                        when(format){
                                DATE_FORMAT -> {datePicked = date
                                        this.setText(getDateString()) }
                                DATE_TIME_FORMAT -> {datePicked = date
                                        this.setText(getDateTimeString())}
                        }
                } else {
                        showDateErrorDialog()
                }
        }

         private fun isDateAfterDate(date: DateTime, format:String){
                 if(date.isAfter(dateValidation)&&dayDifference(date,dateValidation!!)>0){
                         when(format){
                                 DATE_FORMAT -> {datePicked = date
                                         this.setText(getDateString()) }
                                 DATE_TIME_FORMAT -> {datePicked = date
                                         this.setText(getDateTimeString())}
                         }
                 } else {
                         showDateErrorDialog()
                 }
         }

        private fun isDateBeforeDate(date: DateTime, format:String){
                if(date.isBefore(dateValidation)&&dayDifference(date,dateValidation!!)>0){
                        when(format){
                                DATE_FORMAT -> {datePicked = date
                                        this.setText(getDateString()) }
                                DATE_TIME_FORMAT -> {datePicked = date
                                        this.setText(getDateTimeString())}
                        }
                } else {
                        showDateErrorDialog()
                }
        }

                private fun errorDialogMessage():String{
                return when(validator){
                        VALIDATE_AFTER_TODAY -> INVALID_DATE_MESSAGE_FUTURE
                        VALIDATE_BEFORE_TODAY -> INVALID_DATE_MESSAGE_PAST
                        VALIDATE_AFTER_DATE -> INVALID_DATE_MESSAGE_DATE_FUTURE+String.format(
                                DATE_FORMAT,
                                dateValidation!!.dayOfMonth,
                                dateValidation!!.monthOfYear,
                                dateValidation!!.year)
                        VALIDATE_BEFORE_DATE -> INVALID_DATE_MESSAGE_DATE_PAST+String.format(
                                DATE_FORMAT,
                                dateValidation!!.dayOfMonth,
                                dateValidation!!.monthOfYear,
                                dateValidation!!.year)
                        else -> INVALID_DATE
                }
        }

                fun dateTimePicker(){
                val dateTime = DateTime()
                DatePickerDialog(context, { _, year, month, day ->
                        TimePickerDialog(context, { _, hour, min ->
                                val datePick = DateTime.now().withDateTime(year, month + 1, day,hour,min)
                                //onDateSetValidator(datePick)
                        }, dateTime.hourOfDay, dateTime.minuteOfHour, true).show()
                }, dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth).show()
        }
        */