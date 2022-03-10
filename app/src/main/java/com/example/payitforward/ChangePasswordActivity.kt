package com.example.payitforward


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {
    var cancelButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        cancelButton = findViewById<View>(R.id.button_cancel_change_password) as Button
        cancelButton!!.setOnClickListener { cancelActivity() }
    }

    private fun cancelActivity() {
        finish()
    }
}