package com.example.payitforward

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityEditTaskBinding

class EditTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.buttonCancel!!.setOnClickListener { cancelActivity() }
    }

    private fun cancelActivity() {
        finish()
    }
}