package com.example.payitforward


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CreateDeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_deed)
        supportActionBar?.hide()
    }
}