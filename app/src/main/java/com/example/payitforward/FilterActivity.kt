package com.example.payitforward

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.payitforward.databinding.FilterLayoutBinding

class FilterActivity : AppCompatActivity() {

    private lateinit var binding: FilterLayoutBinding
    private var filterNew = false
    private var filterInProgress = false
    private var filterOnReview = false
    private var filterCompleted = false
    private var filterAccepted = false
    private var filterRejected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FilterLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.filterApply.setOnClickListener {
            filterNew = binding.filterNew.isChecked
            filterInProgress = binding.filterInProgress.isChecked
            filterOnReview = binding.filterOnReview.isChecked
            filterCompleted = binding.filterCompleted.isChecked
            filterAccepted = binding.filterCompleted.isChecked
            filterRejected = binding.filterRejected.isChecked
            finish()
        }
        binding.filterCancel.setOnClickListener {
            finish()
        }
    }
}