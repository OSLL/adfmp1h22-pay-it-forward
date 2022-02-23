package com.example.payitforward


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityCreateDeedBinding

class CreateDeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateDeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateDeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}