package com.example.payitforward

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        supportActionBar?.hide()

        val headerTitle: TextView = findViewById(R.id.headerTitle)
        headerTitle.text = intent.getStringExtra("title")

        val backButton: ImageButton = findViewById(R.id.button_menu)
        backButton.setOnClickListener {
            finish()
        }
    }
}