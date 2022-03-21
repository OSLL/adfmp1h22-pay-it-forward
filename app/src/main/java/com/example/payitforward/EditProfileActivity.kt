package com.example.payitforward


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityEditProfileBinding


class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    var cancelButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCancelEditProfile.setOnClickListener { cancelActivity() }
    }

    private fun cancelActivity() {
        finish()
    }
}