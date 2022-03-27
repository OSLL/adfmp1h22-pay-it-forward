package com.example.payitforward

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.payitforward.databinding.FilterLayoutBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: FilterLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}