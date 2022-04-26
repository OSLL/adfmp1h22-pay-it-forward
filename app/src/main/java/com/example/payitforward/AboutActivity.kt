package com.example.payitforward


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    var cancelButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        cancelButton = findViewById<View>(R.id.back_button) as ImageButton
        cancelButton!!.setOnClickListener { cancelActivity() }
    }


    private fun cancelActivity() {
        finish()
    }
}