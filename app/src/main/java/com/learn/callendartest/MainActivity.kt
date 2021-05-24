package com.learn.callendartest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import ascomp.datepickedittext.repo.*
import ascomp.datepickedittext.repo.DatePickEditText
import ascomp.datepickedittext.repo.utils.*
import java.time.LocalDateTime
import java.time.ZoneId


class MainActivity : AppCompatActivity() {

    private lateinit var etMain: EditText
    private lateinit var tvResult: TextView
    private lateinit var tvResultPick: TextView
    private lateinit var tvResultNow: TextView
    private lateinit var btSend: Button
    private lateinit var datePickComponent1: DatePickEditText
    private lateinit var datePickComponent2: DatePickEditText
    private lateinit var datePickComponent3: DatePickEditText
    private var dateObject = DateTimePicker()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePickComponent1 = findViewById(R.id.etDatePickComponent1)
        datePickComponent2 = findViewById(R.id.etDatePickComponent2)
        datePickComponent3 = findViewById(R.id.etDatePickComponent3)
        etMain = findViewById(R.id.etMain)
        tvResult = findViewById(R.id.tvResult)
        tvResultPick = findViewById(R.id.tvResultPick)
        tvResultNow = findViewById(R.id.tvResultNow)
        btSend = findViewById(R.id.btSend)
        etMain.setOnClickListener {
                dateObject.dateTimePicker(this)
        }

        btSend.sendDayDifferenceListener()

//        datePickComponent1.setDateValidator(PickDateBetweenValidator(nowWithZonePl(), nowWithZonePl().withDate(2021,8,1)))
        datePickComponent1.setOnClickListener {
            datePickComponent1.datePicker()
        }

        RelatedComponentValidator(datePickComponent1, datePickComponent2).pickAfterValidate()
        datePickComponent2.setOnClickListener {
            datePickComponent2.dateTimePicker()
        }

        datePickComponent3.setDateLimiter(DateLimitSetter(LocalDateTime.now().withDate(2021, 3, 15), null))
        datePickComponent3.setDateValidator(PickDateAfterValidator(LocalDateTime.now()))
        datePickComponent3.setOnClickListener {
            datePickComponent3.dateTimePicker()
        }
        //tvResultNow.text = LocalDateTime.now().toString()
        //tvResultPick.text = LocalDateTime.now().toString()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mi_calendar ->{
                showCalendarDialog()
                return true
            }
            R.id.mi_fragment_data_bind_test->
            {
                val intent = Intent(this, ActivityDataBindingTest::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCalendarDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_board, null)
        val customDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .show()
        val datePickED = dialogView.findViewById<DatePickEditText>(R.id.etCallendar)
//        datePickED.validateBeforeToday()
        datePickED.setOnClickListener {
            datePickED.dateTimePicker()
        }
        val btDismiss = dialogView.findViewById<Button>(R.id.btCancel)
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun Button.sendDayDifferenceListener(){
        setOnClickListener {
            if (datePickComponent1.checkIfDatePickedIsNotNull())
            tvResult.text = dayDifferenceFromNow(datePickComponent1.getDateTime()).toString()
            tvResultNow.text = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond().toString()
            tvResultPick.text = datePickComponent1.getDateTime().atZone(ZoneId.systemDefault()).toEpochSecond().toString()
        }
    }
}

