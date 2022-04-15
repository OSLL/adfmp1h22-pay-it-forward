package com.example.payitforward


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityEditProfileBinding
import com.example.payitforward.util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (user != null) {
            FirestoreUtil.getUser(user.uid) { user ->
                if (user != null) {
                    if (user.name.isEmpty()) {
                        binding.nameInput.hint = "Please enter your name"
                    } else {
                        binding.nameInput.setText(user.name)
                    }

                    if (user.username.isEmpty()) {
                        binding.usernameInput.hint = "Please enter your username"
                    } else {
                        binding.usernameInput.setText(user.username)
                    }

                    binding.login.setText(user.email)
                    binding.login.isFocusable = false
                    binding.login.isEnabled = false

                    if (user.bio.isEmpty()) {
                        binding.bioInput.hint = "Please enter additional information about yourself"
                    } else {
                        binding.bioInput.setText(user.bio)
                    }

                }
            }
        }
        binding.buttonCancelEditProfile.setOnClickListener { cancelActivity() }
        binding.buttonDone.setOnClickListener { doneButtonActivity() }
    }

    private fun doneButtonActivity() {
        if (user != null) {
            FirestoreUtil.getUser(user.uid) { user ->
                if (user != null) {
                    val editName = binding.nameInput.text.toString()
                    if (user.name != editName) {
                        FirestoreUtil.changeUserName(user.id, editName)
                    }
                    val editUsername = binding.usernameInput.text.toString()
                    if (user.username != editUsername) {
                        FirestoreUtil.changeUserName(user.id, editUsername)
                    }
                    val editBio = binding.bioInput.text.toString()
                    if (user.bio != editBio) {
                        FirestoreUtil.changeUserBio(user.id, editBio)
                    }
                }
            }
        }
        finish()
    }

    private fun cancelActivity() {
        finish()
    }
}