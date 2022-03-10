package com.example.payitforward


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        cancelButton = findViewById<View>(R.id.button_cancel_about) as Button
        cancelButton!!.setOnClickListener { cancelActivity() }
    }


    private fun cancelActivity() {
        finish()
    }
}