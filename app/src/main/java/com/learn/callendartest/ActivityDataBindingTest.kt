package com.learn.callendartest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import android.widget.Toast
import androidx.lifecycle.Observer
import com.learn.callendartest.databinding.ActivityDataBindingTestBinding

class ActivityDataBindingTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_binding_test)

        val mainViewModel = ViewModelProviders.of(this)
                .get(MainViewModel::class.java)

        DataBindingUtil.setContentView<ActivityDataBindingTestBinding>(
                this, R.layout.activity_data_binding_test
        ).apply {
            this.lifecycleOwner = this@ActivityDataBindingTest
            this.viewModel = mainViewModel
        }

        mainViewModel.editTextContent.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}