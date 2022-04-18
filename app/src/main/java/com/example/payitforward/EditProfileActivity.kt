package com.example.payitforward


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityEditProfileBinding
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser

    private var photo: Uri? = null
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (user != null) {
            FirestoreUtil.getUser(user.uid) { user ->
                if (user != null) {
                    if (user.photo != null) {
                        GlideApp.with(this).load(StorageUtil.pathToReference(user.photo)).into(binding.imageView)
                    }

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
        binding.imageView.isClickable = true
        binding.imageView.isFocusable = true
        binding.imageView.setOnClickListener { changePhotoActivity() }
    }

    private fun changePhotoActivity() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

        var userPhoto: String? = null

        if (user != null) {
            FirestoreUtil.getUser(user.uid) { user ->
                if (user != null) {
                    userPhoto = user.photo
                }
            }
        }

        if (userPhoto != null) {
            val photoRef = StorageUtil.pathToReference(userPhoto!!)
            GlideApp.with(this).load(photoRef).into(binding.imageView)
            imagePath = userPhoto
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.imageView.setImageURI(data?.data)
                photo = data?.data
            }
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
                        FirestoreUtil.changeUserUsername(user.id, editUsername)
                    }
                    val editBio = binding.bioInput.text.toString()
                    if (user.bio != editBio) {
                        FirestoreUtil.changeUserBio(user.id, editBio)
                    }
                    if (user.photo != photo.toString()) {
                        photo?.let {
                            StorageUtil.uploadUserImage(it, user.id) { path ->
                                FirestoreUtil.changeUserPhoto(user.id, path)
                            }
                        }
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