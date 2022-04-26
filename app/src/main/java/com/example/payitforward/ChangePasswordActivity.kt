package com.example.payitforward


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordActivity : AppCompatActivity() {

    private var cancelButton: Button? = null
    private var doneButton: Button? = null
    private var auth: FirebaseAuth = Firebase.auth
    private var currentPassword: EditText? = null
    private var newPassword: EditText? = null
    private var repeatNewPassword: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        currentPassword = findViewById<View>(R.id.current_password_input) as EditText
        newPassword = findViewById<View>(R.id.new_password_input) as EditText
        repeatNewPassword = findViewById<View>(R.id.repeat_password_input) as EditText


        cancelButton = findViewById<View>(R.id.button_cancel_change_password) as Button
        cancelButton!!.setOnClickListener { cancelActivity() }

        doneButton = findViewById<View>(R.id.button_done_change_password) as Button
        doneButton!!.setOnClickListener { changePasswordActivity() }
    }

    private fun changePasswordActivity() {
        val user = auth.currentUser
        var authCredential: AuthCredential? = null
        if (user != null && !currentPassword!!.text.toString().isEmpty()) {
            authCredential = EmailAuthProvider.getCredential(
                user.email.toString(),
                currentPassword!!.text.toString()
            )
        }

        if (user != null) {
            if (authCredential != null) {
                user.reauthenticate(authCredential).addOnCompleteListener {
                    if (it.isSuccessful && newPassword!!.text.toString() == repeatNewPassword!!.text.toString()) {
                        user.updatePassword(newPassword!!.text.toString())
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Password updated")
                                    finish()
                                } else {
                                    Log.d(TAG, "Error password not updated")
                                }
                            }
                    } else {
                        Log.d(TAG, "Error auth failed")
                    }
                }
            }
        }
    }

    private fun cancelActivity() {
        finish()
    }
}