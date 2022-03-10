package com.example.payitforward


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class EditProfileActivity : AppCompatActivity() {

    var cancelButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        cancelButton = findViewById<View>(R.id.button_cancel_edit_profile) as Button
        cancelButton!!.setOnClickListener { cancelActivity() }
    }

    private fun cancelActivity() {
        finish()
    }
}